package com.vanshika.libraryapp.books

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentAdminBooksBinding
import com.vanshika.libraryapp.home.BooksEditDeleteInterface

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminBooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminBooksFragment : Fragment(), BooksEditDeleteInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentAdminBooksBinding?= null
    lateinit var libraryDatabase: LibraryDatabase
    var issuedBooksDataClass = IssuedBooksDataClass()
    var issuedBooksList = arrayListOf<IssuedBooksDataClass>()
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var issuedBooksAdapter : IssuedBooksAdapter

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
        binding = FragmentAdminBooksBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        issuedBooksAdapter = IssuedBooksAdapter(issuedBooksList,this)
        linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding?.rvIssuedBooks?.layoutManager = linearLayoutManager
        binding?.rvIssuedBooks?.adapter = issuedBooksAdapter
        getIssuedBooksList()

        binding?.fabAdd?.setOnClickListener {
            findNavController().navigate(R.id.issuedBooksFragment)
        }

        binding?.llStudentSearch?.setOnClickListener {
            findNavController().navigate(R.id.searchStudentFragment)
        }
    }

    private fun getIssuedBooksList() {
        issuedBooksList.clear()
        issuedBooksList.addAll(libraryDatabase.libraryDao().getIssuedBooks())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminBooksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminBooksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun editBook(position: Int) {
        findNavController().navigate(R.id.updateIssuedBooksFragment, bundleOf("issueId" to issuedBooksList[position].issueId))
    }

    override fun deleteBook(position: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(resources.getString(R.string.are_you_sure_you_want_to_delete_this_section))
            .setPositiveButton(resources.getString(R.string.yes)){_,_ ->
                libraryDatabase.libraryDao().deleteIssuedBooks(issuedBooksList[position])
                issuedBooksAdapter.notifyDataSetChanged()
                getIssuedBooksList()
            }
            .setNegativeButton(resources.getString(R.string.no)){dialog,_ ->
                dialog.dismiss()
            }
            .show()
    }
}