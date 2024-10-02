package com.vanshika.libraryapp.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryDataClass(
    @PrimaryKey(autoGenerate = true)
    var categoryId: Int = 0,
    var categoryName: String? = "",
    var categoryDescription: String? = "",
    var totalBooks: Int? = 0
) {
    override fun toString(): String {
        return "$categoryName"
    }
}
