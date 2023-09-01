package net.jfsanchez.dnsman.domain.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssertionUtil {

  public static void _assert(boolean value, String message) throws AssertionError {
    if (!value) {
      throw new AssertionError(message);
    }
  }
}
