package com.vanshika.libraryapp.books

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.CustomDialogReturnedBinding
import com.vanshika.libraryapp.databinding.FragmentAdminBooksBinding
import com.vanshika.libraryapp.home.BooksEditDeleteInterface
import com.vanshika.libraryapp.profile.StudentInformationDataClass
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminBooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminBooksFragment : Fragment(), BooksEditDeleteInterface, IsReturnedInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentAdminBooksBinding? = null
    lateinit var libraryDatabase: LibraryDatabase
    var issuedBooksDataClass = IssuedBooksDataClass()
    var issuedBooksList = arrayListOf<IssuedBooksDataClass>()
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var issuedBooksAdapter: IssuedBooksAdapter
    private var calendar = Calendar.getInstance()
    private var formatDate: String? = null
    private var dateFormat = SimpleDateFormat("dd/MMM/yyy")
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

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
        binding = FragmentAdminBooksBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        issuedBooksAdapter = IssuedBooksAdapter(issuedBooksList, this, this)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding?.rvIssuedBooks?.layoutManager = linearLayoutManager
        binding?.rvIssuedBooks?.adapter = issuedBooksAdapter
        getIssuedBooksList()

        val enroll = issuedBooksDataClass.enroll
        sharedPreferences = requireContext().getSharedPreferences(
            resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        sharedPreferences.edit().putString("enroll", enroll.toString()).apply()

        binding?.fabAdd?.setOnClickListener {
            findNavController().navigate(R.id.issuedBooksFragment)
        }

        binding?.llStudentSearch?.setOnClickListener {
            findNavController().navigate(R.id.searchStudentIssuedFragment)
        }
    }

    private fun getIssuedBooksList() {
        issuedBooksList.clear()
        issuedBooksList.addAll(libraryDatabase.libraryDao().getIssuedBooks())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminBooksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminBooksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun editBook(position: Int) {
        findNavController().navigate(
            R.id.updateIssuedBooksFragment,
            bundleOf("issueId" to issuedBooksList[position].issueId)
        )
    }

    override fun deleteBook(position: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(resources.getString(R.string.are_you_sure_you_want_to_delete_this_section))
            .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                libraryDatabase.libraryDao().deleteIssuedBooks(issuedBooksList[position])
                issuedBooksAdapter.notifyDataSetChanged()
                getIssuedBooksList()
            }
            .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun isReturned(position: Int, isReturned: Boolean) {
        val graduationDays = 14
        val masterDoctorateDays = 21

        Dialog(requireContext()).apply {
            val dialogBinding = CustomDialogReturnedBinding.inflate(layoutInflater)
            setContentView(dialogBinding.root)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            show()
            setCancelable(false)

            val enrollmentType = when (issuedBooksList[position].enroll) {
                0 -> resources.getString(R.string.graduation)
                1 -> resources.getString(R.string.master)
                2 -> resources.getString(R.string.doctorate)
                else -> ""
            }
            dialogBinding.tvEnroll.text = enrollmentType

            val maxDays = if (enrollmentType == resources.getString(R.string.graduation)) {
                graduationDays
            } else {
                masterDoctorateDays
            }

            dialogBinding.etExpectedReturnDate.setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                    requireContext(), { _, year, month, date ->
                        calendar = Calendar.getInstance()
                        calendar.set(year, month, date)
                        formatDate = dateFormat.format(calendar.time)
                        dialogBinding.etExpectedReturnDate.setText(formatDate)
                    }, Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DATE)
                )
                datePickerDialog.show()
            }

            dialogBinding.etActualReturnDate.setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                    requireContext(), { _, year, month, date ->
                        calendar = Calendar.getInstance()
                        calendar.set(year, month, date)
                        formatDate = dateFormat.format(calendar.time)
                        dialogBinding.etActualReturnDate.setText(formatDate)
                    }, Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DATE)
                )
                datePickerDialog.show()
            }

            dialogBinding.btnOk.setOnClickListener {
                if (dialogBinding.etExpectedReturnDate.text.toString().isEmpty()) {
                    dialogBinding.etExpectedReturnDate.error =
                        resources.getString(R.string.enter_return_date)
                } else if (dialogBinding.etActualReturnDate.text.toString().isEmpty()) {
                    dialogBinding.etActualReturnDate.error =
                        resources.getString(R.string.enter_return_date)
                } else {
                    val sdf = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
                    val expectedReturnDate =
                        sdf.parse(dialogBinding.etExpectedReturnDate.text.toString())
                    val actualReturnDate =
                        sdf.parse(dialogBinding.etActualReturnDate.text.toString())
                    if (actualReturnDate != null && expectedReturnDate != null) {
                        val daysDifference =
                            (actualReturnDate.time - expectedReturnDate.time) / (1000 * 60 * 60 * 24)
                        if (daysDifference > maxDays) {
                            dialogBinding.etActualReturnDate.setTextColor(Color.RED)
                            val extraDays = daysDifference - maxDays
                            val fineAmount = extraDays * 10
                            libraryDatabase.libraryDao().insertStudentData(
                                StudentInformationDataClass(
                                    fineAmount = fineAmount.toString().toInt()
                                )
                            )
                        } else {
                            dialogBinding.etActualReturnDate.setTextColor(Color.BLACK)
                        }
                    }
                    val bookName = issuedBooksList[position].bookName ?: ""
                    issuedBooksList[position].isReturned = true
                    libraryDatabase.libraryDao().updateBooksStatus(bookName, 0)
                    libraryDatabase.libraryDao().updateIssuedBooks(issuedBooksList[position])
                    issuedBooksAdapter.notifyItemChanged(position)
                    dismiss()
                }
            }
        }
    }
}