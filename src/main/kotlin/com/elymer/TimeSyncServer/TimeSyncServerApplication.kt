package com.elymer.TimeSyncServer

import org.apache.catalina.connector.Connector
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import java.net.InetAddress
import java.security.SecureRandom
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

//TODO -> Note:  Execute "./gradlew bootjar" (without quotes) in terminal under project directory "D:\Shared\SandipanPaul\IntelliProjects\materialserver>" in order to make jar
@SpringBootApplication
class TimeSyncServerApplication: WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	override fun customize(factory: TomcatServletWebServerFactory) {
		factory.address = InetAddress.getByName("10.0.0.6")
		factory.port = 8085
//		factory.addAdditionalTomcatConnectors(Connector().apply {
//			port = 8090
//		})
	}
}

fun main(args: Array<String>) {
	runApplication<TimeSyncServerApplication>(*args)
	//generateKey()
	//test2()
}

fun deriveKeyFromPassphrase(passphrase: String, salt: ByteArray, iterationCount: Int, keyLength: Int): ByteArray {
	val spec = PBEKeySpec(passphrase.toCharArray(), salt, iterationCount, keyLength * 8) // *8 because we specify the length in bits
	val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
	return skf.generateSecret(spec).encoded
}

fun generateKey() {
	// Example passphrase (this should be input securely in a real application)
	val passphrase = "#Chandra_Yan3"
	// Generating a random salt (should be saved for later use if you intend to derive the key again in the future)
	val random = SecureRandom()
	val salt1 = ByteArray(16)  // 16 bytes salt
	val salt2 = ByteArray(16)  // 16 bytes salt
	random.nextBytes(salt1)
	random.nextBytes(salt2)

	// Parameters for PBKDF2
	val iterationCount = 10000  // You can adjust this value. Higher values add more security but also take longer.
	val keyLength = 32  // 32 bytes for AES-256

	// Deriving the key
	val derivedKey1 = deriveKeyFromPassphrase(passphrase, salt1, iterationCount, keyLength)
	val derivedKey2 = deriveKeyFromPassphrase(passphrase, salt2, iterationCount, keyLength)

	// Print the derived key and salt in Base64 format (useful for storage or transmission)
	println("val requestKeyBase64 =  ${Base64.getEncoder().encodeToString(derivedKey1)}")
	println("val requestKeyBase64 =  ${Base64.getEncoder().encodeToString(derivedKey2)}")
  println()
	println("Salt (Base64): ${Base64.getEncoder().encodeToString(salt1)}")
	println("Salt (Base64): ${Base64.getEncoder().encodeToString(salt2)}")
}

fun test2() {
	// Example passphrase and salt
	val passphrase = "#Chandra_Yan3"
	val random = SecureRandom()
	val salt = ByteArray(16)
	random.nextBytes(salt)
	val iterationCount = 10000
	val keyLength = 32  // Change to 16 bytes for AES-128

	// Derive the 16-byte key using PBKDF2
	val derivedKey = deriveKeyFromPassphrase(passphrase, salt, iterationCount, keyLength)
	val derivedKeyBase64 = Base64.getEncoder().encodeToString(derivedKey)

	// Use AESUtil with the derived key

	val path = "\\\\172.16.16.14\\Material\\connectedIps.jsn"//connectedIps
	val plaintext =  TextFile(path).readAllText()!!
	val encryptedText = AESUtilAdvanced.encrypt(plaintext, derivedKeyBase64)
	val decryptedText = AESUtilAdvanced.decrypt(encryptedText, derivedKeyBase64)

	println("Original Text: $plaintext")
	println("Encrypted Text: $encryptedText")
	println("Decrypted Text: $decryptedText")

	val cryptoKeyMap = mapOf<String, CryptoKey>("RequestKey" to CryptoKey.generateKey("AnyPassword"),"ResponseKey" to CryptoKey.generateKey("AnyPassword"))
	CryptoKey.save(cryptoKeyMap)
}





