package com.vanshika.libraryapp.home

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
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
class AdminHomeFragment : Fragment(), BooksClickInterface, CategoryClickInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentAdminHomeBinding? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var linearLayoutManagerCategory: LinearLayoutManager
    var booksList = arrayListOf<BooksDataClass>()
    var categoryList = arrayListOf<CategoryDataClass>()
    lateinit var booksAdapter: BooksAdapter
    lateinit var booksCategoryAdapter: BooksCategoryAdapter
    lateinit var libraryDatabase: LibraryDatabase
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

        sharedPreferences = requireContext().getSharedPreferences(
            resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()

        linearLayoutManagerCategory =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvCategory?.layoutManager = linearLayoutManagerCategory
        booksCategoryAdapter = BooksCategoryAdapter(categoryList, this)
        binding?.rvCategory?.adapter = booksCategoryAdapter
        getCategoryList()

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

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
                    // Perform action for left swipe
                    AlertDialog.Builder(requireContext())
                        .setMessage(R.string.are_you_sure_you_want_to_delete_this_section)
                        .setPositiveButton(R.string.yes) { _, _ ->
                            libraryDatabase.libraryDao().deleteBooksWithCategory(book)
                            getBooksAccToCategory() // Refresh the list
                        }
                        .setNegativeButton(R.string.no) { dialog, _ ->
                            dialog.dismiss()
                            booksAdapter.notifyItemChanged(position) // Reset item
                        }
                        .show()
                } else if (direction == ItemTouchHelper.RIGHT) {
                    findNavController().navigate(
                        R.id.adminHomeUpdateFragment,
                        bundleOf("bookId" to booksList[position].booksId)
                    )
                }
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
                val iconMargin = 55 // Margin between the icon and the item edges
                val limit = itemView.width * 0.3f // Allow swipe up to 30% of item width
//                A positive dX indicates a swipe to the right.
//                A negative dX indicates a swipe to the left.
                val clampedDx = if (dX > 0) dX.coerceAtMost(limit) else dX.coerceAtLeast(-limit)

                val background = ColorDrawable()

                // Draw background and icon for swipe directions
                if (clampedDx > 0) { // Swiping right (edit action)
                    background.color = Color.parseColor("#CCCC99")
                    background.setBounds(
                        itemView.left,
                        itemView.top,
                        itemView.left + clampedDx.toInt(),
                        itemView.bottom
                    )
                    background.draw(c)

                    // Draw the edit icon
                    val editIcon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.baseline_edit_24)
                    editIcon?.let {
                        val iconTop = itemView.top + (itemView.height - it.intrinsicHeight) / 2
                        val iconLeft = itemView.left + iconMargin
                        val iconRight = iconLeft + it.intrinsicWidth
                        val iconBottom = iconTop + it.intrinsicHeight
                        it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        it.draw(c)
                    }

                } else if (clampedDx < 0) { // Swiping left (delete action)
                    background.color = Color.parseColor("#fe5757")
                    background.setBounds(
                        itemView.right + clampedDx.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    background.draw(c)

                    // Draw the delete icon
                    val deleteIcon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.baseline_delete_24)
                    deleteIcon?.let {
                        val iconTop = itemView.top + (itemView.height - it.intrinsicHeight) / 2
                        val iconRight = itemView.right - iconMargin
                        val iconLeft = iconRight - it.intrinsicWidth
                        val iconBottom = iconTop + it.intrinsicHeight
                        it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        it.draw(c)
                    }
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    clampedDx,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }


            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return 1.0f // Prevent full swipe (clamping already applied in onChildDraw)
            }
        })

        itemTouchHelper.attachToRecyclerView(binding?.rvBooks)


        binding?.fabAdd?.setOnClickListener {
            findNavController().navigate(R.id.booksAccordingToCategoryFragment)
        }
    }

    private fun getCategoryList() {
        categoryList.clear()
        categoryList.add(CategoryDataClass(-1, "All"))
        categoryList.addAll(libraryDatabase.libraryDao().getCategory())
        booksCategoryAdapter.notifyDataSetChanged()
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
        val selectedCategory = booksList[position].booksCategory
        sharedPreferences.edit().putString("selectedCategory", selectedCategory).apply()
        val bundle = bundleOf(
            "booksId" to booksList[position].booksId,
            "noOfBooks" to convertToString,
            "booksCategory" to convertToString,
            "booksDescription" to convertToString,
            "selectedCategory" to selectedCategory
        )
        findNavController().navigate(R.id.booksSpecificationFragment, bundle)
    }

    override fun onItemClick(position: Int) {
        booksList.clear()
        if (categoryList[position].categoryId == -1) {
            booksList.addAll(libraryDatabase.libraryDao().getBooksAccToCategory())
        } else {
            booksList.addAll(libraryDatabase.libraryDao().getHomeBooksAccToCategory(categoryList[position].categoryName.toString()))
        }
        booksAdapter.notifyDataSetChanged()
        booksCategoryAdapter.updatePosition(position)
    }
}