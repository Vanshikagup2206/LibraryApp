package com.vanshika.libraryapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.vanshika.libraryapp.databinding.FragmentBooksDescriptionBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BooksDescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksDescriptionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding : FragmentBooksDescriptionBinding ?= null
    private var booksSpecificationDataClass = BooksSpecificationDataClass()

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
        binding = FragmentBooksDescriptionBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            var bookId = it.getInt("bookSpecificationId",0)
            val authorName = it.getString("authorName")
            booksSpecificationDataClass = Gson().fromJson(authorName,booksSpecificationDataClass::class.java)
            val bookTitle = it.getString("bookTitle")
            booksSpecificationDataClass = Gson().fromJson(bookTitle,booksSpecificationDataClass::class.java)
            val bookStatus = it.getString("bookStatus")
            booksSpecificationDataClass = Gson().fromJson(bookStatus,booksSpecificationDataClass::class.java)
            val bookPublisher = it.getString("bookPublisher")
            booksSpecificationDataClass = Gson().fromJson(bookPublisher,booksSpecificationDataClass::class.java)
            val noOfBooks = it.getString("noOfBooks")
            booksSpecificationDataClass = Gson().fromJson(noOfBooks,booksSpecificationDataClass::class.java)
            val releaseDate = it.getString("releaseDate")
            booksSpecificationDataClass = Gson().fromJson(releaseDate,booksSpecificationDataClass::class.java)
            val bookDescription = it.getString("bookDescription")
            booksSpecificationDataClass = Gson().fromJson(bookDescription,booksSpecificationDataClass::class.java)
            val tableOfContent = it.getString("tableOfContent")
            booksSpecificationDataClass = Gson().fromJson(tableOfContent,booksSpecificationDataClass::class.java)

            binding?.tvBookTitle?.text = bookTitle
            binding?.tvBooksAuthorName?.text = authorName
            binding?.tvBooksStatus?.text = bookStatus
            binding?.tvPublisher?.text = bookPublisher
            binding?.tvNoOfBooks?.text = noOfBooks
            binding?.tvReleaseDate?.text = releaseDate
            binding?.tvBooksDescription?.text = bookDescription
            binding?.tvTableOfContent?.text = tableOfContent
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BooksDescriptionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BooksDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}