package com.elymer.TimeSyncServer

import com.elymer.TimeSyncServer.MachineTimeStatus.Companion.update
import com.elymer.TimeSyncServer.Support.Companion.serialize
import jakarta.servlet.http.HttpServletRequest
import kotlinx.serialization.Serializable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*
import java.net.http.HttpResponse.ResponseInfo

@RestController
@Validated
class TimeSyncController {
  internal val machineTimeStatus:MachineTimeStatus = MachineTimeStatus.read()?.let {
    MachineTimeStatus(it.map, it.timerDelay)
  }?:MachineTimeStatus()

  @GetMapping()
  fun getRequestHandler(httpRequest: HttpServletRequest): String {
    val ipAddress = getClientIP(httpRequest)
    val pathInfo = httpRequest.pathInfo
    return "Time Sync Server for 'SMT' is Running..."
  }
  @PostMapping("/setTimerDelay")
  fun requestSetTimerDelay(@RequestBody timerDelay: Int?) {
    timerDelay?.let {
      this.machineTimeStatus.timerDelay = timerDelay
      val s = this.machineTimeStatus.update()
    }
  }
  @GetMapping("/getTimerDelay")
  fun requestGetTimerDelay():Int {
   return this.machineTimeStatus.timerDelay
  }
  @GetMapping("/machineTimeStatus")
  fun requestMachineTimeStatus(): String {
    return this.machineTimeStatus.map.serialize()
  }

  @GetMapping("/time")
  fun requestTimeSync(httpRequest: HttpServletRequest): String {
    val ipAddress = getClientIP(httpRequest)
    Time1970().getSecondsFrom1970().toLong().let { secondsFrom1970 ->
      val dt = Time1970().getDateFromSeconds(secondsFrom1970.toInt())
      DateTimeInfo(secondsFrom1970, this.machineTimeStatus.timerDelay.toLong()).serialize().let { json ->
       val ip = ipAddress.split('.').last().toInt()
       if( machineTimeStatus.map.contains(ip)) {
         machineTimeStatus.map[ip] = secondsFrom1970
       }
        else{
         machineTimeStatus.map[ip] = secondsFrom1970
        }
        machineTimeStatus.update()
        println("SyncTime Request From IP: '$ipAddress' Received.\nSending response data $json\n-----------")
        return  json
      }
    }
  }

  @PostMapping("/debug-log")
  fun requestLogHandlerDebug(@RequestBody debugMsg: String?, httpRequest: HttpServletRequest): String {
    val ipAddress = getClientIP(httpRequest)
    val pathInfo = httpRequest.pathInfo
    println("Debug Msg Received: $debugMsg from ip: $ipAddress")
    return "debug-log received"
  }
  @PostMapping("/regular-log")
  fun requestLogHandlerRegular(@RequestBody debugMsg: String?, httpRequest: HttpServletRequest): String {
    val ipAddress = getClientIP(httpRequest)
    val pathInfo = httpRequest.pathInfo
    println("regular Msg Received: $debugMsg from ip: $ipAddress")
    return "regular-log received"
  }
  @PostMapping( "/error-log")
  fun requestLogHandlerError(@RequestBody debugMsg: String?, httpRequest: HttpServletRequest): String {
    val ipAddress = getClientIP(httpRequest)
    val pathInfo = httpRequest.pathInfo
    println("error Msg Received: $debugMsg from ip: $ipAddress")
    return "error-log received"
  }
  @PostMapping()
  fun requestLogHandler(@RequestBody debugMsg: String?, httpRequest: HttpServletRequest): String {
    val ipAddress = getClientIP(httpRequest)
    val pathInfo = httpRequest.pathInfo
    println("Msg Received: $debugMsg from ip: $ipAddress")
    return "msg-log received"
  }
  private fun getClientIP(request: HttpServletRequest): String {
    val xForwardedForHeader = request.getHeader("X-FORWARDED-FOR")
    return if (xForwardedForHeader == null || xForwardedForHeader.isEmpty()) {
      request.remoteAddr
    } else {
      //right now we dont have any reverse proxy server. when we will use reverse proxy then below code needed
      //todo: As it's a comma separated list, get first IP
      xForwardedForHeader.split(",")[0] // 0 is not necessary. here filter logic needed
    }
  }
}