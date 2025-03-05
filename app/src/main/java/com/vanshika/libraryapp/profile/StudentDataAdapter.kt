package com.vanshika.libraryapp.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.home.BooksClickInterface

class StudentDataAdapter (
    var studentDataList : ArrayList<StudentInformationDataClass>, var booksClickInterface: BooksClickInterface
        ) :
        RecyclerView.Adapter<StudentDataAdapter.ViewHolder>(){
            class ViewHolder(var view : View) : RecyclerView.ViewHolder(view){
                var tvStudentName : TextView = view.findViewById(R.id.tvStudentName)
                var tvRegistrationNo : TextView = view.findViewById(R.id.tvRegistrationNo)
                var tvDepartment : TextView = view.findViewById(R.id.tvDepartment)
                var tvMobileNo : TextView = view.findViewById(R.id.tvMobileNo)
                var ivStudentPhoto : ImageView = view.findViewById(R.id.ivStudentPhoto)
                var tvSemester : TextView = view.findViewById(R.id.tvSemester)
                var tvEnroll : TextView = view.findViewById(R.id.tvEnroll)
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(
            R.layout.admin_profile_items, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return studentDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvStudentName.text = studentDataList[position].studentName
        holder.tvRegistrationNo.text = studentDataList[position].registrationNo.toString()
        holder.tvDepartment.text = studentDataList[position].department
        holder.tvMobileNo.text = studentDataList[position].mobileNo.toString()
        holder.tvSemester.text = studentDataList[position].semester.toString()

        val studentPhoto = studentDataList[position].studentPhoto
        if (studentPhoto?.isNotEmpty() == true){
            Glide.with(holder.itemView.context)
                .load(studentDataList[position].studentPhoto)
                .placeholder(R.drawable.empty)
                .into(holder.ivStudentPhoto)
        }else{
            holder.ivStudentPhoto.setImageResource(R.drawable.empty)
        }

        holder.tvStudentName.setOnClickListener {
            booksClickInterface.moveToNext(position)
        }

        when (studentDataList[position].enroll){
            0 -> {
                holder.tvEnroll.setText(R.string.graduation)
            }
            1 ->{
                holder.tvEnroll.setText(R.string.master)
            }
            2 ->{
                holder.tvEnroll.setText(R.string.doctorate)
            }
        }
    }
}