package br.eti.monzeu.taskprocessor.util;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

public abstract class AutoConverter<S, T> implements Converter<S, T> {

  @Autowired
  protected ConversionService conversionService;

  @PostConstruct
  private void register() {
    if (conversionService instanceof GenericConversionService) {
      ((GenericConversionService) conversionService).addConverter(this);
    }
  }
}