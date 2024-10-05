package com.vanshika.libraryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanshika.libraryapp.databinding.FragmentBooksAdditionBinding
import com.vanshika.libraryapp.home.BooksAdapter
import com.vanshika.libraryapp.home.BooksDataClass
import com.vanshika.libraryapp.home.BooksSpecificationAdapter
import com.vanshika.libraryapp.home.BooksSpecificationDataClass

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BooksAdditionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksAdditionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentBooksAdditionBinding ?= null
    lateinit var linearLayoutManager: LinearLayoutManager
    var specifiedList = arrayListOf<BooksSpecificationDataClass>()
    lateinit var booksSpecificationAdapter: BooksSpecificationAdapter
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
        // Inflate the layout for this fragment
        binding = FragmentBooksAdditionBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnAdd?.setOnClickListener {
            if(binding?.etBookAuthorName?.text?.isEmpty() == true){
                binding?.etBookAuthorName?.error = resources.getString(R.string.enter_author_name)
            }
            else if(binding?.etBookTitle?.text?.isNullOrEmpty() == true){
                binding?.etBookTitle?.error = resources.getString(R.string.enter_book_title)
            }
            else if(binding?.etPublisher?.text?.isNullOrEmpty() == true){
                binding?.etPublisher?.error = resources.getString(R.string.enter_publisher)
            }
            else if(binding?.etShortDescription?.text?.isNullOrEmpty() == true){
                binding?.etShortDescription?.error = resources.getString(R.string.enter_short_description)
            }
            else if(binding?.etDescription?.text?.isNullOrEmpty() == true){
                binding?.etDescription?.error = resources.getString(R.string.enter_description)
            }
            else if(binding?.etTableOfContent?.text?.isNullOrEmpty() == true){
                binding?.etTableOfContent?.error = resources.getString(R.string.enter_the_table_content)
            }
            else if(binding?.etCopiesAvailable?.text?.isNullOrEmpty() == true){
                binding?.etCopiesAvailable?.error = resources.getString(R.string.enter_copies_available)
            }
            else if(binding?.etReleaseDate?.text?.isNullOrEmpty() == true){
                binding?.etReleaseDate?.error = resources.getString(R.string.enter_date)
            }
            else if (binding?.rgStatus?.checkedRadioButtonId == -1) {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.select_status),
                        Toast.LENGTH_SHORT
                    ).show()
            }
            else{
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
         * @return A new instance of fragment BooksAdditionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BooksAdditionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}