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
    var booksDescriptionId : Int = 0,
    var booksStatus : String ?= "",
    var booksDescription : String ?= "",
    var isWishlist : Boolean ?= false
)
