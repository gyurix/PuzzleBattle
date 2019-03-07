package org.puzzlebattle;

import org.junit.Test;
import org.puzzlebattle.core.utils.HashUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHashUtils {
  @Test
  public void testMD5() {
    assertEquals(HashUtils.hash("Hello", HashUtils.HashType.MD5), "8b1a9953c4611296a827abf8c47804d7");
  }

  @Test
  public void testSHA256() {
    assertEquals(HashUtils.hash("Hello", HashUtils.HashType.SHA_256),
            "185f8db32271fe25f561a6fc938b2e264306ec304eda518007d1764826381969");
  }

  @Test
  public void testToHex() {
    assertEquals(HashUtils.toHex("Hello".getBytes()), "48656c6c6f");
  }
}
