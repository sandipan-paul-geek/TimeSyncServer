package com.elymer.TimeSyncServer

import java.io.File

data class ProcessDetail(
    var seconds: Int = 0,
    var filePath: String = "",
    var moduleName: String = "",
    var processId: Int = 0
) {
    constructor(moduleName: String, moduleFilePath: String) : this() {
        this.filePath = moduleFilePath
        this.moduleName = moduleName
    }

    constructor(process: Process) : this() {
        this.seconds = Time1970().getSecondsFrom1970();
        this.filePath = process.info().command().orElse("")
        this.moduleName = File(this.filePath).name
        this.processId = process.pid().toInt()
    }

    companion object {
        //private val Process_PriceImporting = ProcessDetail("PriceImporting.jar", "D:\\Shared\\SandipanPaul\\VisualStudioProjects\\MaterialPlan\\bin\\x64\\Release\\PriceImporting.jar")
        private const val dataPath = "C:\\Material\\process_details.json"
    }
}
