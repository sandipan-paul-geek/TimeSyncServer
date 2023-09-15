package com.elymer.TimeSyncServer

import com.elymer.TimeSyncServer.Support.Companion.deserialize
import com.elymer.TimeSyncServer.Support.Companion.serialize
import kotlinx.serialization.Serializable

interface Instruction {
  val key: String
  val data: String?
  companion object
  {
    internal const val GetFiles  = "GetFiles"
    internal const val Ping  = "Ping"

  }
  @kotlinx.serialization.Serializable
  class Request(override val key: String, override val data: String?) : Instruction
  {
    companion object{
      internal inline fun <reified T> getObject(value:Any):T?
      {
        return try
        {
          (if(value is String)  value else value.serialize()).deserialize<T>()
        }
        catch(ex:Exception)
        {
          null
        }
      }
    }
  }

  @Serializable
  class Response(override val key: String, override val data: String?= null, val err: String? = null) : Instruction
}

