package com.sushmoyr.booklibrary.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sushmoyr.booklibrary.R
import com.sushmoyr.booklibrary.database.Book
import com.sushmoyr.booklibrary.database.BookViewModel
import com.sushmoyr.booklibrary.databinding.FragmentViewBinding

class ViewFragment : Fragment() {

    private var _binding: FragmentViewBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<ViewFragmentArgs>()
    private lateinit var myBook: Book
    private lateinit var bookViewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewBinding.inflate(inflater, container, false)

        setUpUI(args.BookData)
        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setUpUI(bookData: Book) {
        val genre = "Genre: ${bookData.genre}"
        val author = bookData.author
        val name = bookData.name
        val description = bookData.description
        val price = "Price: ${bookData.price}"
        val qty = "Quantity: ${bookData.quantity} Pcs."

        binding.titleView.text = name
        binding.authorView.text = author
        binding.genreView.text = genre
        binding.descView.text = description
        binding.priceView.text = price
        binding.quantityView.text = qty
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.view_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.edit_menu -> {
                val action =
                    ViewFragmentDirections.actionViewFragmentToUpdateFragment(args.BookData)
                findNavController().navigate(action)
            }
            R.id.delete_item -> {
                val builder = MaterialAlertDialogBuilder(requireContext())
                builder.setPositiveButton("Yes") { _, _ ->
                    bookViewModel.deleteBook(args.BookData)
                    Toast.makeText(requireContext(), "Successfully Deleted", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(ViewFragmentDirections.actionViewFragmentToHomeFragment())
                }
                builder.setNegativeButton("No") { _, _ ->

                }
                builder.setTitle("Delete Book?")
                builder.setMessage("Are you sure want to delete this entry? You won't be able to undo this action.")
                builder.create().show()
            }
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}