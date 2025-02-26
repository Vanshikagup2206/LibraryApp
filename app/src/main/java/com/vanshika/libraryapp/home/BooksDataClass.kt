package com.vanshika.libraryapp.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BooksDataClass(
    @PrimaryKey(autoGenerate = true)
    var booksId : Int = 0,
    var categoryId : Int ?= 0,
    var booksAbout : String ?= "",
    var booksCategory : String ?= "",
    var noOfBooks : Int ?= 0,
)
