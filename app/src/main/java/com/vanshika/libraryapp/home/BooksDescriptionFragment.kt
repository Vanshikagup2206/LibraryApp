package com.vanshika.libraryapp.home

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.vanshika.libraryapp.R
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
            var bookSpecificationId = it.getInt("bookSpecificationId",0)

            val booksJson = it.getString("authorName")
            val booksData = Gson().fromJson(booksJson, booksSpecificationDataClass::class.java)

            binding?.tvBooksAuthorName?.text = booksData.booksAuthorName
            binding?.tvBookTitle?.text = booksData.booksName
            binding?.tvPublisher?.text = booksData.booksPublisher
            binding?.tvNoOfBooks?.text = booksData.noOfBooks.toString()
            binding?.tvReleaseDate?.text = booksData.booksReleaseDate
            binding?.tvBooksDescription?.text = booksData.booksBriefDescription
            binding?.tvSrNo?.text = booksData.booksSrNo.toString()
            binding?.tvTableOfContent?.text = booksData.booksTable
            binding?.tvPageNo?.text = booksData.booksPageNo.toString()
            binding?.tvLanguage?.text = booksData.bookLanguage
            binding?.tvShelfNo?.text = booksData.shelfNo
            binding?.tvBookNo?.text = booksData.bookNo

            binding?.ivBookPhoto?.let { imageView ->
                booksData.booksPhoto?.let { photoUri ->
                    Glide.with(requireContext())
                        .load(photoUri)
                        .placeholder(R.drawable.empty)
                        .into(imageView)
                }
            }

            if(booksData.booksStatus == 0){
                binding?.tvBooksStatus?.text = resources.getString(R.string.available)
            }else{
                binding?.tvBooksStatus?.text = resources.getString(R.string.issued)
            }

        }
        binding?.llBack?.setOnClickListener {
            findNavController().popBackStack()
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