package com.elymer.TimeSyncServer

import java.io.File

open class FilePaths
{
  companion object {

    internal val FilesUUID: JsonFile =  GetFile(Folder.Material, "filesuuid.jsn")
    internal val uuidInterrupt: JsonFile = GetFile(Folder.Interrupt, "filesuuid.jsn")

    internal fun GetFile(folderPath: String, fileNameWithExt: String): JsonFile {
      return JsonFile(File("$folderPath/$fileNameWithExt"))
    }
  }
}

