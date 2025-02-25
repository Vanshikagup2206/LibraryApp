package com.vanshika.libraryapp.books

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.CustomDialogReturnedBinding
import com.vanshika.libraryapp.databinding.FragmentSearchedStudentIssuedBinding
import com.vanshika.libraryapp.home.BooksEditDeleteInterface
import java.text.SimpleDateFormat
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchedStudentIssuedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchedStudentIssuedFragment : Fragment(), BooksEditDeleteInterface, IsReturnedInterface{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentSearchedStudentIssuedBinding?= null
    lateinit var libraryDatabase: LibraryDatabase
    lateinit var linearLayoutManager: LinearLayoutManager
    var studentList = arrayListOf<IssuedBooksDataClass>()
    lateinit var issuedBooksAdapter : IssuedBooksAdapter
    var calendar = Calendar.getInstance()
    var formatDate : String ?= null
    var dateFormat = SimpleDateFormat("dd/MMM/yyy")

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
        binding = FragmentSearchedStudentIssuedBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        issuedBooksAdapter = IssuedBooksAdapter(studentList, this, this)
        linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding?.rvStudent?.layoutManager = linearLayoutManager
        binding?.rvStudent?.adapter = issuedBooksAdapter
        getStudentData()

        binding?.ivBack?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getStudentData() {
        val selectedStudentRegNo = arguments?.getInt("selectedStudentRegNo") ?: ""
        studentList.clear()
        studentList.addAll(libraryDatabase.libraryDao().getIssuedBooksAccToRegNo(selectedStudentRegNo.toString().toInt()))
        issuedBooksAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchedStudentIssuedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchedStudentIssuedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun editBook(position: Int) {
        findNavController().navigate(R.id.updateIssuedBooksFragment, bundleOf("issueId" to studentList[position].issueId))
    }

    override fun deleteBook(position: Int) {
        AlertDialog.Builder(requireContext())
            .setMessage(resources.getString(R.string.are_you_sure_you_want_to_delete_this_section))
            .setPositiveButton(resources.getString(R.string.yes)){_,_ ->
                libraryDatabase.libraryDao().deleteIssuedBooks(studentList[position])
                issuedBooksAdapter.notifyDataSetChanged()
                getStudentData()
            }
            .setNegativeButton(resources.getString(R.string.no)){dialog,_ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun isReturned(position: Int, isReturned: Boolean) {
        Dialog(requireContext()).apply {
            val dialogBinding = CustomDialogReturnedBinding.inflate(layoutInflater)
            setContentView(dialogBinding.root)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            show()
            setCancelable(false)

            when(studentList[position].enroll){
                0 -> dialogBinding.tvEnroll.text = resources.getString(R.string.graduation)
                1 -> dialogBinding.tvEnroll.text = resources.getString(R.string.master)
                2 -> dialogBinding.tvEnroll.text = resources.getString(R.string.doctorate)
            }

            dialogBinding.etExpectedReturnDate.setOnClickListener {
                var datePickerDialog = DatePickerDialog(requireContext(), {_, year, month, date ->
                    calendar = Calendar.getInstance()
                    calendar.set(year, month, date)
                    formatDate = dateFormat.format(calendar.time)
                    dialogBinding.etExpectedReturnDate.setText(formatDate)
                }, Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DATE))
                datePickerDialog.show()
            }

            dialogBinding.etActualReturnDate.setOnClickListener {
                var datePickerDialog = DatePickerDialog(requireContext(), {_, year, month, date ->
                    calendar = Calendar.getInstance()
                    calendar.set(year, month, date)
                    formatDate = dateFormat.format(calendar.time)
                    dialogBinding.etActualReturnDate.setText(formatDate)
                }, Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DATE))
                datePickerDialog.show()
            }

            dialogBinding.btnOk.setOnClickListener {
                if (dialogBinding.etExpectedReturnDate.text.toString().isEmpty()){
                    dialogBinding.etExpectedReturnDate.error = resources.getString(R.string.enter_return_date)
                }else if (dialogBinding.etActualReturnDate.text.toString().isEmpty()){
                    dialogBinding.etActualReturnDate.error = resources.getString(R.string.enter_return_date)
                }else{
                    studentList[position].isReturned = true
                    libraryDatabase.libraryDao().updateIssuedBooks(studentList[position])
                    issuedBooksAdapter.notifyItemChanged(position)
                    dismiss()
                }
            }
            if (dialogBinding.tvEnroll.text.toString() == resources.getString(R.string.graduation)){

            }else if (dialogBinding.tvEnroll.text.toString() == resources.getString(R.string.master)){

            }else if (dialogBinding.tvEnroll.text.toString() == resources.getString(R.string.doctorate)){

            }
        }
    }
}