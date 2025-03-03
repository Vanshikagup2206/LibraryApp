package com.vanshika.libraryapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.wishlist.IsWishlistInterface

class BooksSpecificationStudentAdapter(
    var booksSpecificationList : ArrayList<BooksSpecificationDataClass>,
    var booksClickInterface: BooksClickInterface,
    var isWishlistInterface: IsWishlistInterface
) : RecyclerView.Adapter<BooksSpecificationStudentAdapter.ViewHolder>(){
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view){
        var tvBooksAuthorName: TextView = view.findViewById(R.id.tvBooksAuthorName)
        var tvBookName: TextView = view.findViewById(R.id.tvBookName)
        var tvBooksStatus: TextView = view.findViewById(R.id.tvBooksStatus)
        var tvBooksDescription: TextView = view.findViewById(R.id.tvBooksDescription)
        var tvBooksPublisher: TextView = view.findViewById(R.id.tvPublisher)
        var tvNoOfBooks: TextView = view.findViewById(R.id.tvNoOfBooks)
        var tvBriefDescription: TextView = view.findViewById(R.id.tvBriefDescription)
        var tvSrNo: TextView = view.findViewById(R.id.tvSrNo)
        var tvBooksTable: TextView = view.findViewById(R.id.tvTableOfContent)
        var tvPageNo: TextView = view.findViewById(R.id.tvPageNo)
        var tvBooksReleaseDate: TextView = view.findViewById(R.id.tvReleaseDate)
        var tvLanguage: TextView = view.findViewById(R.id.tvLanguage)
        var cbWishlist: CheckBox = view.findViewById(R.id.cbWishlist)
        var ivBookPhoto: ImageView = view.findViewById(R.id.ivBookPhoto)
        var tvBookNo : TextView = view.findViewById(R.id.tvBookNo)
        var tvShelfNo : TextView = view.findViewById(R.id.tvShelfNo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_books_specification_student
            , parent, false
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
        holder.tvSrNo.text = booksSpecificationList[position].booksSrNo.toString()
        holder.tvBooksTable.text = booksSpecificationList[position].booksTable
        holder.tvPageNo.text = booksSpecificationList[position].booksPageNo.toString()
        holder.tvBooksReleaseDate.text = booksSpecificationList[position].booksReleaseDate
        holder.tvLanguage.text = booksSpecificationList[position].bookLanguage
        holder.tvShelfNo.text = booksSpecificationList[position].shelfNo
        holder.tvShelfNo.text = booksSpecificationList[position].shelfNo

        holder.tvBooksDescription.setOnClickListener {
            booksClickInterface.moveToNext(position)
        }
        holder.tvBookName.setOnClickListener {
            booksClickInterface.moveToNext(position)
        }

        val bookPhoto = booksSpecificationList[position].booksPhoto
        if (bookPhoto?.isNotEmpty() == true){
            Glide.with(holder.itemView.context)
                .load(booksSpecificationList[position].booksPhoto)
                .placeholder(R.drawable.empty)
                .into(holder.ivBookPhoto)
        }

        holder.cbWishlist.setOnClickListener {
            isWishlistInterface.isWishlist(position, holder.cbWishlist.isChecked)
        }

        holder.cbWishlist.isChecked = booksSpecificationList[position].isWishlist ?: false

        when (booksSpecificationList[position].booksStatus) {
            0 -> {
                holder.tvBooksStatus.setText(R.string.available)
            }
            1 -> {
                holder.tvBooksStatus.setText(R.string.issued)
            }
        }
    }
}