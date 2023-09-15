package com.elymer.TimeSyncServer

import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files

open class TextFile(val file: File) {
  constructor(filePath: String) : this(File(filePath))

  internal fun getName():String
  {
    return file.name
  }
  internal fun getNameWithoutExtension():String
  {
    return file.nameWithoutExtension
  }
  internal fun getExtension():String
  {
    return file.extension
  }
  internal fun getPath():String
  {
    return file.path
  }
  internal fun readAllLines(): List<String> {
    if (this.file.exists()) {
      return this.file.readLines(Charset.defaultCharset())
    }
    return listOf()
  }

  internal fun readAllText(): String? {
    if (this.file.exists()) {
      return this.file.readText(Charset.defaultCharset())
    }
    return null
  }

  internal fun writeJson(string: String): Boolean {
    return try {
      this.file.writeText(string)
      true
    } catch (ex: Exception) {
      false
    }
  }

  internal fun getFiles(ignoreFolders:Boolean = true): List<File>? {
    return try
    {
      if (!file.exists()) {
        return null
      }
      if (!file.isDirectory) {
        return null
      }
      if (!file.canRead()) {
        return null
      }
      var childFiles = (file.list() ?: return null).filterNotNull().map { File(file, it ) }
      if(ignoreFolders)
      {
        childFiles = childFiles.parallelStream().filter { !it.isDirectory }.toList()
      }
      return childFiles
    } catch (ex: Exception) {
      null
    }
  }

  internal fun lastModified(): Long {
    return this.file.lastModified()
  }

  internal fun moveTo(newLocation: String): Boolean {
    try {
      Files.move(this.file.toPath(), File(newLocation).toPath())
    } catch (ex: Exception) {
      return false
    }
    return true
  }

//  fun asTemp(): JsonFile {
//     return FilePaths.GetFile(Folder.Temp, getName())
//  }
}