package com.vanshika.libraryapp.home

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentBooksUpdateBinding
import java.text.SimpleDateFormat
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BooksUpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksUpdateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentBooksUpdateBinding ?= null
    var booksSpecificationDataClass = BooksSpecificationDataClass()
    var booksDataClass = BooksDataClass()
    var booksSpecificationList = arrayListOf<BooksSpecificationDataClass>()
    lateinit var libraryDatabase: LibraryDatabase
    lateinit var booksSpecificationAdapter: ArrayAdapter<BooksSpecificationDataClass>
    var bookSpecificationId = 0
    var calendar = Calendar.getInstance()
    var formatDate : String ?= ""
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    private var selectedImageUri : Uri ?= null
    var dateFormat = SimpleDateFormat("dd/MMM/yyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            bookSpecificationId = it.getInt("bookSpecificationId",0)
            Log.e(TAG, "id: $bookSpecificationId", )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBooksUpdateBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val booksJson = it.getString("noOfBooks")
        }
        libraryDatabase = LibraryDatabase.getInstance(requireContext())

        sharedPreferences = requireContext().getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val selectedCategory = sharedPreferences.getString("selectedCategory","")
        binding?.tvBooksCategory?.text = selectedCategory
        getBooksList()

        binding?.etReleaseDate?.setOnClickListener {
            var datePickerDialog = DatePickerDialog(
                requireContext(), { _, year, month, date ->
                    calendar = Calendar.getInstance()
                    calendar.set(year, month, date)
                    formatDate = dateFormat.format(calendar.time)
                    binding?.etReleaseDate?.setText(formatDate)
                }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE)
            )
            datePickerDialog.show()
        }

        binding?.etBookAuthorName?.setText(booksSpecificationDataClass.booksAuthorName)
        binding?.etBookTitle?.setText(booksSpecificationDataClass.booksName)
        binding?.etPublisher?.setText(booksSpecificationDataClass.booksPublisher)
        binding?.etCopiesAvailable?.setText(booksSpecificationDataClass.noOfBooks.toString())
        binding?.etReleaseDate?.setText(booksSpecificationDataClass.booksReleaseDate)
        binding?.etShortDescription?.setText(booksSpecificationDataClass.booksDescription)
        binding?.etDescription?.setText(booksSpecificationDataClass.booksBriefDescription)
        binding?.etTableOfContent?.setText(booksSpecificationDataClass.booksTable)
        binding?.etBookLanguage?.setText(booksSpecificationDataClass.bookLanguage)
        binding?.etShelfNo?.setText(booksSpecificationDataClass.shelfNo)
        binding?.etBookNo?.setText(booksSpecificationDataClass.bookNo)
        binding?.etSrNo?.setText(booksSpecificationDataClass.booksSrNo.toString())
        binding?.etPageNo?.setText(booksSpecificationDataClass.booksPageNo.toString())

        binding?.ivBookPhoto?.let {imageView ->
            booksSpecificationDataClass.booksPhoto?.let { photoUri ->
                Glide.with(requireContext())
                    .load(photoUri)
                    .placeholder(R.drawable.empty)
                    .into(imageView)
            }
        }

        when(booksSpecificationDataClass.booksStatus){
            0 -> binding?.rbAvailable?.isChecked = true
            1 -> binding?.rbIssued?.isChecked = true
        }

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){uri: Uri? ->
            uri.let {
                selectedImageUri = it
                Glide.with(requireContext())
                    .load(it)
                    .into(binding?.ivBookPhoto!!)
            }
        }

        binding?.btnUpdateImage?.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding?.btnUpdate?.setOnClickListener {
            if (binding?.etBookAuthorName?.text?.toString()?.trim()?.isEmpty() == true){
                binding?.etBookAuthorName?.error = resources.getString(R.string.enter_author_name)
            }else if (binding?.etBookTitle?.text?.trim()?.isEmpty() == true) {
                binding?.etBookTitle?.error = resources.getString(R.string.enter_book_title)
            } else if (binding?.etPublisher?.text?.trim()?.isEmpty() == true) {
                binding?.etPublisher?.error = resources.getString(R.string.enter_publisher)
            } else if (binding?.etBookLanguage?.text?.trim()?.isEmpty() == true) {
                binding?.etBookLanguage?.error =
                    resources.getString(R.string.enter_language_of_book)
            } else if (binding?.etShortDescription?.text?.trim()?.isEmpty() == true) {
                binding?.etShortDescription?.error =
                    resources.getString(R.string.enter_short_description)
            } else if (binding?.etDescription?.text?.trim()?.isEmpty() == true) {
                binding?.etDescription?.error = resources.getString(R.string.enter_description)
            } else if (binding?.etSrNo?.text?.trim()?.isEmpty() == true) {
                binding?.etTableOfContent?.error =
                    resources.getString(R.string.enter_sr_no)
            }else if (binding?.etTableOfContent?.text?.trim()?.isEmpty() == true) {
                binding?.etTableOfContent?.error =
                    resources.getString(R.string.enter_the_table_content)
            } else if (binding?.etPageNo?.text?.trim()?.isEmpty() == true) {
                binding?.etTableOfContent?.error =
                    resources.getString(R.string.enter_page_no)
            }else if (binding?.etCopiesAvailable?.text?.trim()?.isEmpty() == true) {
                binding?.etCopiesAvailable?.error =
                    resources.getString(R.string.enter_copies_available)
            } else if (binding?.etReleaseDate?.text?.trim()?.isEmpty() == true) {
                binding?.etReleaseDate?.error = resources.getString(R.string.enter_date)
            } else if (binding?.rgStatus?.checkedRadioButtonId == -1) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.select_status),
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (binding?.etShelfNo?.text?.isEmpty() == true){
                binding?.etShelfNo?.error = resources.getString(R.string.enter_shelf_no)
            }
                else if (binding?.etBookNo?.text?.isEmpty() == true){
                    binding?.etBookNo?.error = resources.getString(R.string.enter_book_no)
            }
                else {
                var status = if (binding?.rbAvailable?.isChecked == true){
                    0
                }else{
                    1
                }
                libraryDatabase.libraryDao().updateBooksSpecification(
                    BooksSpecificationDataClass(
                        booksSpecificationId = bookSpecificationId,
                        booksAuthorName = binding?.etBookAuthorName?.text?.toString(),
                        booksName = binding?.etBookTitle?.text?.toString(),
                        booksPublisher = binding?.etPublisher?.text?.toString(),
                        bookLanguage = binding?.etBookLanguage?.text?.toString(),
                        booksDescription = binding?.etShortDescription?.text?.toString(),
                        booksBriefDescription = binding?.etDescription?.text?.toString(),
                        booksTable = binding?.etTableOfContent?.text?.toString(),
                        noOfBooks = binding?.etCopiesAvailable?.text?.toString()?.toInt(),
                        booksReleaseDate = binding?.etReleaseDate?.text?.toString(),
                        booksStatus = status,
                        booksPhoto = selectedImageUri?.toString(),
                        booksCategory = binding?.tvBooksCategory?.text?.toString(),
                        bookNo = binding?.etBookNo?.text?.toString(),
                        shelfNo = binding?.etShelfNo?.text?.toString(),
                        booksSrNo = binding?.etSrNo?.text?.toString()?.toInt(),
                        booksPageNo = binding?.etPageNo?.text?.toString()?.toInt()
                    )
                )
                findNavController().popBackStack()
            }
        }
    }

    private fun getBooksList() {
        booksSpecificationDataClass = libraryDatabase.libraryDao().getBooksAccToSpecificationId(bookSpecificationId)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BooksUpdateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BooksUpdateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}