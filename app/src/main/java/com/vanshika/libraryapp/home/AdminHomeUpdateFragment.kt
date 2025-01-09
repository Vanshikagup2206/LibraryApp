package com.vanshika.libraryapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentAdminHomeUpdateBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminHomeUpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminHomeUpdateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentAdminHomeUpdateBinding? = null
    lateinit var libraryDatabase: LibraryDatabase
    var booksDataClass = BooksDataClass()
    var booksList = arrayListOf<BooksDataClass>()
    var bookId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            bookId = it.getInt("bookId",0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomeUpdateBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        getBooksAccToIdList()

        binding?.etBooksCategory?.setText(booksDataClass.booksCategory)
        binding?.etAboutBooksCategory?.setText(booksDataClass.booksAbout)
        binding?.etNoOfBooks?.setText(booksDataClass.noOfBooks.toString())

        binding?.btnAddBookAccToCategory?.setOnClickListener {
            if (binding?.etBooksCategory?.text?.toString()?.trim()?.isEmpty() == true) {
                binding?.etBooksCategory?.error = resources.getString(R.string.enter_category)
            } else if (binding?.etAboutBooksCategory?.text?.toString()?.trim()?.isEmpty() == true) {
                binding?.etAboutBooksCategory?.error =
                    resources.getString(R.string.enter_description)
            } else if (binding?.etNoOfBooks?.text?.toString()?.trim()?.isEmpty() == true) {
                binding?.etNoOfBooks?.error =
                    resources.getString(R.string.enter_no_of_books_in_this_category)
            } else {
                libraryDatabase.libraryDao().updateBooksWithCategory(
                    BooksDataClass(
                        booksId = bookId,
                        booksCategory = binding?.etBooksCategory?.text.toString(),
                        booksAbout = binding?.etAboutBooksCategory?.text.toString(),
                        noOfBooks = binding?.etNoOfBooks?.text?.toString()?.toInt()
                    )
                )
                findNavController().popBackStack()
            }
            getBooksAccToIdList()
        }
    }
    fun getBooksAccToIdList(){
        booksDataClass = libraryDatabase.libraryDao().getBooksAccToId(bookId)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminHomeUpdateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminHomeUpdateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}