package com.elymer.TimeSyncServer

import java.io.File

class Folder {
  companion object {
    private const val smtNetwork = "10.0.0"
    private const val officeNetwork = "172.16.16"
// C:\Material\smtFileData

    internal val Material: String = getDir("\\\\$officeNetwork.14",  "Material") //Material
    internal val Interrupt: String = getDir("\\\\$officeNetwork.14",  "Material\\Interrupt") //Material
    internal val Backup: String = getDir("\\\\$officeNetwork.14",  "Material\\backup") //Material

    internal val SmtFileData: String = getDir(Material, "smtFileData") //smtFileData
    internal val InstantData: String = getDir(SmtFileData, "instant") //smtFileData/instant

    private fun getDir(parentFolderPath: String?, newFolderName: String?): String {
      var root = parentFolderPath ?: "C:\\"
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
