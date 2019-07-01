package br.eti.monzeu.taskprocessor.service;

import br.eti.monzeu.taskprocessor.Error;
import br.eti.monzeu.taskprocessor.Request;
import br.eti.monzeu.taskprocessor.Status;
import br.eti.monzeu.taskprocessor.Task;
import br.eti.monzeu.taskprocessor.TaskStatus;
import br.eti.monzeu.taskprocessor.repository.TaskRepository;
import br.eti.monzeu.taskprocessor.util.QueryUtil;
import br.eti.monzeu.taskprocessor.util.QueryUtil.KeyValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessorImpl implements TaskProcessor {

  public static final Logger LOGGER = LoggerFactory.getLogger(TaskProcessorImpl.class);

  private ApplicationContext applicationContext;
  private TaskNameResolver taskNameResolver;
  private ConversionService conversion;
  private ObjectMapper om;
  private TaskRepository repository;

  public TaskProcessorImpl(ApplicationContext applicationContext, TaskNameResolver taskNameResolver,
      ConversionService conversion, ObjectMapper om, TaskRepository repository) {
    this.applicationContext = applicationContext;
    this.taskNameResolver = taskNameResolver;
    this.om = om;
    this.conversion = conversion;
    this.repository = repository;
  }

  @Override
  public List<TaskStatus<?>> search(Instant modifiedSince, String query) {

    String principal = getPrincipal();

    TaskFilter filter = new TaskFilter();
    filter.user(principal);

    filter.modifiedSince(modifiedSince);

    Iterable<KeyValue> terms = QueryUtil.split(query);
    for (KeyValue kv : terms) {
      if ("code".equals(kv.getKey())) {
        UUID uuid = UUID.fromString(kv.getValue());
        filter.id(uuid);
      } else if ("name".equals(kv.getKey())) {
        filter.name(kv.getValue());
      }
    }

    List<TaskModel> list = repository.find(filter);

    @SuppressWarnings("unchecked")
    List<TaskStatus<?>> tasks = (List<TaskStatus<?>>) conversion.convert(list,
        TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(TaskModel.class)),
        TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(TaskStatus.class)));
    return tasks;
  }

  @Override
  public TaskStatus<?> getByCode(String taskCode) {
    if (taskCode == null) {
      throw new NullPointerException();
    }

    UUID uuid = UUID.fromString(taskCode);
    Optional<TaskModel> task = repository.findById(uuid);

    TaskStatus<?> taskStatus = conversion.convert(task, TaskStatus.class);
    return taskStatus;
  }


  public <V> TaskStatus<V> process(Request<V> request)
      throws JsonProcessingException {

    String principal = getPrincipal();

    TaskModel model = new TaskModel();
    model.setId(UUID.randomUUID());
    model.setCreatedAt(Instant.now());
    model.setInput(om.writeValueAsString(request));
    model.setModifiedAt(model.getCreatedAt());
    model.setName(request.getClass().getName());
    model.setStatus(Status.READY);
    model.setUser(principal);
    model = repository.save(model);

    try {

      String taskBeanName = resolveTaskBeanName(request);
      Task<Request<V>, V> task = (Task<Request<V>, V>) applicationContext.getBean(taskBeanName);

      V result = task.run(request);

      boolean isVoid = false;
      for (Type genericInterface : task.getClass().getGenericInterfaces()) {
        if (genericInterface instanceof ParameterizedType) {
          Type rawType = ((ParameterizedType) genericInterface).getRawType();
          if (Task.class.equals(rawType)) {
            Type genericType = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
            isVoid = Void.class.equals(genericType);
          }
        }
      }

      model.setModifiedAt(Instant.now());
      model.setStatus(Status.COMPLETED);
      model.setOutput(isVoid ? null : om.writeValueAsString(result));
      model = repository.save(model);

    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      Error err = new Error();
      err.setMessage(e.getMessage());

      model.setModifiedAt(Instant.now());
      model.setStatus(Status.FAILED);
      model.setOutput(om.writeValueAsString(err));
      model = repository.save(model);
    }

    @SuppressWarnings("unchecked")
    TaskStatus<V> result = conversion.convert(model, TaskStatus.class);
    return result;
  }

  private String getPrincipal() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth != null ? (String) auth.getPrincipal() : "«none»";
  }

  public String resolveTaskBeanName(Request<?> request) {
    return taskNameResolver.resolveTaskBeanName(request);
  }

}
