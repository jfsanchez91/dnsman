package net.jfsanchez.dnsman.application.util;

import java.util.LinkedList;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;

public final class MockitoUtil {
  private MockitoUtil() {
  }

  public static void resetAll(Object obj) {
    final var mocks = new LinkedList<>();
    for (final var field : obj.getClass().getDeclaredFields()) {
      try {
        final var isAccessible = field.canAccess(obj);
        field.setAccessible(true);
        final var mock = field.get(obj);
        field.setAccessible(isAccessible);
        if (MockUtil.isMock(mock)) {
          mocks.add(mock);
        }
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    Mockito.reset(mocks.toArray(new Object[0]));
  }
}
