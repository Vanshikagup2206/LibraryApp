package com.vanshika.libraryapp.books

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentUpdateIssuedBooksBinding
import java.text.SimpleDateFormat
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateIssuedBooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateIssuedBooksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentUpdateIssuedBooksBinding ?= null
    var issuedBooksDataClass = IssuedBooksDataClass()
    var issuedBooksList = arrayListOf<IssuedBooksDataClass>()
    lateinit var libraryDatabase: LibraryDatabase
    var issueId = 0
    var calendar = Calendar.getInstance()
    var formatDate : String ?=""
    var dateFormat = SimpleDateFormat("dd/MMM/yyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            issueId = it.getInt("issueId", 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateIssuedBooksBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        getIssuedBooksList()

        binding?.etIssueDate?.setOnClickListener {
            var datePickerDialog = DatePickerDialog(
                requireContext(),{_, year, month, date ->
                    calendar = Calendar.getInstance()
                    calendar.set(year, month, date)
                    formatDate = dateFormat.format(calendar.time)
                    binding?.etIssueDate?.setText(formatDate)
                }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE)
            )
            datePickerDialog.show()
        }

        binding?.etReturnDate?.setOnClickListener {
            var datePickerDialog = DatePickerDialog(
                requireContext(),{_, year, month, date ->
                    calendar = Calendar.getInstance()
                    calendar.set(year, month, date)
                    formatDate = dateFormat.format(calendar.time)
                    binding?.etReturnDate?.setText(formatDate)
                }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE)
            )
            datePickerDialog.show()
        }

        binding?.etStudentName?.setText(issuedBooksDataClass.studentName)
        binding?.etRegistrationNo?.setText(issuedBooksDataClass.regNo.toString())
        binding?.etSemester?.setText(issuedBooksDataClass.semester.toString())
        binding?.etBookName?.setText(issuedBooksDataClass.bookName)
        binding?.etIssueDate?.setText(issuedBooksDataClass.issueDate)
        binding?.etReturnDate?.setText(issuedBooksDataClass.returnDate)
        when(issuedBooksDataClass.enroll){
            0 -> binding?.rbGraduation?.isChecked = true
            1 -> binding?.rbMaster?.isChecked = true
            2 -> binding?.rbDoctorate?.isChecked = true
        }

        binding?.btnUpdate?.setOnClickListener {
            if (binding?.etStudentName?.text?.isEmpty() == true) {
                binding?.etStudentName?.error = resources.getString(R.string.enter_student_name)
            } else if (binding?.etRegistrationNo?.text?.isEmpty() == true) {
                binding?.etRegistrationNo?.error =
                    resources.getString(R.string.enter_your_registration_no)
            } else if (binding?.etSemester?.text?.isEmpty() == true) {
                binding?.etSemester?.error = resources.getString(R.string.enter_semester)
            } else if (binding?.etBookName?.text?.isEmpty() == true) {
                binding?.etBookName?.error = resources.getString(R.string.enter_book_title)
            } else if (binding?.etIssueDate?.text?.isEmpty() == true) {
                binding?.etIssueDate?.error = resources.getString(R.string.enter_issue_date)
            } else if (binding?.etReturnDate?.text?.isEmpty() == true) {
                binding?.etReturnDate?.error = resources.getString(R.string.enter_return_date)
            } else if (binding?.rgEnroll?.checkedRadioButtonId == -1){
                Toast.makeText(requireContext(), resources.getString(R.string.select_one), Toast.LENGTH_SHORT).show()
            }else {
                val enroll = if (binding?.rbGraduation?.isChecked == true) {
                    0
                } else if (binding?.rbMaster?.isChecked == true){
                    1
                }else{
                    2
                }
                libraryDatabase.libraryDao().updateIssuedBooks(
                    IssuedBooksDataClass(
                        issueId = issueId,
                        studentName = binding?.etStudentName?.text?.toString(),
                        regNo = binding?.etRegistrationNo?.text?.toString()?.toInt(),
                        semester = binding?.etSemester?.text?.toString()?.toInt(),
                        bookName = binding?.etBookName?.text?.toString(),
                        issueDate = binding?.etIssueDate?.text?.toString(),
                        returnDate = binding?.etReturnDate?.text?.toString(),
                        enroll = enroll
                    )
                )
                findNavController().popBackStack()
            }
        }
    }

    private fun getIssuedBooksList() {
        issuedBooksDataClass = libraryDatabase.libraryDao().getIssuedBooksAccToId(issueId)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateIssuedBooksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateIssuedBooksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}