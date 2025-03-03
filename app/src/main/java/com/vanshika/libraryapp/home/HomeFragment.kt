package com.vanshika.libraryapp.home

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
import com.vanshika.libraryapp.MainActivity
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), BooksClickInterface, CategoryClickInterface {
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentHomeBinding ?= null
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var linearLayoutManagerCategory: LinearLayoutManager
    var booksList = arrayListOf<BooksDataClass>()
    var categoryList = arrayListOf<CategoryDataClass>()
    lateinit var booksAdapter: BooksAdapter
    lateinit var categoryAdapter: BooksCategoryAdapter
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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        booksAdapter = BooksAdapter(booksList,this)
        linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.rvBooks?.layoutManager = linearLayoutManager
        binding?.rvBooks?.adapter = booksAdapter
        getBooksAccToCategory()

        linearLayoutManagerCategory = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvCategory?.layoutManager = linearLayoutManagerCategory
        categoryAdapter = BooksCategoryAdapter(categoryList, this)
        binding?.rvCategory?.adapter = categoryAdapter
        getCategoryList()
    }

    private fun getCategoryList() {
        categoryList.clear()
        categoryList.add(CategoryDataClass(-1,"All"))
        categoryList.addAll(libraryDatabase.libraryDao().getCategory())
        categoryAdapter.notifyDataSetChanged()
    }

    private fun getBooksAccToCategory() {
        booksList.clear()
        booksList.addAll(libraryDatabase.libraryDao().getBooksAccToCategory())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun moveToNext(position: Int) {
        val convertToString = Gson().toJson(booksList[position])
        val selectedCategory = booksList[position].booksCategory
        val bundle = bundleOf(
            "booksId" to booksList[position].booksId,
            "noOfBooks" to convertToString,
            "booksCategory" to convertToString,
            "booksDescription" to convertToString,
            "selectedCategory" to selectedCategory
        )
        findNavController().navigate(R.id.bookSpecificationStudentFragment, bundle)
    }

    override fun onItemClick(position: Int) {
        booksList.clear()
        if (categoryList[position].categoryId == -1){
            booksList.addAll(libraryDatabase.libraryDao().getBooksAccToCategory())
        }else {
            booksList.addAll(libraryDatabase.libraryDao().getHomeBooksAccToCategory(categoryList[position].categoryName.toString()))
        }
        booksAdapter.notifyDataSetChanged()
        categoryAdapter.updatePosition(position)
    }
}