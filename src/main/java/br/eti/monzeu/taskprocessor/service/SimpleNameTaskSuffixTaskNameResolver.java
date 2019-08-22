package br.eti.monzeu.taskprocessor.service;

import br.eti.monzeu.taskprocessor.Request;
import java.beans.Introspector;
import org.springframework.stereotype.Component;

@Component
public class SimpleNameTaskSuffixTaskNameResolver implements TaskNameResolver {

  @Override
  public String resolveTaskBeanName(Request<?> request) {
    final String clsName = request.getClass().getSimpleName();
    final String suffix = "Request";

    String taskBeanName = clsName;
    if (clsName.endsWith(suffix)) {
      taskBeanName = clsName.substring(0, clsName.length() - suffix.length());
    }

    taskBeanName = Introspector.decapitalize(taskBeanName + "Task");
    return taskBeanName;
  }
}
