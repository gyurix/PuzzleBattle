package org.puzzlebattle.core.utils;

import lombok.Getter;
import lombok.Setter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

/**
 * Utils for encryption
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public class EncryptionUtils {
  private static KeyGenerator aesGenerator;
  private static Random random = new Random();
  private static KeyPairGenerator rsaGenerator;
  private static KeyFactory rsaKeyFactory;

  static {
    try {
      rsaGenerator = KeyPairGenerator.getInstance("RSA");
      rsaGenerator.initialize(2048);
      aesGenerator = KeyGenerator.getInstance("AES");
      aesGenerator.init(256);
      rsaKeyFactory = KeyFactory.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  private Cipher aesCipher;
  @Getter
  @Setter
  private IvParameterSpec aesDecryptIV;
  @Getter
  @Setter
  private IvParameterSpec aesEncryptIV;
  @Getter
  @Setter
  private Key aesKey;
  private Cipher rsaCipher;
  @Getter
  @Setter
  private PrivateKey rsaDecryptKey;
  @Getter
  @Setter
  private PublicKey rsaEncryptKey;


  /**
   * Constructor for encryption utils
   */

  public EncryptionUtils() {
    try {
      rsaCipher = Cipher.getInstance("RSA");
      aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }


  /**
   * Method which generates EAS key
   *
   * @return EAS key
   */

  public static Key generateAES() {
    return aesGenerator.generateKey();
  }


  /**
   * Method which generate IV
   *
   * @return Iv parameter spec
   */

  public static IvParameterSpec generateIV() {
    byte[] data = new byte[16];
    random.nextBytes(data);
    return new IvParameterSpec(data);
  }


  /**
   * Key pair will be generated
   *
   * @return generated key pair
   */

  public static KeyPair generateRSA() {
    return rsaGenerator.generateKeyPair();
  }


  /**
   * Conversion to EAS key
   *
   * @param key data which represents key in buffer
   * @return the sum of x and y
   */

  public static Key toAESKey(byte[] key) {
    return new SecretKeySpec(key, "AES");
  }


  /**
   * A conversion to Iv
   *
   * @param data data in buffer
   * @return IV parameter spec
   */

  public static IvParameterSpec toIV(byte[] data) {
    return new IvParameterSpec(data);
  }


  /**
   * In this method RSA public key will be generated from buffer
   *
   * @param key buffer with decoded key
   * @return RSA public key
   */

  public static PublicKey toRSAPublicKey(byte[] key) {
    try {
      return rsaKeyFactory.generatePublic(new X509EncodedKeySpec(key));
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Decryption of EAS
   *
   * @param data data in buffer
   * @return decrypted EAS, byte buffer
   */

  public byte[] decryptAES(byte[] data) {
    try {
      aesCipher.init(Cipher.DECRYPT_MODE, aesKey, aesDecryptIV);
      byte[] decrypted = aesCipher.doFinal(data);
      byte[] out = new byte[decrypted.length - 16];
      byte[] newDecryptIv = new byte[16];
      System.arraycopy(decrypted, 0, newDecryptIv, 0, 16);
      System.arraycopy(decrypted, 16, out, 0, decrypted.length - 16);
      aesDecryptIV = new IvParameterSpec(newDecryptIv);
      return out;
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Decryption of RSA
   *
   * @param data data in buffer
   * @return decrypted RSA, byte buffer, if any error- null
   */

  public byte[] decryptRSA(byte[] data) {
    try {
      rsaCipher.init(Cipher.DECRYPT_MODE, rsaDecryptKey);
      return rsaCipher.doFinal(data);
    } catch (Throwable e) {
      e.printStackTrace();
      return null;
    }
  }


  /**
   * Encryption of EAS
   *
   * @param data data in buffer
   * @return encrypted EAS, byte buffer
   */

  public byte[] encryptAES(byte[] data) {
    try {
      aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, aesEncryptIV);
      aesEncryptIV = generateIV();
      byte[] encryptable = new byte[data.length + 16];
      System.arraycopy(aesEncryptIV.getIV(), 0, encryptable, 0, 16);
      System.arraycopy(data, 0, encryptable, 16, data.length);
      return aesCipher.doFinal(encryptable);
    } catch (Throwable e) {
      e.printStackTrace();
      return null;
    }
  }


  /**
   * An example of a method - replace this comment with your own
   *
   * @param data data in buffer
   * @return encrypted RSA, byte buffer
   */

  public byte[] encryptRSA(byte[] data) {
    try {
      rsaCipher.init(Cipher.ENCRYPT_MODE, rsaEncryptKey);
      return rsaCipher.doFinal(data);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
}

