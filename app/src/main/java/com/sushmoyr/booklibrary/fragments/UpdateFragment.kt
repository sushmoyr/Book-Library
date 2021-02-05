package com.sushmoyr.booklibrary.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sushmoyr.booklibrary.R
import com.sushmoyr.booklibrary.database.Book
import com.sushmoyr.booklibrary.database.BookViewModel
import com.sushmoyr.booklibrary.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    //CONSTANTS
    private val DEFAULT_AUTHOR_NAME = "NOT AVAILABLE"
    private val DEFAULT_GENRE = "Unspecified"
    private val DEFAULT_DESCRIPTION = "No Description Provided"
    private val DEFAULT_PRICE = 0.0
    private var hasError: Boolean = false

    private lateinit var bookViewModel: BookViewModel
    private val args by navArgs<UpdateFragmentArgs>()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        subscribeUI(args.BookData)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun subscribeUI(book: Book) {
        binding.authorName.setText(book.author)
        binding.bookName.setText(book.name)
        binding.descriptionUpdate.setText(book.description)
        binding.genre.setText(book.genre)
        binding.price.setText(book.price.toString())
        binding.quantity.setText(book.quantity.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.save_update) {
            updateDataToDatabase()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateDataToDatabase() {
        hasError = false
        val book = getBookData()
        if (!hasError) {
            bookViewModel.updateBook(book)
            Toast.makeText(requireContext(), "Book Information Inserted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
        }
    }

    fun getBookData(): Book {
        var authorName = DEFAULT_AUTHOR_NAME
        var description = DEFAULT_DESCRIPTION
        var price = DEFAULT_PRICE
        var genre = DEFAULT_GENRE
        var quantity: Int = 0
        var bookName: String = ""
        //Book name
        if (TextUtils.isEmpty(binding.bookName.text.toString())) {
            binding.bookName.setError("Name can't be empty")
            hasError = true
        } else {
            bookName = binding.bookName.text.toString()
        }
        //Author Name
        if (!TextUtils.isEmpty(binding.authorName.text.toString())) {
            authorName = binding.authorName.text.toString()
        }
        //Description
        if (!TextUtils.isEmpty(binding.descriptionUpdate.text.toString())) {
            description = binding.descriptionUpdate.text.toString()
        }
        //genre
        if (!TextUtils.isEmpty(binding.genre.text.toString())) {
            genre = binding.genre.text.toString()
        }
        if (!TextUtils.isEmpty(binding.quantity.text.toString())) {
            quantity = Integer.parseInt(binding.quantity.text.toString())
        }
        if (!TextUtils.isEmpty(binding.price.text.toString())) {
            if (binding.price.text.toString().toDoubleOrNull() != null) {
                price = binding.price.text.toString().toDouble()
            } else
                binding.price.setError("Invalid Format")
        }

        return Book(args.BookData.id, bookName, authorName, description, genre, price, quantity)
    }

}