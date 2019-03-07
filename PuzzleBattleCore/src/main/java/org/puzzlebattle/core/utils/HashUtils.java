package org.puzzlebattle.core.utils;

import lombok.Getter;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.puzzlebattle.core.utils.Logging.*;


public final class HashUtils {
  public static String hash(String msg, HashType type) {
    logFine("Hash", "algorithm", type, "message", msg);
    String out = toHex(type.getDigest().digest(msg.getBytes(UTF_8)));
    logInfo("Hash - Result", "algorithm", type, "message", msg, "result", out);
    return out;
  }

  public static String toHex(byte[] data) {
    return DatatypeConverter.printHexBinary(data).toLowerCase();
  }

  public enum HashType {
    MD5,
    SHA_256;
    @Getter
    private MessageDigest digest;

    HashType() {
      String algo = toString();
      try {
        digest = MessageDigest.getInstance(algo);
      } catch (NoSuchAlgorithmException e) {
        logSevere("Hashing algorithm was not found", "algorithm", algo, "error", e);
      }
    }

    @Override
    public String toString() {
      return name().replace("_", "-");
    }
  }
}
