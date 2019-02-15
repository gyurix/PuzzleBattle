package org.puzzlebattle.core.utils.reflection;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodInvoker {
  private Class cl;
  private Method method;
  private String name;
  private Class[] params;

  public MethodInvoker(Class cl, String name, Class[] params) {
    this.cl = cl;
    this.params = params;
    this.name = name;
    MethodInvoker has = Reflection.methods.get(this);
    if (has != null) {
      method = has.method;
      return;
    }
    resolve();
    Reflection.methods.put(this, this);
  }

  public Object call(Object obj, Object... args) {
    try {
      return method.invoke(obj, args);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return null;
  }

  public Method getMethod() {
    return method;
  }

  @Override
  public int hashCode() {
    return cl.hashCode() * 31 + name.hashCode() * 31 * 31 + Arrays.hashCode(params) * 31 * 31 * 31;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof MethodInvoker))
      return false;
    MethodInvoker mi = (MethodInvoker) obj;
    return mi.cl == cl && mi.name.equals(name) && Arrays.equals(mi.params, params);
  }

  private void resolve() {
    Class check = cl;
    while (check != null) {
      try {
        this.method = check.getDeclaredMethod(name, params);
        this.method.setAccessible(true);
      } catch (Throwable ignored) {
      }
      check = check.getSuperclass();
    }
  }
}
