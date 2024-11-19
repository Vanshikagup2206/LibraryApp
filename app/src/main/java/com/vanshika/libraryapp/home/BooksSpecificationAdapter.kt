package com.vanshika.libraryapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.libraryapp.R

class BooksSpecificationAdapter(var booksSpecificationList:ArrayList<BooksSpecificationDataClass>,
var booksClickInterface: BooksClickInterface, var booksEditDeleteInterface: BooksEditDeleteInterface):
    RecyclerView.Adapter<BooksSpecificationAdapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvBooksAuthorName: TextView = view.findViewById(R.id.tvBooksAuthorName)
        var tvBookName: TextView = view.findViewById(R.id.tvBookName)
        var tvBooksStatus: TextView = view.findViewById(R.id.tvBooksStatus)
        var tvBooksDescription: TextView = view.findViewById(R.id.tvBooksDescription)
        var tvBooksPublisher : TextView = view.findViewById(R.id.tvPublisher)
        var tvNoOfBooks : TextView = view.findViewById(R.id.tvNoOfBooks)
        var tvBriefDescription : TextView = view.findViewById(R.id.tvBriefDescription)
        var tvBooksTable : TextView = view.findViewById(R.id.tvTableOfContent)
        var tvBooksReleaseDate : TextView = view.findViewById(R.id.tvReleaseDate)
        var tvLanguage : TextView = view.findViewById(R.id.tvLanguage)
        var tvEdit : TextView = view.findViewById(R.id.tvEdit)
        var tvDelete : TextView = view.findViewById(R.id.tvDelete)
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
        holder.tvBooksAuthorName.text = booksSpecificationList[position].booksAuthorName
        holder.tvBookName.text = booksSpecificationList[position].booksName
        holder.tvBooksDescription.text = booksSpecificationList[position].booksDescription
        holder.tvBooksPublisher.text = booksSpecificationList[position].booksPublisher
        holder.tvNoOfBooks.text = booksSpecificationList[position].noOfBooks.toString()
        holder.tvBriefDescription.text = booksSpecificationList[position].booksBriefDescription
        holder.tvBooksTable.text = booksSpecificationList[position].booksTable
        holder.tvBooksReleaseDate.text = booksSpecificationList[position].booksReleaseDate
        holder.tvLanguage.text = booksSpecificationList[position].bookLanguage

        holder.tvEdit.setOnClickListener {
            booksEditDeleteInterface.editBook(position)
        }

        holder.tvDelete.setOnClickListener {
            booksEditDeleteInterface.deleteBook(position)
        }

        holder.tvBooksDescription.setOnClickListener {
            booksClickInterface.moveToNext(position)
        }
        holder.tvBookName.setOnClickListener {
            booksClickInterface.moveToNext(position)
        }

        when (booksSpecificationList[position].booksStatus) {

        0->{
            holder.tvBooksStatus.setText(R.string.available)
        }
          1->{
                holder.tvBooksStatus.setText(R.string.issued)
            }
        }
    }
}