package com.vanshika.libraryapp.home

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//@Entity(foreignKeys = [
//    ForeignKey(
//        entity = BooksDataClass :: class,
//        parentColumns = ["booksId"],
//        childColumns = ["booksSpecificationId"],
//        onDelete = ForeignKey.CASCADE
//    )
//])
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
    var booksTable : String ?= "",
    var booksReleaseDate : String ?= "",
    var bookLanguage : String ?= "",
    var booksCategory : String ?="",
    var isWishlist : String ?= ""
){
    override fun toString(): String {
        return "$booksName"
    }
}

