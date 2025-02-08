package com.vanshika.libraryapp.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.home.BooksEditDeleteInterface

class IssuedBooksAdapter(
    var issuedBooksList: ArrayList<IssuedBooksDataClass>, var booksEditDeleteInterface: BooksEditDeleteInterface, var isReturnedInterface: IsReturnedInterface
) : RecyclerView.Adapter<IssuedBooksAdapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvStudentName: TextView = view.findViewById(R.id.tvStudentName)
        var tvRegNo: TextView = view.findViewById(R.id.tvRegNo)
        var tvSemester: TextView = view.findViewById(R.id.tvSemester)
        var tvBookName: TextView = view.findViewById(R.id.tvBookName)
        var tvIssuedDate: TextView = view.findViewById(R.id.tvIssuedDate)
        var tvReturnDate: TextView = view.findViewById(R.id.tvReturnDate)
        var tvEnroll: TextView = view.findViewById(R.id.tvEnroll)
        var cbReturned: CheckBox = view.findViewById(R.id.cbReturned)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_issued_books, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return issuedBooksList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvStudentName.setText(issuedBooksList[position].studentName)
        holder.tvRegNo.setText(issuedBooksList[position].regNo.toString())
        holder.tvSemester.setText(issuedBooksList[position].semester.toString())
        holder.tvBookName.setText(issuedBooksList[position].bookName)
        holder.tvIssuedDate.setText(issuedBooksList[position].issueDate)
        holder.tvReturnDate.setText(issuedBooksList[position].returnDate)

        holder.itemView.setOnClickListener {
            booksEditDeleteInterface.editBook(position)
        }

        holder.itemView.setOnLongClickListener {
            booksEditDeleteInterface.deleteBook(position)
            return@setOnLongClickListener true
        }

        holder.cbReturned.setOnClickListener {
            isReturnedInterface.isReturned(position, holder.cbReturned.isChecked)
        }

        holder.cbReturned.isChecked = issuedBooksList[position].isReturned ?: false

        when (issuedBooksList[position].enroll) {
            0 -> {
                holder.tvEnroll.setText(R.string.graduation)
            }
            1 -> {
                holder.tvEnroll.setText(R.string.master)
            }
            3 -> {
                holder.tvEnroll.setText(R.string.doctorate)
            }
        }


    }
}