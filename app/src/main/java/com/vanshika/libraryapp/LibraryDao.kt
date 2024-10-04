package com.vanshika.libraryapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vanshika.libraryapp.home.BooksDataClass
import com.vanshika.libraryapp.home.CategoryDataClass

@Dao
interface LibraryDao {
    @Insert
    fun insertBooksWithCategory(booksDataClass: BooksDataClass)


    @Query(" SELECT * FROM BooksDataClass")
    fun getBooksAccToCategory(): List<BooksDataClass>

    @Delete
    fun deleteBooksWithCategory(booksDataClass: BooksDataClass)

    @Update
    fun updateBooksWithCategory(booksDataClass: BooksDataClass)
}