package com.vanshika.libraryapp.books

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IssuedBooksDataClass(
    @PrimaryKey(autoGenerate = true)
    var issueId : Int = 0,
    var studentName : String ?= "",
    var regNo : Int ?= 0,
    var semester : Int ?= 0,
    var bookName : String ?= "",
    var issueDate : String ?= "",
    var returnDate : String ?= "",
    var enroll : Int ?= 0
){
    override fun toString(): String {
        return "$regNo"
    }
}
