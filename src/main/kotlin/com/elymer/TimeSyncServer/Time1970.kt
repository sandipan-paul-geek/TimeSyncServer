package com.elymer.TimeSyncServer

import java.util.*

class Time1970
{
  fun getDaysFrom1970(): Int
  {
    return ((Calendar.getInstance().timeInMillis + Calendar.getInstance().timeZone.rawOffset) / 1000 / 86400).toInt()
  }

  fun getSecondsFrom1970(): Int
  {
    return ((Calendar.getInstance().timeInMillis + Calendar.getInstance().timeZone.rawOffset) / 1000).toInt()
  }

  fun getDaysFrom(year: Int, month: Int, day: Int): Int
  {
    Calendar.getInstance().also { calendar ->
      calendar.set(year, month, day)
      return ((calendar.timeInMillis + Calendar.getInstance().timeZone.rawOffset) / 1000 / 86400).toInt()
    }
  }

  fun getSecondsFrom(year: Int, month: Int, day: Int): Int
  {
    Calendar.getInstance().also { calendar ->
      calendar.set(year, month, day)
      return ((calendar.timeInMillis + Calendar.getInstance().timeZone.rawOffset) / 1000).toInt()
    }
  }
  fun getSecondsFrom(year: Int, month: Int, day: Int, hour:Int, min:Int, seconds:Int): Int
  {
    Calendar.getInstance().also { calendar ->
      calendar.set(year, month, day, hour, min, seconds)
      return ((calendar.timeInMillis) / 1000).toInt()
    }
  }

  fun getDate(days: Int): Datetime
  {
    val date = Date(days.toLong() * 1000 * 86400 - Calendar.getInstance().timeZone.rawOffset)
    return Datetime(date)
  }

  fun getDateFromSeconds(seconds: Int): Datetime
  {
    val date = Date(seconds.toLong() * 1000 - Calendar.getInstance().timeZone.rawOffset)
    return Datetime(date)
  }

  fun getDateTimeFromSeconds(seconds: Int): Datetime
  {
    val date = Date(seconds.toLong() * 1000 - Calendar.getInstance().timeZone.rawOffset)
    return Datetime(date)
  }
  fun Datetime.toString(format: String? = null):String
  {
    return format?.let { this.string(it) }?: this.string()
  }
}