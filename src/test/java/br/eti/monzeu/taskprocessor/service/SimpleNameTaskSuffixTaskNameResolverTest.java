package br.eti.monzeu.taskprocessor.service;

import static org.junit.jupiter.api.Assertions.*;

import br.eti.monzeu.taskprocessor.SimpleRequest;
import org.junit.jupiter.api.Test;

class SimpleNameTaskSuffixTaskNameResolverTest {

  SimpleNameTaskSuffixTaskNameResolver resolver = new SimpleNameTaskSuffixTaskNameResolver();

  @Test
  void resolveTaskBeanName() {
    SimpleRequest request = new SimpleRequest();
    String beanName = resolver.resolveTaskBeanName(request);
    assertEquals("simpleTask", beanName);
  }
}