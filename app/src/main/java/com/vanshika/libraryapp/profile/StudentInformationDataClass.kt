package com.vanshika.libraryapp.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudentInformationDataClass(
    @PrimaryKey(autoGenerate = true)
    var studentId : Int = 0,
    var studentName : String ?= "",
    var registrationNo : Int ?= 0,
    var department : String ?= "",
    var mobileNo : String ?= "",
    var studentPhoto : String ?= null,
    var semester : Int ?= null,
    var enroll : Int ?= 0,
    var fineAmount : Int ?= 0
){
    override fun toString(): String {
        return registrationNo.toString()
    }
}
