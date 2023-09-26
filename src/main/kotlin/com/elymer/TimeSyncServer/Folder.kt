package com.elymer.TimeSyncServer

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import javax.naming.spi.DirectoryManager

class Folder {
  companion object {
    private const val serverIP = "6"
    private const val smtNetwork = "10.0.0"
    internal val currentDirectory = Paths.get("").toAbsolutePath().toString()
    internal val AppDataFolder: String = getDir(currentDirectory,  "AppData")
    internal val TempFolder: String = getDir(AppDataFolder,  "Temp")
    internal val LogFolder: String = getDir(TempFolder,  "Log")

    private fun getDir(parentFolderPath: String?, newFolderName: String?): String {
      var root = parentFolderPath ?: currentDirectory
      if (newFolderName != null) {
        root += "\\$newFolderName"
      }
      val rootFile = File(root)
      if (!rootFile.exists()) {
        rootFile.mkdir()
      }
      return root
    }
  }
}
