package br.eti.monzeu.taskprocessor.service;

import br.eti.monzeu.taskprocessor.Error;
import br.eti.monzeu.taskprocessor.Status;
import br.eti.monzeu.taskprocessor.TaskStatus;
import br.eti.monzeu.taskprocessor.util.AutoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskModel2TaskStatusConverter extends AutoConverter<TaskModel, TaskStatus<?>> {

  @Autowired
  private ObjectMapper om;

  @Override
  public TaskStatus<?> convert(TaskModel src) {
    if (src == null) {
      return null;
    }

    TaskStatus<Object> dst = new TaskStatus<>();
    dst.setCode(src.getId().toString());
    dst.setCreatedAt(src.getCreatedAt());
    dst.setModifiedAt(src.getModifiedAt());
    dst.setName(src.getName());
    dst.setStatus(src.getStatus());
    dst.setUser(src.getUser());

    boolean success = false;

    if (Status.FAILED.equals(src.getStatus()) && src.getOutput() != null) {
      try {
        Error err = om.readValue(src.getOutput(), Error.class);
        dst.setResult(err);
        success = true;
      } catch (Exception ignored) {
      }
    }

    if (!success) {
      if ("null".equals(src.getOutput())) {
        dst.setResult(null);
        success = true;
      }
    }

    if (!success) {
      try {
        String err = om.readValue(src.getOutput(), String.class);
        dst.setResult(err);
        success = true;
      } catch (Exception ignored) {
      }
    }

    if (!success) {
      try {
        Map<?, ?> err = om.readValue(src.getOutput(), Map.class);
        dst.setResult(err);
        success = true;
      } catch (Exception ignored) {
      }
    }

    if (!success) {
      dst.setResult(src.getOutput());
    }

    return dst;
  }

}
