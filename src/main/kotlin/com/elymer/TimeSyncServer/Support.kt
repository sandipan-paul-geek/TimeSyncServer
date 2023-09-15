package com.elymer.TimeSyncServer

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Support {
  companion object {
    val String.Companion.empty: String
      get() {
        return ""
      }

    fun String.tryParseToInt(): Int? = try {
      this.toInt()
    } catch (_: Exception) {
      null
    }

    fun String.tryParseToLong(): Long? = try {
      this.toLong()
    } catch (_: Exception) {
      null
    }

    inline fun <reified T> Any.tryParse(): T? = if (this is T) try {
      this
    } catch (_: Exception) {
      null
    } else null

    inline fun <reified T> String.deserialize(): T? {
      return try {
        Json.decodeFromString<T>(this)
      } catch (_: Exception) {
        println(this)
        null
      }
    }

    inline fun <reified T> T.serialize(encodeDefault: Boolean? = null): String {
      return encodeDefault?.let {
        Json { encodeDefaults = true }.encodeToString(this)
      } ?: Json.encodeToString(this)
    }
  }
}