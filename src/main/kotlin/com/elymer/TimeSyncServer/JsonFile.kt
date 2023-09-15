package com.elymer.TimeSyncServer

import com.elymer.TimeSyncServer.Support.Companion.deserialize
import java.io.File

class JsonFile(file: File) : TextFile(file) {
  constructor(filePath: String) : this(File(filePath))
  constructor(folderName: String, fileName:String) : this(File(folderName, fileName))

  internal inline fun <reified T> readArrayOf(): List<T>? {
    val json = readAllText() ?: return null
    var i = 0
    while (i < 10) {
      try
      {
        return json.deserialize()
      } catch (ex: Exception)
      {
        println(ex.message)
      }
      i++
    }
    return null
  }
  internal inline fun <reified T> readObj(): T? {
    val json = readAllText() ?: return null
    return try {
      json.deserialize<T>()
    } catch (ex: Exception) {
      null
    }
  }
}