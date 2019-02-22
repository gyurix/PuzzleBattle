package org.puzzlebattle.core.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Write a description of class SkladPonuka here.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public class FieldAccessor {
  private static Field modifierField;

  static {
    try {
      modifierField = Field.class.getDeclaredField("modifiers");
      modifierField.setAccessible(true);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  private Class cl;
  private Field field;
  private String name;

  public FieldAccessor(Class cl, String name) {
    this.cl = cl;
    this.name = name;
    FieldAccessor has = Reflection.fields.get(this);
    if (has != null) {
      field = has.field;
      return;
    }
    resolve();
    Reflection.fields.put(this, this);
  }

  public Object get(Object obj) {
    try {
      return field.get(obj);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return null;
  }

  public Field getField() {
    return field;
  }

  @Override
  public int hashCode() {
    return cl.hashCode() * 31 + name.hashCode() * 31 * 31;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof FieldAccessor))
      return false;
    FieldAccessor fa = (FieldAccessor) obj;
    return fa.cl == cl && fa.name.equals(name);
  }

  private void resolve() {
    Class check = cl;
    while (check != null) {
      try {
        this.field = check.getDeclaredField(name);
        modifierField.setInt(field, modifierField.getInt(field) & ~Modifier.FINAL);
        this.field.setAccessible(true);
      } catch (Throwable ignored) {
      }
      check = check.getSuperclass();
    }
  }

  public void set(Object obj, Object value) {
    try {
      field.set(obj, value);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
}
