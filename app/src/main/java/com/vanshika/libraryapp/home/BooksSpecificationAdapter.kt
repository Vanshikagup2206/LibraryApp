package com.vanshika.libraryapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.libraryapp.R

class BooksSpecificationAdapter(var booksSpecificationList:ArrayList<BooksSpecificationDataClass>,
var booksClickInterface: BooksClickInterface):
    RecyclerView.Adapter<BooksSpecificationAdapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvBooksAuthorName: TextView = view.findViewById(R.id.tvBooksAuthorName)
        var tvBookName: TextView = view.findViewById(R.id.tvBookName)
        var tvBooksStatus: TextView = view.findViewById(R.id.tvBooksStatus)
        var tvBooksDescription: TextView = view.findViewById(R.id.tvBooksDescription)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_books_specification,
            parent, false
        )
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return booksSpecificationList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvBooksAuthorName.setText(booksSpecificationList[position].booksAuthorName)
        holder.tvBookName.setText(booksSpecificationList[position].booksName)
        holder.tvBooksDescription.setText(booksSpecificationList[position].booksDescription)
        holder.tvBooksDescription.setOnClickListener {
            booksClickInterface.moveToNext(position)
        }
        holder.tvBookName.setOnClickListener {
            booksClickInterface.moveToNext(position)
        }

//        when (specifiedList[position].booksStatus) {
//            0 -> {
//            }
//            1->{
//            }
//        }
    }
}