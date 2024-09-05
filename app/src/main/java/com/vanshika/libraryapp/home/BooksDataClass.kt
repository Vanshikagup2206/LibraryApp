package com.vanshika.libraryapp.home

import android.provider.ContactsContract.Contacts.Photo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BooksDataClass(
    @PrimaryKey(autoGenerate = true)
    var BooksId : Int = 0,
    var categoryId : Int ?= 0,
    var BooksType : String ?= "",
//    var BooksPhotos : Photo ?= ""
)
