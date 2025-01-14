package com.vanshika.libraryapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.vanshika.libraryapp.R

class BooksCategoryAdapter(
    var categoryList: ArrayList<CategoryDataClass>,
    var categoryClickInterface: CategoryClickInterface
) : RecyclerView.Adapter<BooksCategoryAdapter.ViewHolder>() {
    var selectedPosition = 0

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var btnCategory: MaterialButton = view.findViewById(R.id.btnCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btnCategory.setText(categoryList[position].categoryName)
        holder.btnCategory.setOnClickListener {
            categoryClickInterface.onItemClick(position)
        }
    }

    fun updatePosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }
}