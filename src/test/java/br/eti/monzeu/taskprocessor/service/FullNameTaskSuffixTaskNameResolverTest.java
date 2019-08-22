package br.eti.monzeu.taskprocessor.service;

import static org.junit.jupiter.api.Assertions.*;

import br.eti.monzeu.taskprocessor.SimpleRequest;
import org.junit.jupiter.api.Test;

class FullNameTaskSuffixTaskNameResolverTest {

  FullNameTaskSuffixTaskNameResolver resolver = new FullNameTaskSuffixTaskNameResolver();

  @Test
  void resolveTaskBeanName() {
    SimpleRequest request = new SimpleRequest();
    String beanName = resolver.resolveTaskBeanName(request);
    assertEquals("br.eti.monzeu.taskprocessor.SimpleTask", beanName);
  }
}