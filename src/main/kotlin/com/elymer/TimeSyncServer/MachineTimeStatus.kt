package com.elymer.TimeSyncServer

import com.elymer.TimeSyncServer.Support.Companion.serialize
import org.springframework.stereotype.Service

@kotlinx.serialization.Serializable
class MachineTimeStatus(var map: MutableMap<Int, Long> = mutableMapOf(),  var timerDelay:Int = 60000) {
  init {
    println(timerDelay)
  }
  companion object {
    fun read(): MachineTimeStatus? {
      return  FilePaths.MachineTimeStatus.readObj<MachineTimeStatus>()
    }
    fun MachineTimeStatus.update():Boolean {
     return kotlin.runCatching {
           this.serialize().let { json ->
             FilePaths.MachineTimeStatus.writeJson(json)
           }
         }.takeIf { it.isSuccess }?.getOrNull() ?:false
    }
  }
}