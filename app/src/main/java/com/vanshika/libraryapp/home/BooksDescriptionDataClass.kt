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
data class BooksDescriptionDataClass(
    @PrimaryKey(autoGenerate = true)
    var bookAuthor: String ?= "",
    var booksDescriptionId : Int = 0,
    var booksName : String ?= "",
    var booksContent : String ?= "",
    var booksLocation : String ?= "",
    var booksCopies : String ?= "",
    var booksStatus : String ?= "",
    var booksDescription : String ?= "",
    var isWishlist : Boolean ?= false,
    var booksId : Int ?= 0
)
