package br.eti.monzeu.taskprocessor.service;

import br.eti.monzeu.taskprocessor.Request;
import org.springframework.stereotype.Component;

public class FullNameTaskSuffixTaskNameResolver implements TaskNameResolver {

  @Override
  public String resolveTaskBeanName(Request<?> request) {
    final String clsName = request.getClass().getName();
    final String suffix = "Request";

    String taskBeanName = clsName;
    if (clsName.endsWith(suffix)) {
      taskBeanName = clsName.substring(0, clsName.length() - suffix.length());
    }

    taskBeanName = taskBeanName + "Task";
    return taskBeanName;
  }
}
