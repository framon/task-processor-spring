package br.eti.monzeu.taskprocessor;

public interface Task<R extends Request<V>, V> {

  V run(R request) throws Exception;
}
