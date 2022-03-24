package com.lh1145112l5.w22timetracker

import com.google.firebase.Timestamp

class TimeRecord (
    var activity: String? = null,
    var startTime: Timestamp? = null,
    var endTime: Timestamp? = null
        ) {
    /**
     * This method will ensure that the TimeRecord has both a startTime and an EndTime
     * then calculate the amount of minutes between the start and end times
     */
    fun getDuration() : Long {
        if (startTime != null && endTime != null) {
            val difference = endTime!!.toDate().time - startTime!!.toDate().time
            return difference/1000/60
        }
        return 0
    }
}