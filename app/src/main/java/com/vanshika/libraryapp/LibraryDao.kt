package com.vanshika.libraryapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vanshika.libraryapp.home.BooksDataClass
import com.vanshika.libraryapp.home.BooksSpecificationDataClass
import com.vanshika.libraryapp.home.CategoryDataClass
import com.vanshika.libraryapp.profile.StudentInformationDataClass

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

    // Queries for category
    @Query("SELECT * FROM CategoryDataClass")
    fun getCategory() : List<CategoryDataClass>

    @Insert
    fun insertBookCategory(categoryDataClass: CategoryDataClass)

    @Query("SELECT * FROM BooksDataClass WHERE categoryName =:categoryName")
    fun getHomeBooksAccToCategory(categoryName: String) : List<BooksDataClass>

    // Queries for Books Specification
    @Insert
    fun insertBooksSpecification(booksSpecificationDataClass: BooksSpecificationDataClass)

    @Query("SELECT * FROM BooksSpecificationDataClass")
    fun getBookSpecification(): List<BooksSpecificationDataClass>

    @Query("SELECT * FROM BooksDataClass WHERE booksId =:booksId")
    fun getBooksAccToId(booksId : Int): BooksDataClass

    @Query("SELECT * FROM BooksSpecificationDataClass WHERE booksSpecificationId =:booksSpecificationId")
    fun getBooksAccToSpecificationId(booksSpecificationId : Int): BooksSpecificationDataClass

    @Query("SELECT * FROM BooksSpecificationDataClass WHERE booksName =:booksName")
    fun getSearchedBooks(booksName : String): List<BooksSpecificationDataClass>

    @Query("SELECT * FROM BooksSpecificationDataClass WHERE booksCategory =:booksCategory")
    fun getBooksSpecificationAccToCategory(booksCategory : String): List<BooksSpecificationDataClass>

    @Update
    fun updateBooksSpecification(booksSpecificationDataClass: BooksSpecificationDataClass)

    @Delete
    fun deleteBooksSpecification(booksSpecificationDataClass: BooksSpecificationDataClass)

    // Queries for wishlist

    @Query("SELECT *FROM BooksSpecificationDataClass WHERE isWishlist =:isWishlist")
    fun getWishlistBooks(isWishlist : Boolean): List<BooksSpecificationDataClass>

    // Queries for student data

    @Insert
    fun insertStudentData(studentInformationDataClass: StudentInformationDataClass)

    @Query("SELECT * FROM StudentInformationDataClass")
    fun getStudentData() : List<StudentInformationDataClass>

    @Delete
    fun deleteStudentData(studentInformationDataClass: StudentInformationDataClass)

    @Update
    fun updateStudentData(studentInformationDataClass: StudentInformationDataClass)

    @Query("SELECT * FROM StudentInformationDataClass WHERE studentId =:studentId")
    fun getStudentDataAccToId(studentId : Int): StudentInformationDataClass

    @Query("SELECT * FROM StudentInformationDataClass WHERE registrationNo =:registrationNo")
    fun getSearchedStudent(registrationNo : Int) : List<StudentInformationDataClass>

}