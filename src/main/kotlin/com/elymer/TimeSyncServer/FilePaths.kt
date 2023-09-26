package com.elymer.TimeSyncServer

import java.io.File

open class FilePaths
{
  companion object {
    internal val MachineTimeStatus : JsonFile =  GetFile(Folder.AppDataFolder, "MachineTimeStatus.json")

    internal fun GetFile(folderPath: String, fileNameWithExt: String): JsonFile {
      return JsonFile(File("$folderPath/$fileNameWithExt"))
    }
  }
}

