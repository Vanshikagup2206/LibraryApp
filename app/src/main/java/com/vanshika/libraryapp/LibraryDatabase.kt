package com.vanshika.libraryapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vanshika.libraryapp.books.IssuedBooksDataClass
import com.vanshika.libraryapp.home.BooksDataClass
import com.vanshika.libraryapp.home.BooksDescriptionDataClass
import com.vanshika.libraryapp.home.BooksSpecificationDataClass
import com.vanshika.libraryapp.home.CategoryDataClass
import com.vanshika.libraryapp.profile.StudentInformationDataClass

@Database(
    entities = [BooksDataClass::class, BooksDescriptionDataClass::class, BooksSpecificationDataClass::class, CategoryDataClass::class, StudentInformationDataClass::class, IssuedBooksDataClass::class],
    version = 1,
    exportSchema = true
)
abstract class LibraryDatabase : RoomDatabase() {
    abstract fun libraryDao(): LibraryDao

    companion object {
        private var libraryDatabase: LibraryDatabase? = null
        fun getInstance(context: Context): LibraryDatabase {
            if (libraryDatabase == null) {
                libraryDatabase =
                    Room.databaseBuilder(context, LibraryDatabase::class.java, "LibraryDatabase")
                        .allowMainThreadQueries().build()
            }
            return libraryDatabase!!
        }
    }
}