package com.elymer.TimeSyncServer

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

object AESUtil {
  private const val CHARACTER_ENCODING = "UTF-8"
  private const val ALGORITHM = "AES"
  private const val KEY_SIZE = 16

  fun encrypt(input: String, key: String): String {
    val c = Cipher.getInstance(ALGORITHM)
    val secretKey = generateKey(key)
    c.init(Cipher.ENCRYPT_MODE, secretKey)
    val encryptedValue = c.doFinal(input.toByteArray(Charsets.UTF_8))
    return encodeBase64String(encryptedValue)
  }

  fun decrypt(encrypted: String, key: String): String {
    val c = Cipher.getInstance(ALGORITHM)
    val secretKey = generateKey(key)
    c.init(Cipher.DECRYPT_MODE, secretKey)
    val decodedValue = decodeBase64StringToBytes(encrypted)
    val decryptedValue = c.doFinal(decodedValue)
    return String(decryptedValue)
  }

  private fun generateKey(key: String): SecretKeySpec {
    var keyBytes = key.toByteArray(Charsets.UTF_8)
    keyBytes = keyBytes.copyOf(KEY_SIZE)
    return SecretKeySpec(keyBytes, ALGORITHM)
  }

  private fun encodeBase64String(bytes: ByteArray): String {
    return String(java.util.Base64.getEncoder().encode(bytes))
  }

  private fun decodeBase64StringToBytes(stringData: String): ByteArray {
    return  java.util.Base64.getDecoder().decode(stringData.toByteArray(charset("UTF-8")))
  }
}