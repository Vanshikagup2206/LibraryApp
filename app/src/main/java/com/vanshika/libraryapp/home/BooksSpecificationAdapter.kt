package com.vanshika.libraryapp.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BooksSpecificationAdapter(var SpecifiedList:ArrayList<BooksSpecificationDataClass>):
    RecyclerView.Adapter<BooksSpecificationAdapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}