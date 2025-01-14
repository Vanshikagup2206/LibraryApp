package com.vanshika.libraryapp.home

import android.provider.ContactsContract.Contacts.Photo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BooksDataClass(
    @PrimaryKey(autoGenerate = true)
    var booksId : Int = 0,
    var categoryId : Int ?= 0,
    var categoryName : String ?= "",
    var booksAbout : String ?= "",
    var booksCategory : String ?= "",
    var noOfBooks : Int ?= 0,
)
