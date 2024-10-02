package com.vanshika.libraryapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.libraryapp.R

class NewCategoryadapter(
    var categoryList: ArrayList<CategoryDataClass>
) : RecyclerView.Adapter<NewCategoryadapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvCategory = view.findViewById<TextView>(R.id.tvCategory)
        var tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        var tvTotalBooks = view.findViewById<TextView>(R.id.tvTotalBooks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCategory.setText(categoryList[position].categoryName)
        holder.tvDescription.setText(categoryList[position].categoryDescription)
        holder.tvTotalBooks.setText(categoryList[position].totalBooks.toString())
    }
}