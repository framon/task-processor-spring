package br.eti.monzeu.taskprocessor.service;

import br.eti.monzeu.taskprocessor.Request;

public interface TaskNameResolver {

  String resolveTaskBeanName(Request<?> request);
}
