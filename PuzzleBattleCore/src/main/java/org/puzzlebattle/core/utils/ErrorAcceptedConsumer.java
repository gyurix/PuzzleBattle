package org.puzzlebattle.core.utils;

public interface ErrorAcceptedConsumer<T> {
  void accept(T t) throws Throwable;
}
