package com.vanshika.libraryapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.libraryapp.R

class BooksSpecificationAdapter(var specifiedList:ArrayList<BooksSpecificationDataClass>):
    RecyclerView.Adapter<BooksSpecificationAdapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var authorName: TextView = view.findViewById(R.id.author_name)
        var bookName: TextView = view.findViewById(R.id.book_name)
        var status: TextView = view.findViewById(R.id.status)
        var description: TextView = view.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_books_specification,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return specifiedList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.authorName.setText(specifiedList[position].booksAuthor)
        holder.bookName.setText(specifiedList[position].booksName)
        holder.description.setText(specifiedList[position].booksDescription)
        when (specifiedList[position].booksStatus) {
            0 -> {
            }
            1->{
            }
        }
    }
}