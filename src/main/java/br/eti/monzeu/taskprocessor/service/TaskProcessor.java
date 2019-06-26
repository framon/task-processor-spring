package br.eti.monzeu.taskprocessor.service;

import br.eti.monzeu.taskprocessor.Request;
import br.eti.monzeu.taskprocessor.TaskStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.Instant;
import java.util.List;

public interface TaskProcessor {

  List<TaskStatus<?>> search(Instant modifiedSince, String query);

  TaskStatus<?> getByCode(String taskCode);

  <T> TaskStatus<T> process(Request<T> request) throws JsonProcessingException;
}
