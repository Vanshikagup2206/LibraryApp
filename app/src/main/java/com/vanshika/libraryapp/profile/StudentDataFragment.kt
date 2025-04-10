package com.vanshika.libraryapp.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentStudentDataBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudentDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentDataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentStudentDataBinding ?= null
    var studentInformationDataClass = StudentInformationDataClass()
    lateinit var libraryDatabase: LibraryDatabase
    private var selectedImageUri: Uri ?= null

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
        binding = FragmentStudentDataBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        libraryDatabase = LibraryDatabase.getInstance(requireContext())

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri: Uri? ->
            uri.let {
                selectedImageUri = it
                Glide.with(requireContext())
                    .load(it)
                    .into(binding?.ivSelectedImage!!)
            }
        }

        binding?.btnAddImage?.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding?.btnAdd?.setOnClickListener {
            if(binding?.etStudentName?.text?.trim()?.isEmpty() == true){
                binding?.etStudentName?.error = resources.getString(R.string.enter_student_name)
            }
            else if (binding?.etRegistrationNo?.text?.trim()?.isEmpty() == true){
                binding?.etRegistrationNo?.error = resources.getString(R.string.enter_your_registration_no)
            }
            else if (binding?.etDepartment?.text?.trim()?.isEmpty() == true){
                binding?.etDepartment?.error = resources.getString(R.string.enter_department)
            }
            else if (binding?.etMobileNo?.text?.trim()?.isEmpty() == true){
                binding?.etMobileNo?.error = resources.getString(R.string.enter_mobile_no)
            }
            else if (binding?.etSemester?.text?.isEmpty() == true){
                binding?.etSemester?.error = resources.getString(R.string.enter_semester)
            }
            else if (binding?.rgEnroll?.checkedRadioButtonId == -1){
                Toast.makeText(requireContext(), resources.getString(R.string.select_one), Toast.LENGTH_SHORT).show()
            }
            else{
                val enroll = if (binding?.rbGraduation?.isChecked == true){
                    0
                }else if (binding?.rbMaster?.isChecked == true){
                    1
                }else{
                    2
                }
                libraryDatabase.libraryDao().insertStudentData(
                    StudentInformationDataClass(
                        studentName = binding?.etStudentName?.text?.toString(),
                        registrationNo = binding?.etRegistrationNo?.text?.toString()?.toInt(),
                        department = binding?.etDepartment?.text?.toString(),
                        mobileNo = binding?.etMobileNo?.text?.toString(),
                        studentPhoto = selectedImageUri?.toString(),
                        semester = binding?.etSemester?.text?.toString()?.toInt(),
                        enroll = enroll
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
         * @return A new instance of fragment StudentDataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudentDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}