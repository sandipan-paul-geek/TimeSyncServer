package com.elymer.TimeSyncServer

import com.elymer.TimeSyncServer.Support.Companion.serialize
import jakarta.servlet.http.HttpServletRequest
import kotlinx.serialization.Serializable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

@RestController
@Validated
class TimeSyncController {
  @GetMapping()
  fun getRequestHandler(httpRequest: HttpServletRequest): String {
    val ipAddress = getClientIP(httpRequest)
    val pathInfo = httpRequest.pathInfo
    return "Welcome to What is My IP!   Your Ip: $ipAddress"
  }

  @GetMapping("/time")
  fun requestTimeSync(httpRequest: HttpServletRequest): String {
    val ipAddress = getClientIP(httpRequest)
    Time1970().getSecondsFrom1970().toLong().let { secondsFrom1970 ->
      val dt = Time1970().getDateFromSeconds(secondsFrom1970.toInt())
      DateTimeInfo(secondsFrom1970, 60000).serialize().let { json ->
        println("SyncTime Request From IP: '$ipAddress' Received.\nSending response data $json\n-----------")
        return  json
      }
    }
  }

  @PostMapping("/debug-log")
  fun requestLogHandler_debug(@RequestBody debugMsg: String?, httpRequest: HttpServletRequest): String {
    val ipAddress = getClientIP(httpRequest)
    val pathInfo = httpRequest.pathInfo
    println("Debug Msg Received: $debugMsg from ip: $ipAddress")
    return "debug-log received"
  }
  @PostMapping("/regular-log")
  fun requestLogHandler_regular(@RequestBody debugMsg: String?, httpRequest: HttpServletRequest): String {
    val ipAddress = getClientIP(httpRequest)
    val pathInfo = httpRequest.pathInfo
    println("regular Msg Received: $debugMsg from ip: $ipAddress")
    return "regular-log received"
  }
  @PostMapping( "/error-log")
  fun requestLogHandler_error(@RequestBody debugMsg: String?, httpRequest: HttpServletRequest): String {
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
//  @PostMapping("/error-log")
//  fun requesErrorLog(errorMsg: String, httpRequest: HttpServletRequest): String {
//    val ipAddress = getClientIP(httpRequest)
//    println("Debug Msg Received: $errorMsg from ip: $ipAddress")
//    return "No Error Info Received"
//  }


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