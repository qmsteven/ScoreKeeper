package com.lh1145112a1.assignment1

class Score (
    var scoreName : String? = null,
    var description : String? = null,
    var id : String? = null,
    var uID : String? = null
) {
    override fun toString(): String {
        if (scoreName != null)
            return scoreName!!
        else
            return "undefined"
    }
}