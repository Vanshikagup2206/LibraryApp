package com.vanshika.libraryapp.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentSearchStudentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchStudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchStudentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentSearchStudentBinding ?= null
    var studentList = arrayListOf<StudentInformationDataClass>()
    lateinit var studentDataAdapter: ArrayAdapter<StudentInformationDataClass>
    lateinit var libraryDatabase: LibraryDatabase

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
        binding = FragmentSearchStudentBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        studentDataAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, studentList)
        binding?.lvStudentRegNo?.adapter = studentDataAdapter
        getStudentList()

        binding?.searchBar?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
        androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding?.searchBar?.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                studentDataAdapter.filter.filter(newText)
                return false
            }
        })
        binding?.lvStudentRegNo?.setOnItemClickListener { parent, view, position, id ->
            val selectedStudentRegNo = studentList[position].registrationNo
            val bundle = bundleOf(
                "studentId" to studentList[position].studentId,
                "selectedStudentRegNo" to selectedStudentRegNo
            )
            findNavController().navigate(R.id.searchedStudentFragment, bundle)
        }
    }

    private fun getStudentList() {
        studentList.clear()
        studentList.addAll(libraryDatabase.libraryDao().getStudentData())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchStudentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchStudentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}