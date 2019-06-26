package br.eti.monzeu.taskprocessor;

public interface Task<V> {

  V run() throws Exception;
}
