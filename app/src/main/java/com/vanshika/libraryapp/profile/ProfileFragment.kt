package com.vanshika.libraryapp.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentProfileBinding? = null
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
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        val sharedPreferences = requireContext().getSharedPreferences(
            resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val enteredRegNo = sharedPreferences.getString("enteredRegNo", null)

        if (enteredRegNo.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Registration No not found!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        binding?.tvRegistrationNo?.text = enteredRegNo
        val studentDetails = libraryDatabase.libraryDao().getStudentRegNo(enteredRegNo.toInt())

        studentDetails.let {
            binding?.tvStudentName?.text = it.studentName
            when (it.enroll) {
                0 -> binding?.tvEnroll?.setText(resources.getString(R.string.graduation))
                1 -> binding?.tvEnroll?.setText(resources.getString(R.string.master))
                2 -> binding?.tvEnroll?.setText(resources.getString(R.string.doctorate))
            }
            binding?.tvDept?.text = it.department
            binding?.tvFine?.text = it.fineAmount.toString()
            binding?.ivStudent?.let { imageView ->
                it.studentPhoto?.let { photoUri ->
                    Glide.with(requireContext())
                        .load(photoUri)
                        .placeholder(R.drawable.empty)
                        .into(imageView)
                }
            }
        }

        val issuedBooks = libraryDatabase.libraryDao().getReturnedBooks(false, enteredRegNo.toInt())
        val issuedBooksCount = libraryDatabase.libraryDao().getIssuedBooksCount(enteredRegNo.toInt())

        if (issuedBooks.isNotEmpty()){
            if (issuedBooksCount == 1){
                if (binding?.tvEnroll?.text == resources.getString(R.string.graduation)){
                    binding?.ivLibCard1?.visibility = View.GONE
                    binding?.ivLibCard2?.visibility = View.VISIBLE
                }else {
                    binding?.ivLibCard1?.visibility = View.GONE
                    binding?.ivLibCard2?.visibility = View.VISIBLE
                    binding?.ivLibCard3?.visibility = View.VISIBLE
                }
            } else if (issuedBooksCount == 2){
                if (binding?.tvEnroll?.text == resources.getString(R.string.graduation)){
                    binding?.ivLibCard1?.visibility = View.GONE
                    binding?.ivLibCard2?.visibility = View.GONE
                }else {
                    binding?.ivLibCard1?.visibility = View.GONE
                    binding?.ivLibCard2?.visibility = View.GONE
                    binding?.ivLibCard3?.visibility = View.VISIBLE
                }
            } else{
                binding?.ivLibCard1?.visibility = View.GONE
                binding?.ivLibCard2?.visibility = View.GONE
                binding?.ivLibCard3?.visibility = View.GONE
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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}