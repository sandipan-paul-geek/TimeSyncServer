package com.elymer.TimeSyncServer

import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtilAdvanced {
	private const val transformation = "AES/CBC/PKCS5Padding"
	private const val aesAlgorithm = "AES"

	fun encrypt(input: String, base64Key: String): String {
		val keyBytes = Base64.getDecoder().decode(base64Key)
		val secretKey = SecretKeySpec(keyBytes, aesAlgorithm)

		val cipher = Cipher.getInstance(transformation)
		val ivBytes = ByteArray(16) // 16 bytes IV for AES
		SecureRandom().nextBytes(ivBytes)
		val ivSpec = IvParameterSpec(ivBytes)

		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)

		val encrypted = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
		val combined = ivBytes + encrypted  // Store IV with ciphertext for decryption

		return Base64.getEncoder().encodeToString(combined)
	}

	fun decrypt(input: String, base64Key: String): String {
		val keyBytes = Base64.getDecoder().decode(base64Key)
		val secretKey = SecretKeySpec(keyBytes, aesAlgorithm)

		val combined = Base64.getDecoder().decode(input)
		val ivBytes = combined.sliceArray(0 until 16)
		val encrypted = combined.sliceArray(16 until combined.size)
		val ivSpec = IvParameterSpec(ivBytes)

		val cipher = Cipher.getInstance(transformation)
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)

		val original = cipher.doFinal(encrypted)
		return String(original, Charsets.UTF_8)
	}
}
