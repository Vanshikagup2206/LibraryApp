package com.vanshika.libraryapp.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.books.IssuedBooksAdapter
import com.vanshika.libraryapp.books.IssuedBooksDataClass
import com.vanshika.libraryapp.books.IssuedDetailsAdapter
import com.vanshika.libraryapp.databinding.FragmentStudentAllRecordBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudentsAllRecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentsAllRecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentStudentAllRecordBinding? = null
    lateinit var linearLayoutManager: LinearLayoutManager
    var issuedBooksDataClass = IssuedBooksDataClass()
    var studentInformationDataClass = StudentInformationDataClass()
    var studentDataList = arrayListOf<IssuedBooksDataClass>()
    lateinit var libraryDatabase: LibraryDatabase
    lateinit var issuedDetailsAdapter: IssuedDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentAllRecordBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val studentId = it.getInt("studentId", 0)

            val booksJson = it.getString("studentName")
            val booksData = Gson().fromJson(booksJson, studentInformationDataClass::class.java)

            binding?.tvStudentName?.text = booksData.studentName
            binding?.tvRegistrationNo?.text = booksData.registrationNo.toString()
            binding?.ivStudentPhoto?.let { imageView ->
                booksData.studentPhoto?.let { photoUri ->
                    Glide.with(requireContext())
                        .load(photoUri)
                        .placeholder(R.drawable.empty)
                        .into(imageView)
                }
            }
        }

        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        issuedDetailsAdapter = IssuedDetailsAdapter(studentDataList)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding?.rvStudentIssuedBooks?.layoutManager = linearLayoutManager
        binding?.rvStudentIssuedBooks?.adapter = issuedDetailsAdapter

        binding?.llIssuedBooks?.setOnClickListener {
            getIssuedBooks()
        }

        binding?.llPreviouslyIssued?.setOnClickListener {
            getReturnedBooks()
        }
    }

    private fun getReturnedBooks() {
        val isReturned = true
        val studentId = arguments?.getInt("studentId",0)
        studentDataList.clear()
        studentDataList.addAll(libraryDatabase.libraryDao().getReturnedBooks(isReturned,studentId.toString().toInt()))
        issuedDetailsAdapter.notifyDataSetChanged()
    }

    private fun getIssuedBooks() {
        val isReturned = false
        val studentId = arguments?.getInt("studentId",0)
        studentDataList.clear()
        studentDataList.addAll(libraryDatabase.libraryDao().getReturnedBooks(isReturned, studentId.toString().toInt()))
        issuedDetailsAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StudentsAllRecordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudentsAllRecordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}