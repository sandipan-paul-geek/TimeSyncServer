package com.elymer.TimeSyncServer

import com.elymer.TimeSyncServer.Support.Companion.serialize
import java.security.SecureRandom
import java.util.*

@kotlinx.serialization.Serializable
class CryptoKey(val passphrase: String? = null, val salt: String?= null, val keySize:Int = 16, val iterationCount:Int= 10000, val Key:String?= null)
{
	companion object
	{
		private val filePath = ""

		internal fun generateKey(passphrase:String, iterationCount:Int = 10000, keyLength:Int = 16 ): CryptoKey {
			val random = SecureRandom()
			val saltBuffer = ByteArray(16)  // 16 bytes salt
			random.nextBytes(saltBuffer)
			val keyBytes = deriveKeyFromPassphrase(passphrase, saltBuffer, iterationCount, keyLength)
			val saltStr = Base64.getEncoder().encodeToString(saltBuffer)
			val key = Base64.getEncoder().encodeToString(keyBytes)
			return CryptoKey(passphrase, saltStr,keyLength, iterationCount, key)
		}

		internal fun read(): Map<String, CryptoKey>? {
			return JsonFile(filePath).readObj<Map<String, CryptoKey>>()
		}

		internal fun save(cryptoKeyMap: Map<String, CryptoKey>) {
			cryptoKeyMap.serialize().let { json ->
				JsonFile(filePath).writeJson(json)
			}
		}
	}
}
