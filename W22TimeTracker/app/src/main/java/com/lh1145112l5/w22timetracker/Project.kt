package com.lh1145112l5.w22timetracker

data class Project (
    var projectName: String? = null,
    var description: String? = null,
    var id: String? = null,
    var uID: String? = null,
    var timeRecords : ArrayList<TimeRecord>? = null
        ) {
    override
    fun toString() : String {
        if (projectName != null)
            return projectName!!
        else
            return "undefined"
    }

    fun addTimeRecord(newRecord : TimeRecord) {
        timeRecords!!.add(newRecord)
    }
}