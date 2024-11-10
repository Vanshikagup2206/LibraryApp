package com.vanshika.libraryapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
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
class BooksSpecificationFragment : Fragment(),BooksClickInterface {
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
            var bookId = it.getInt("bookId",0)
            var noOfBooks = it.getString("noOfBooks")
            booksDataClass = Gson().fromJson(noOfBooks,booksDataClass::class.java)
            var booksCategory = it.getString("booksCategory")
            booksDataClass = Gson().fromJson(booksCategory,booksDataClass::class.java)
            var aboutBooks = it.getString("aboutBooks")
            booksDataClass = Gson().fromJson(aboutBooks,booksDataClass::class.java)
        }
        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        booksSpecificationAdapter = BooksSpecificationAdapter(booksSpecificationList,this)
        linearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

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
        booksSpecificationList.clear()
        booksSpecificationList.addAll(libraryDatabase.libraryDao().getBookSpecification())
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
        var convertToString = Gson().toJson(booksSpecificationList[position])
        findNavController().navigate(R.id.booksDescriptionFragment, bundleOf("bookSpecificationId" to booksSpecificationList[position].booksSpecificationId))
        findNavController().navigate(R.id.booksDescriptionFragment, bundleOf("authorName" to convertToString))
        findNavController().navigate(R.id.booksDescriptionFragment, bundleOf("bookName" to convertToString))
        findNavController().navigate(R.id.booksDescriptionFragment, bundleOf("bookStatus" to convertToString))
        findNavController().navigate(R.id.booksDescriptionFragment, bundleOf("bookPublisher" to convertToString))
        findNavController().navigate(R.id.booksDescriptionFragment, bundleOf("noOfCopies" to convertToString))
        findNavController().navigate(R.id.booksDescriptionFragment, bundleOf("releaseDate" to convertToString))
        findNavController().navigate(R.id.booksDescriptionFragment, bundleOf("bookDescription" to convertToString))
        findNavController().navigate(R.id.booksDescriptionFragment, bundleOf("tableOfContent" to convertToString))
    }
}