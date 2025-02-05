package com.vanshika.libraryapp.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentWishlistBinding
import com.vanshika.libraryapp.home.BooksClickInterface
import com.vanshika.libraryapp.home.BooksDataClass
import com.vanshika.libraryapp.home.BooksSpecificationAdapter
import com.vanshika.libraryapp.home.BooksSpecificationDataClass

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WishlistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WishlistFragment : Fragment(), BooksClickInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentWishlistBinding? = null
    lateinit var linearLayoutManager: LinearLayoutManager
    var booksDataClass = BooksDataClass()
    var booksSpecificationDataClass = BooksSpecificationDataClass()
    var booksSpecificationList = arrayListOf<BooksSpecificationDataClass>()
    lateinit var libraryDatabase: LibraryDatabase
    lateinit var wishlistAdapter: WishlistAdapter

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
        binding = FragmentWishlistBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        wishlistAdapter = WishlistAdapter(booksSpecificationList, this)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding?.rvWishlist?.layoutManager = linearLayoutManager
        binding?.rvWishlist?.adapter = wishlistAdapter

        getWishlistBooks()
    }

    private fun getWishlistBooks() {
        val isWishlist = true
        booksSpecificationList.clear()
        booksSpecificationList.addAll(libraryDatabase.libraryDao().getWishlistBooks(isWishlist))
        wishlistAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WishlistFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WishlistFragment().apply {
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
            "tableOfContent" to convertToString,
            "language" to convertToString,
            "bookPhoto" to convertToString
        )
        findNavController().navigate(
            R.id.booksDescriptionStudentFragment, bundle
        )
    }
}