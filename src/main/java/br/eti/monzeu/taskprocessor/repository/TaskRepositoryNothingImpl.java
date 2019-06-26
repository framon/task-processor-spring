package br.eti.monzeu.taskprocessor.repository;

import br.eti.monzeu.taskprocessor.service.TaskFilter;
import br.eti.monzeu.taskprocessor.service.TaskModel;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Nothing")
public class TaskRepositoryNothingImpl implements TaskRepository {

  @Override
  public Optional<TaskModel> findById(UUID uuid) {
    return Optional.empty();
  }

  @Override
  public TaskModel save(TaskModel model) {
    return model;
  }

  @Override
  public List<TaskModel> find(TaskFilter filter) {
    return Collections.emptyList();
  }
}
