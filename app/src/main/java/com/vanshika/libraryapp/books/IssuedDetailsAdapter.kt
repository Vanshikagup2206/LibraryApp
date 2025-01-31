package com.vanshika.libraryapp.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.libraryapp.R

class IssuedDetailsAdapter(
    var issuedBooksList: ArrayList<IssuedBooksDataClass>
) : RecyclerView.Adapter<IssuedDetailsAdapter.ViewHolder>(){
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        var tvSrNo: TextView = view.findViewById(R.id.tvSrNo)
        var tvBookTitle: TextView = view.findViewById(R.id.tvBookTitle)
        var tvIssuedDate: TextView = view.findViewById(R.id.tvIssuedDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.student_issued_books_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return issuedBooksList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSrNo.text = (position+1).toString()
        holder.tvBookTitle.setText(issuedBooksList[position].bookName)
        holder.tvIssuedDate.setText(issuedBooksList[position].issueDate)
    }
}
