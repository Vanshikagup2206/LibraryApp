package com.vanshika.libraryapp.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.MainActivity
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentBooksAccordingToCategoryBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BooksAccordingToCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksAccordingToCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var binding: FragmentBooksAccordingToCategoryBinding ?= null
    var booksDataClass = BooksDataClass()
    lateinit var libraryDatabase: LibraryDatabase
    var booksId = 0
    private var param1: String? = null
    private var param2: String? = null
    private val selectedImageUris = mutableListOf<String>()
    private lateinit var imagesAdapter : ImageAdapter

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
        binding = FragmentBooksAccordingToCategoryBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libraryDatabase = LibraryDatabase.getInstance(requireContext())

        imagesAdapter = ImageAdapter(selectedImageUris)
        binding?.rvSelectedImages?.adapter = imagesAdapter
        binding?.rvSelectedImages?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        arguments?.let {
            booksId = it.getInt("booksId",0)
            if (booksId>0){
                getBooksAccToIdList()
            }
        }

        val pickImagesLauncher =
            registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
                if (uris.isNotEmpty()) {
                    selectedImageUris.clear()
                    selectedImageUris.addAll(uris.map { it.toString() }) // Convert to string URIs
                    imagesAdapter.notifyDataSetChanged() // Notify adapter for preview update
                }
            }

        binding?.btnPickImages?.setOnClickListener {
            pickImagesLauncher.launch("image/*")
        }


        binding?.btnAddBookAccToCategory?.setOnClickListener {
            if (binding?.etBooksCategory?.text?.toString()?.trim()?.isEmpty() == true) {
                binding?.etBooksCategory?.error = resources.getString(R.string.enter_category)
            } else if (binding?.etAboutBooksCategory?.text?.toString()?.trim()?.isEmpty() == true) {
                binding?.etAboutBooksCategory?.error = resources.getString(R.string.enter_description)
            } else if (binding?.etNoOfBooks?.text?.toString()?.trim()?.isEmpty() == true) {
                binding?.etNoOfBooks?.error = resources.getString(R.string.enter_no_of_books_in_this_category)}
            else {
                libraryDatabase.libraryDao().insertBooksWithCategory(
                    BooksDataClass(
                        booksCategory = binding?.etBooksCategory?.text?.toString(),
                        booksAbout = binding?.etAboutBooksCategory?.text?.toString(),
                        noOfBooks = binding?.etNoOfBooks?.text?.toString()?.toInt()
                    )
                )
                findNavController().popBackStack()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BooksAccordingToCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BooksAccordingToCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun getBooksAccToIdList(){
        booksDataClass = libraryDatabase.libraryDao().getBooksAccToId(booksId)
    }
}