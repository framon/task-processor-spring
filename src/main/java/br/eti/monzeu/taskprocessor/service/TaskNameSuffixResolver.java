package br.eti.monzeu.taskprocessor.service;

import br.eti.monzeu.taskprocessor.Request;
import org.springframework.stereotype.Component;

@Component
public class TaskNameSuffixResolver implements TaskNameResolver {

  @Override
  public String resolveTaskBeanName(Request<?> request) {
    String simpleName = request.getClass().getName();
    String suffix = "Request";

    if (simpleName.endsWith(suffix)) {
      simpleName = simpleName.substring(0, simpleName.length() - suffix.length());
    }
    String taskBeanName = simpleName + "Task";
    return taskBeanName;
  }
}
