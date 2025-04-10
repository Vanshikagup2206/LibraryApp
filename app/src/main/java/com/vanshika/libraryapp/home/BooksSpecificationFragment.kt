package com.vanshika.libraryapp.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentBooksSpecificationBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BooksSpecificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksSpecificationFragment : Fragment(), BooksClickInterface, BooksEditDeleteInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentBooksSpecificationBinding? = null
    lateinit var linearLayoutManager: LinearLayoutManager
    var booksDataClass = BooksDataClass()
    var booksSpecificationList = arrayListOf<BooksSpecificationDataClass>()
    lateinit var libraryDatabase: LibraryDatabase
    lateinit var booksSpecificationAdapter: BooksSpecificationAdapter


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
        binding = FragmentBooksSpecificationBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val bookId = it.getInt("bookId", 0)

            val booksJson = it.getString("noOfBooks")
            val booksData = Gson().fromJson(booksJson, booksDataClass::class.java)

            binding?.tvNoOfBooks?.text =
                booksData.noOfBooks.toString()
            binding?.tvBooksCategory?.text = booksData.booksCategory
            binding?.tvBooksDescription?.text = booksData.booksAbout
        }

        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        booksSpecificationAdapter = BooksSpecificationAdapter(booksSpecificationList, this,this)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding?.rvBooksSpecification?.layoutManager = linearLayoutManager
        binding?.rvBooksSpecification?.adapter = booksSpecificationAdapter
        getBooksSpecificationList()


        binding?.ivBack?.setOnClickListener {
            findNavController().popBackStack()
        }
        binding?.btnFab?.setOnClickListener {
            findNavController().navigate(R.id.booksAdditionFragment)
        }
    }

    private fun getBooksSpecificationList() {
        val selectedCategory = arguments?.getString("selectedCategory")?:""
        booksSpecificationList.clear()
        booksSpecificationList.addAll(libraryDatabase.libraryDao().getBooksSpecificationAccToCategory(selectedCategory))
        booksSpecificationAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BooksSpecificationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BooksSpecificationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun moveToNext(position: Int) {
        val convertToString = Gson().toJson(booksSpecificationList[position])
        val bundle = bundleOf(
            "bookSpecificationId" to booksSpecificationList[position].booksSpecificationId,
            "authorName" to convertToString,
            "bookTitle" to convertToString,
            "bookStatus" to convertToString,
            "bookPublisher" to convertToString,
            "noOfBooks" to convertToString,
            "releaseDate" to convertToString,
            "bookDescription" to convertToString,
            "srNo" to convertToString,
            "tableOfContent" to convertToString,
            "pageNo" to convertToString,
            "language" to convertToString,
            "bookPhoto" to convertToString,
            "shelfNo" to convertToString,
            "bookNo" to convertToString
        )
        findNavController().navigate(
            R.id.booksDescriptionFragment, bundle)
    }

    override fun editBook(position: Int) {
        findNavController().navigate(R.id.booksUpdateFragment, bundleOf("bookSpecificationId" to booksSpecificationList[position].booksSpecificationId))
    }

    override fun deleteBook(position: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(resources.getString(R.string.are_you_sure_you_want_to_delete_this_section))
            .setPositiveButton(resources.getString(R.string.yes)){_,_ ->
                libraryDatabase.libraryDao().deleteBooksSpecification(booksSpecificationList[position])
                getBooksSpecificationList()
            }
            .setNegativeButton(resources.getString(R.string.no)){dialog,_ ->
                dialog.dismiss()
            }
            .show()
    }
}