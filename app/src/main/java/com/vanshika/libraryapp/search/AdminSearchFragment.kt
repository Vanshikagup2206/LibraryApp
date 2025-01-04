package com.vanshika.libraryapp.search

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
import com.vanshika.libraryapp.databinding.FragmentAdminSearchBinding
import com.vanshika.libraryapp.home.BooksSpecificationDataClass

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminSearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentAdminSearchBinding ?= null
    var books = arrayListOf<BooksSpecificationDataClass>()
    lateinit var booksSpecificationAdapter: ArrayAdapter<BooksSpecificationDataClass>
    lateinit var libraryDatabase: LibraryDatabase
    var booksSpecificationDataClass = BooksSpecificationDataClass()

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
        binding = FragmentAdminSearchBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        booksSpecificationAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, books)
        binding?.lvBookName?.adapter = booksSpecificationAdapter
        getBooksName()

        binding?.searchBar?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding?.searchBar?.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                booksSpecificationAdapter.filter.filter(newText)
                return false
            }

        })
        binding?.lvBookName?.setOnItemClickListener { parent, view, position, id ->
            val selectedBook = books[position].booksName
            val bundle = bundleOf(
                "bookSpecificationId" to books[position].booksSpecificationId,
                "selectedBook" to selectedBook
            )
            findNavController().navigate(R.id.searchedBooksFragment, bundle)
        }
    }

    private fun getBooksName() {
        books.clear()
        books.addAll(libraryDatabase.libraryDao().getBookSpecification())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminSearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

