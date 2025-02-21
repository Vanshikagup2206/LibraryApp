package com.vanshika.libraryapp.books

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentBooksBinding
import com.vanshika.libraryapp.profile.StudentInformationDataClass

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentBooksBinding? = null
    lateinit var linearLayoutManager: LinearLayoutManager
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
        binding = FragmentBooksBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        issuedDetailsAdapter = IssuedDetailsAdapter(studentDataList)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding?.rvStudentIssuedBooks?.layoutManager = linearLayoutManager
        binding?.rvStudentIssuedBooks?.adapter = issuedDetailsAdapter

        val sharedPreferences = requireContext().getSharedPreferences(
            resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val enteredRegNo = sharedPreferences.getString("enteredRegNo", null)

        if (enteredRegNo.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Registration number not found!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        binding?.tvRegistrationNo?.text = enteredRegNo
        val studentDetails = libraryDatabase.libraryDao().getStudentRegNo(enteredRegNo.toInt())

        studentDetails.let {
            binding?.tvStudentName?.text = it.studentName
            binding?.ivStudentPhoto?.let { imageView ->
                it.studentPhoto?.let { photoUri ->
                    Glide.with(requireContext())
                        .load(photoUri)
                        .placeholder(R.drawable.empty)
                        .into(imageView)
                }
            }
        }

        binding?.llIssuedBooks?.setOnClickListener {
            getIssuedBooks()
        }

        binding?.llPreviouslyIssued?.setOnClickListener {
            getReturnedBooks()
        }
    }

    private fun getReturnedBooks() {
        val isReturned = true
        val sharedPreferences = requireContext().getSharedPreferences(
            resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val enteredRegNo = sharedPreferences.getString("enteredRegNo", null)
        studentDataList.clear()
        studentDataList.addAll(
            libraryDatabase.libraryDao()
                .getReturnedBooks(isReturned, enteredRegNo.toString().toInt())
        )
        issuedDetailsAdapter.notifyDataSetChanged()
    }

    private fun getIssuedBooks() {
        val isReturned = false
        val sharedPreferences = requireContext().getSharedPreferences(
            resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val enteredRegNo = sharedPreferences.getString("enteredRegNo", null)
        studentDataList.clear()
        studentDataList.addAll(
            libraryDatabase.libraryDao()
                .getReturnedBooks(isReturned, enteredRegNo.toString().toInt())
        )
        issuedDetailsAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BooksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BooksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}