package com.vanshika.libraryapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vanshika.libraryapp.home.BooksDataClass
import com.vanshika.libraryapp.home.CategoryDataClass

@Dao
interface LibraryDao {
    @Insert
    fun insertBooksWithCategory(booksDataClass: BooksDataClass)

   @Query(" SELECT * FROM BooksDataClass")
   fun  getBooksAccToCategory():List<BooksDataClass>
}