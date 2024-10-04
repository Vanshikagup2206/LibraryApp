package com.vanshika.libraryapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.libraryapp.R

class BooksAdapter(
    var booksList: ArrayList<BooksDataClass>,
    var onItemClick: booksInterface
) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvCategory: TextView = view.findViewById(R.id.tvBooksCategory)
        var tvDescription: TextView = view.findViewById(R.id.tvBooksAbout)
        var ivNext: ImageView = view.findViewById(R.id.ivNext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.books_with_category_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.tvCategory.setText(booksList[position].booksCategory)
//        holder.tvDescription.setText(booksList[position].booksAbout)
        val book = booksList[position]
        holder.tvCategory.text = book.booksCategory
        holder.tvDescription.text = book.booksAbout
//        holder.ivNext.setOnClickListener {
//            onItemClick.onItemclick(position)
//        }
    }
}