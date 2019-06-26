package br.eti.monzeu.taskprocessor.repository;

import br.eti.monzeu.taskprocessor.service.TaskFilter;
import br.eti.monzeu.taskprocessor.service.TaskModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {

  Optional<TaskModel> findById(UUID uuid);

  TaskModel save(TaskModel model);

  List<TaskModel> find(TaskFilter filter);
}
