package com.elymer.TimeSyncServer

import com.elymer.TimeSyncServer.Support.Companion.serialize
import org.springframework.stereotype.Service

@Service
class MachineTimeStatus {
  internal var map: MutableMap<Int, Long> = mutableMapOf()
  internal var timerDelay = 60000
  init {
    read()?.let {
      map = this@MachineTimeStatus.map
      timerDelay = this@MachineTimeStatus.timerDelay
    }
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