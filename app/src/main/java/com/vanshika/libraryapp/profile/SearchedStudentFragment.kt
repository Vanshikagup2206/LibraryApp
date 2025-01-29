package com.vanshika.libraryapp.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentSearchedStudentBinding
import com.vanshika.libraryapp.home.BooksClickInterface

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchedStudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchedStudentFragment : Fragment(), BooksClickInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentSearchedStudentBinding?= null
    lateinit var linearLayoutManager: LinearLayoutManager
    var studentInformationDataClass = StudentInformationDataClass()
    var studentList = arrayListOf<StudentInformationDataClass>()
    lateinit var libraryDatabase: LibraryDatabase
    lateinit var studentDataAdapter: StudentDataAdapter

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
        binding = FragmentSearchedStudentBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        studentDataAdapter = StudentDataAdapter(studentList,this)
        linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding?.rvStudent?.layoutManager = linearLayoutManager
        binding?.rvStudent?.adapter = studentDataAdapter
        getStudentData()

        binding?.ivBack?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getStudentData() {
        val selectedStudentRegNo = arguments?.getInt("selectedStudentRegNo") ?: ""
        studentList.clear()
        studentList.addAll(libraryDatabase.libraryDao().getSearchedStudent(selectedStudentRegNo.toString().toInt()))
        studentDataAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchedStudentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchedStudentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun moveToNext(position: Int) {
        findNavController().navigate(R.id.studentAllRecordFragment)
    }
}