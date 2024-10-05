package com.vanshika.libraryapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.libraryapp.R

class BooksSpecificationAdapter(var SpecifiedList:ArrayList<BooksSpecificationDataClass>):
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
        return SpecifiedList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.authorName.setText(SpecifiedList[position].booksAuthor)
        holder.bookName.setText(SpecifiedList[position].booksName)
        holder.status.setText(SpecifiedList[position].booksStatus)
        holder.description.setText(SpecifiedList[position].booksDescription)
    }
}