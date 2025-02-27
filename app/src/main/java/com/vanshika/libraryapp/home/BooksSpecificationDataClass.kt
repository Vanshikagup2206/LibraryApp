package com.vanshika.libraryapp.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BooksSpecificationDataClass(
    @PrimaryKey(autoGenerate = true)
    var booksSpecificationId : Int = 0,
    var booksId : Int ?= 0,
    var booksName : String ?= "",
    var booksAuthorName : String ?= "",
    var booksDescription : String ?= "",
    var booksStatus : Int ?= 0,
    var booksPublisher : String ?= "",
    var noOfBooks : Int ?= 0,
    var booksBriefDescription : String ?="",
    var booksSrNo : Int ?= 0,
    var booksTable : String ?= "",
    var booksPageNo : Int ?= 0,
    var booksReleaseDate : String ?= "",
    var bookLanguage : String ?= "",
    var booksCategory : String ?="",
    var isWishlist : Boolean ?= false,
    var booksPhoto: String ?= null,
    var shelfNo: String ?= "",
    var bookNo : String ?= ""
){
    override fun toString(): String {
        return "$booksName"
    }
}

