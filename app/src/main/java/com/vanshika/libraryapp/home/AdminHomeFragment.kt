package com.vanshika.libraryapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.FragmentAdminHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminHomeFragment : Fragment(), BooksInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentAdminHomeBinding? = null
    lateinit var linearLayoutManager: LinearLayoutManager
    var booksList = arrayListOf<BooksDataClass>()
    lateinit var booksAdapter: BooksAdapter
    lateinit var libraryDatabase: LibraryDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mainActivity = activity as MainActivity
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
        binding = FragmentAdminHomeBinding.inflate(inflater)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        booksAdapter = BooksAdapter(booksList, this)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.rvBooks?.layoutManager = linearLayoutManager
        binding?.rvBooks?.adapter = booksAdapter
        getBooksAccToCategory()
        // set up itemTouchHelper
        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false // No drag-and-drop functionality
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val book = booksList[position]
                    if (direction == ItemTouchHelper.LEFT) {
                        // Show confirmation dialog
                        AlertDialog.Builder(requireContext())
                            .setMessage(R.string.are_you_sure_you_want_to_delete_this_section)
                            .setPositiveButton(R.string.yes) { _, _ ->
                                libraryDatabase.libraryDao().deleteBooksWithCategory(book)
                                getBooksAccToCategory() // Refresh the list
                            }
                            .setNegativeButton(R.string.no) { dialog, _ ->
                                dialog.dismiss()
                                booksAdapter.notifyItemChanged(position) // Revert swipe
                            }
                            .show()
                    } else if (direction == ItemTouchHelper.RIGHT) {
                        Toast.makeText(
                            requireContext(),
                            "Update book: ${book.booksCategory}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            })
        itemTouchHelper.attachToRecyclerView(binding?.rvBooks)
        binding?.fabAdd?.setOnClickListener {
            findNavController().navigate(R.id.booksAccordingToCategoryFragment)
        }
    }

    private fun getBooksAccToCategory() {
        booksList.clear()
        booksList.addAll(libraryDatabase.libraryDao().getBooksAccToCategory())
        booksAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}