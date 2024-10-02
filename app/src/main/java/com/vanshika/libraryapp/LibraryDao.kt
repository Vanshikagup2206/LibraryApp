package com.vanshika.libraryapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vanshika.libraryapp.home.CategoryDataClass

@Dao
interface LibraryDao {
    @Insert
    fun insertCategory(categoryDataClass: CategoryDataClass)
   @Query(" SELECT * FROM CategoryDataClass")
   fun  getCategory():List<CategoryDataClass>
}