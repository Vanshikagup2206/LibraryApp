package com.vanshika.libraryapp.home

import android.app.Dialog
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.vanshika.libraryapp.LibraryDatabase
import com.vanshika.libraryapp.R
import com.vanshika.libraryapp.databinding.CustomBooksAccToCategoryBinding
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
class AdminHomeFragment : Fragment(), BooksClickInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentAdminHomeBinding? = null
    lateinit var linearLayoutManager: LinearLayoutManager
    var booksList = arrayListOf<BooksDataClass>()
    lateinit var booksAdapter: BooksAdapter
    lateinit var libraryDatabase: LibraryDatabase
    private lateinit var deleteIcon: Drawable
    private lateinit var editIcon: Drawable
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
        binding = FragmentAdminHomeBinding.inflate(inflater)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_delete_24)!!
        editIcon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_edit_24)!!
        libraryDatabase = LibraryDatabase.getInstance(requireContext())
        booksAdapter = BooksAdapter(booksList, this)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.rvBooks?.layoutManager = linearLayoutManager
        binding?.rvBooks?.adapter = booksAdapter
        getBooksAccToCategory()
        // set up itemTouchHelper
//        val itemTouchHelper =
//            ItemTouchHelper(object :
//                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//                    return false // No drag-and-drop functionality
//                }
//
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    val position = viewHolder.adapterPosition
//                    val book = booksList[position]
//                    val itemView = viewHolder.itemView
//                    Log.d(
//                        "SwipeDetection",
//                        "Swiped book: ${book.booksCategory}, direction: $direction"
//                    )
//                    if (direction == ItemTouchHelper.LEFT) {
//                        // Show confirmation dialog
////                        itemView.setBackgroundColor(R.drawable.boundary_to_swipe_delete)
//                        itemView.setBackgroundColor(R.drawable.boundary_to_swipe_delete)
////                        itemView.background = ColorDrawable(Color.parseColor("#FF5252"))
//                        AlertDialog.Builder(requireContext())
//                            .setMessage(R.string.are_you_sure_you_want_to_delete_this_section)
//                            .setPositiveButton(R.string.yes) { _, _ ->
//                                libraryDatabase.libraryDao().deleteBooksWithCategory(book)
//                                getBooksAccToCategory() // Refresh the list
//                            }
//                            .setNegativeButton(R.string.no) { dialog, _ ->
//                                dialog.dismiss()
////                                itemView.setBackgroundColor(0)// reset the color
//                                booksAdapter.notifyItemChanged(position) // Revert swipe
//                            }
//                            .show()
//                            .setCancelable(false)
//                    } else if (direction == ItemTouchHelper.RIGHT) {
//                        itemView.setBackgroundColor(R.drawable.boundary_to_update)
//                        Toast.makeText(
//                            requireContext(),
//                            "Update book: ${book.booksCategory}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        val dialogBinding =
//                            CustomBooksAccToCategoryBinding.inflate(layoutInflater)
//                        Dialog(requireContext()).apply {
//                            setContentView(dialogBinding.root)
//                            getWindow()?.setLayout(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.WRAP_CONTENT
//                            )
//                            show()
//                            setCancelable(false)
//
//                            dialogBinding.btnAddAccToCategory.setOnClickListener {
//                                if (dialogBinding.etCategory.text.toString().trim()
//                                        .isNullOrEmpty()
//                                ) {
//                                    dialogBinding.etCategory.error =
//                                        resources.getString(R.string.enter_category)
//                                } else if (dialogBinding.etAboutCategory.text.toString().trim()
//                                        .isNullOrEmpty()
//                                ) {
//                                    dialogBinding.etAboutCategory.error =
//                                        resources.getString(R.string.enter_description)
//                                } else {
//                                    libraryDatabase.libraryDao().updateBooksWithCategory(
//                                        BooksDataClass(
//                                            booksId = booksList[position].booksId,
//                                            booksCategory = dialogBinding.etCategory.text.toString(),
//                                            booksAbout = dialogBinding.etAboutCategory.text.toString(),
//                                            categoryId = booksList[position].booksId
//                                        )
//                                    )
//                                    getBooksAccToCategory()
//                                    booksAdapter.notifyDataSetChanged()
//                                    dismiss()
//                                }
//                            }
//                        }
//                    }
//
//                }
//
//            })
//        itemTouchHelper.attachToRecyclerView(binding?.rvBooks)

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private val swipeThreshold = 150f //swipe distance

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // No drag-and-drop functionality
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconSize = 50
                val iconMargin = (itemView.height - iconSize * 2) / 3
                val limitedDx = dX.coerceAtLeast(-swipeThreshold)

                if (limitedDx < 0) { // Swipe left
                    // Draw background for delete and edit
                    val background = ColorDrawable(Color.parseColor("#CCCC99"))
                    background.setBounds(
                        (itemView.right + limitedDx).toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    background.draw(c)

                    // Draw delete icon
                    deleteIcon.setBounds(
                        itemView.right - iconMargin - iconSize,
                        itemView.top + iconMargin,
                        itemView.right - iconMargin,
                        itemView.top + iconMargin + iconSize
                    )
                    deleteIcon.draw(c)

                    //Draw edit icon
                    editIcon.setBounds(
                        itemView.right - iconMargin - iconSize,
                        itemView.top + iconMargin * 2 + iconSize,
                        itemView.right - iconMargin,
                        itemView.top + iconMargin * 2 + iconSize * 2
                    )
                    editIcon.draw(c)
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    limitedDx,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return 1.5f
            }
        })

        itemTouchHelper.attachToRecyclerView(binding?.rvBooks)

        binding?.rvBooks?.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                // Detect clicks on delete and edit icons
                if (e.action == MotionEvent.ACTION_DOWN) {
                    val child = rv.findChildViewUnder(e.x, e.y)
                    if (child != null) {
                        val viewHolder = rv.getChildViewHolder(child)
                        val position = viewHolder.adapterPosition
                        val itemView = viewHolder.itemView
                        val book = booksList[position]

                        if (deleteIcon.bounds.contains(e.x.toInt(), e.y.toInt())) {
                            AlertDialog.Builder(rv.context)
                                .setMessage(R.string.are_you_sure_you_want_to_delete_this_section)
                                .setPositiveButton("Yes") { _, _ ->
                                    libraryDatabase.libraryDao().deleteBooksWithCategory(book)
                                    getBooksAccToCategory()
                                }
                                .setNegativeButton(R.string.no) { dialog, _ ->
                                    dialog.dismiss()
                                    rv.adapter?.notifyItemChanged(viewHolder.adapterPosition)
                                }
                                .show()
                            return true
                        } else if (editIcon.bounds.contains(e.x.toInt(), e.y.toInt())) {
                            Toast.makeText(rv.context, "edit", Toast.LENGTH_SHORT).show()
                            return true
                        }
                    }
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })

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

    override fun moveToNext(position: Int) {
        val convertToString = Gson().toJson(booksList[position])
        val bundle = bundleOf(
            "booksId" to booksList[position].booksId,
            "noOfBooks" to convertToString,
            "booksCategory" to convertToString,
            "booksDescription" to convertToString
        )
        findNavController().navigate(R.id.booksSpecificationFragment,bundle)
    }
}