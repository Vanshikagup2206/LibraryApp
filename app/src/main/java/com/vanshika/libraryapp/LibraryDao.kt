package com.vanshika.libraryapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vanshika.libraryapp.home.BooksDataClass
import com.vanshika.libraryapp.home.BooksSpecificationDataClass
import com.vanshika.libraryapp.home.CategoryDataClass

@Dao
interface LibraryDao {
    // Queries for Books Category
    @Insert
    fun insertBooksWithCategory(booksDataClass: BooksDataClass)

    @Query(" SELECT * FROM BooksDataClass")
    fun getBooksAccToCategory(): List<BooksDataClass>

    @Delete
    fun deleteBooksWithCategory(booksDataClass: BooksDataClass)

    @Update
    fun updateBooksWithCategory(booksDataClass: BooksDataClass)

    // Queries for Books Specification
    @Insert
    fun insertBooksSpecification(booksSpecificationDataClass: BooksSpecificationDataClass)

    @Query("SELECT * FROM BooksSpecificationDataClass")
    fun getBookSpecification(): List<BooksSpecificationDataClass>

    @Query("SELECT * FROM BooksDataClass WHERE booksId =:booksId")
    fun getBooksAccToId(booksId : Int): BooksDataClass

    @Update
    fun updateBooksSpecification(booksSpecificationDataClass: BooksSpecificationDataClass)

    @Delete
    fun deleteBooksSpecification(booksSpecificationDataClass: BooksSpecificationDataClass)

}