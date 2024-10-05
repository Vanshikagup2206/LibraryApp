package com.vanshika.libraryapp.home

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = BooksDataClass :: class,
        parentColumns = ["booksId"],
        childColumns = ["booksId"]
    )
])
data class BooksSpecificationDataClass(
    @PrimaryKey(autoGenerate = true)
    var booksSpecificationId : Int = 0,
    var booksName : String ?= "",
    var booksAuthor : String ?= "",
    var booksDescription : String ?= "",
    var booksStatus : Int ?= 0,
    var isWishlist : Boolean ?= false,
    var booksId : Int ?= 0
)
