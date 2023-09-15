package com.elymer.TimeSyncServer

import com.elymer.TimeSyncServer.Support.Companion.deserialize
import com.elymer.TimeSyncServer.Support.Companion.serialize
import jakarta.servlet.http.HttpServletRequest
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import java.util.Base64

//@RestController
//@Validated
//class TestController {
////  Salt (Base64): iISrh2TndBRv+tJPZ21jVA==
////  Salt (Base64): 906m2S/uqTp8IwzkFky2TA==
//
//  val requestKeyBase64 = "vyu8a6xKA5ULqrhT52fIs2H4eBLSuidVNHuao9pN6Ag="  // orgKey:  "rVWhV5IWIv3V4IN5"
//  val responseKeyBase64 = "ttHA3Iv+9DOYwY1tiUG+5CAFlyA5pAZIWOnq+0Fw5wI=" // orgKey: "Bjr+XND1GLQAgF5F"
//
//  @PostMapping("/x1wr0d60") //Read
//  fun writeHandler(@RequestBody encryptedData: String): String? {
//    return AESUtilAdvanced.decrypt(encryptedData, requestKeyBase64).let { decryptedData ->
//      decryptedData.deserialize<Instruction.Request>()?.let { request ->
//        //do your other checks and tasks
//        //...
//        //...
//        takeIf { request.key == "ReadFiles" }?.let {
//          val path = "\\\\172.16.16.14\\Material\\connectedIps.jsn"//connectedIps
//          Instruction.Response(request.key,  TextFile(path).readAllText()).serialize().let {
//            val str = AESUtilAdvanced.encrypt(it, responseKeyBase64)
//            println(str)
//            str
//          }
//        }
//      }
//    }
//  }
//  @PostMapping()
//  fun getRequestHandler(@RequestBody data:String, httpRequest: HttpServletRequest): String {
//    val ipAddress = getClientIP(httpRequest)
//    println(data)
//    return "Welcome to What is My IP!   Your Ip: $ipAddress"
//  }
//  private fun getClientIP(request: HttpServletRequest): String {
//    val xForwardedForHeader = request.getHeader("X-FORWARDED-FOR")
//    return if (xForwardedForHeader == null || xForwardedForHeader.isEmpty()) {
//      request.remoteAddr
//    } else {
//      //right now we dont have any reverse proxy server. when we will use reverse proxy then below code needed
//      //todo: As it's a comma separated list, get first IP
//      xForwardedForHeader.split(",")[0] // 0 is not necessary. here filter logic needed
//    }
//  }
//}