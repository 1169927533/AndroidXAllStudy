package com.example.a11699.comp_chat.util
import java.text.SimpleDateFormat
import java.util.*

/**
 *Create time 2020/5/27
 *Create Yu
 */
class DataUtilKotlin {
    companion object {
        fun getNowTime(): String {
            val time = System.currentTimeMillis()
            val date = Date(time)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            return dateFormat.format(date)
        }

        fun getNowTime_HOUR(): String {
            val time = System.currentTimeMillis()
            val date = Date(time)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            return dateFormat.format(date)
        }

        fun ComPare_K(nowTime: String, headTime: String, isYeasterDay: (Date, Date) -> Int): Int {
            val format = SimpleDateFormat("yyyy-MM-dd")
            var date1: Date? = null
            var date2: Date? = null
            try {
                date1 = format.parse(nowTime)
                date2 = format.parse(headTime)

            } catch (e: Exception) {

            }
            return isYeasterDay.invoke(date1!!, date2!!)
        }
    }
}
