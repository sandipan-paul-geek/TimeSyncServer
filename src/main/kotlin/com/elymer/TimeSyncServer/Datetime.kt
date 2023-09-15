package com.elymer.TimeSyncServer

import java.text.SimpleDateFormat
import java.util.*

class Datetime(val date: Date) {
  companion object {
    internal fun Now(): Datetime {
      return Datetime()
    }

    internal fun String.tryParseToDateTime(format: String): Datetime? {
      SimpleDateFormat(format).parse(this)?.let { date ->
        return Datetime(date)
      } ?: return null
    }

    internal fun valueOf(stringDate: String, format: String): Datetime? {
      return stringDate.tryParseToDateTime(format)
    }
  }

  constructor(milliSec: Long) : this(Date(milliSec))
  constructor() : this(Date())

  fun getMillis(): Long {
    return date.time
  }

  internal fun addDays(days: Int): Datetime {
    val newTimeInMillis = this.date.time + (days * 86400 * 1000)
    val newDate = Date(newTimeInMillis)
    return Datetime(newDate)
  }

  internal fun minusDays(days: Int): Datetime {
    val newTimeInMillis = this.date.time - (days * 86400 * 1000)
    val newDate = Date(newTimeInMillis)
    return Datetime(newDate)
  }

  internal fun addSeconds(seconds: Int): Datetime {
    val newTimeInMillis = this.date.time + (seconds * 1000)
    val newDate = Date(newTimeInMillis)
    return Datetime(newDate)
  }

  internal fun isSmallerThan(dateTime: Datetime): Boolean {
    val s = this.date
    val b = dateTime.date
    val bool = s.before(b)
    if (bool) {
      print(b)
    }
    return bool
    //return this.date.before( dateTime.date)
  }

  internal fun isGreaterThan(dateTime: Datetime): Boolean {
    val bool = this.date.after(dateTime.date)
    return bool
  }

  fun string(format: String = Format.SystemFormat12Hr): String {
    return SimpleDateFormat(format, Locale.ENGLISH).format(this.date)
  }

  class Format {
    companion object {
      //"2022-07-08 14:42:06"
      internal const val TrackDataFormat24Hr = "yyyy-MM-dd hh:mm:ss"
      internal const val SimpleDate = "dd-MM-yyyy"
      internal const val SystemFormat12Hr = "dd-MM-yyyy hh:mm:ss a"
    }
  }
}