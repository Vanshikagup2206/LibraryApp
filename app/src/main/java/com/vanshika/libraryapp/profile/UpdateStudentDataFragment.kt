package com.vanshika.libraryapp.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentUpdateStudentDataBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateStudentDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateStudentDataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentUpdateStudentDataBinding ?= null
    var studentInformationDataClass = StudentInformationDataClass()
    var studentDataList = arrayListOf<StudentInformationDataClass>()
    lateinit var libraryDatabase: LibraryDatabase
    lateinit var studentDataAdapter: StudentDataAdapter
    var studentId = 0
    private var selectedImageUri : Uri?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            studentId = it.getInt("studentId",0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateStudentDataBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        getStudentList()

        binding?.etStudentName?.setText(studentInformationDataClass.studentName)
        binding?.etRegistrationNo?.setText(studentInformationDataClass.registrationNo.toString())
        binding?.etDepartment?.setText(studentInformationDataClass.department)
        binding?.etMobileNo?.setText(studentInformationDataClass.mobileNo.toString())
        binding?.etSemester?.setText(studentInformationDataClass.semester.toString())

        binding?.ivSelectedImage?.let { imageView ->
            studentInformationDataClass.studentPhoto?.let { photoUri ->
                Glide.with(requireContext())
                    .load(photoUri)
                    .placeholder(R.drawable.empty)
                    .into(imageView)
            }
        }

        binding?.btnAdd?.setOnClickListener {
            if(binding?.etStudentName?.text?.isEmpty() == true){
                binding?.etStudentName?.error = resources.getString(R.string.enter_student_name)
            }
            else if (binding?.etRegistrationNo?.text?.isEmpty() == true){
                binding?.etRegistrationNo?.error = resources.getString(R.string.enter_your_registration_no)
            }
            else if (binding?.etDepartment?.text?.isEmpty() == true){
                binding?.etDepartment?.error = resources.getString(R.string.enter_department)
            }
            else if (binding?.etMobileNo?.text?.isEmpty() == true){
                binding?.etMobileNo?.error = resources.getString(R.string.enter_mobile_no)
            }
            else if (binding?.etSemester?.text?.isEmpty() == true){
                binding?.etSemester?.error = resources.getString(R.string.enter_semester)
            }
            else{
                libraryDatabase.libraryDao().updateStudentData(
                    StudentInformationDataClass(
                        studentId = studentId,
                        studentName = binding?.etStudentName?.text?.toString(),
                        registrationNo = binding?.etRegistrationNo?.text?.toString()?.toInt(),
                        department = binding?.etDepartment?.text?.toString(),
                        mobileNo = binding?.etMobileNo?.text?.toString(),
                        studentPhoto = selectedImageUri?.toString(),
                        semester = binding?.etSemester?.text?.toString()?.toInt()
                    )
                )
                findNavController().popBackStack()
            }
        }
    }

    private fun getStudentList() {
        studentInformationDataClass = libraryDatabase.libraryDao().getStudentDataAccToId(studentId)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateStudentDataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateStudentDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}