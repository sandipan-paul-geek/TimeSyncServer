package com.elymer.TimeSyncServer

import kotlinx.serialization.Serializable

@Serializable
  class DateTimeInfo {
    companion object {
      const val INDIA_STANDARD_TIME = "India Standard Time"
    }

    var seconds: Long
    var timerDelay: Long
    var timeZone: String

    constructor() {
      this.seconds = 0
      this.timerDelay = 10000
      this.timeZone = INDIA_STANDARD_TIME
    }

    constructor(seconds: Long, timerDelay: Long ) {
      this.seconds = seconds
      this.timerDelay = timerDelay
      this.timeZone = INDIA_STANDARD_TIME
    }
  }