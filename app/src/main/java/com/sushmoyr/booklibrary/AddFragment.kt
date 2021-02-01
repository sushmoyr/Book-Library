package com.sushmoyr.booklibrary

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sushmoyr.booklibrary.database.Book
import com.sushmoyr.booklibrary.database.BookViewModel
import com.sushmoyr.booklibrary.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    //CONSTANTS
    private val DEFAULT_AUTHOR_NAME = "NOT AVAILABLE"
    private val DEFAULT_GENRE = "Unspecified"
    private val DEFAULT_DESCRIPTION = "No Description Provided"
    private val DEFAULT_PRICE = 0.0
    private var hasError: Boolean = false

    private lateinit var bookViewModel: BookViewModel

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.save) {
            insertDataToDatabase()
        }

        return super.onOptionsItemSelected(item)
    }

    fun insertDataToDatabase() {
        hasError = false
        val book = getBookData()
        if (!hasError) {
            bookViewModel.addBook(book)
            Toast.makeText(requireContext(), "Book Information Inserted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
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
        if (!TextUtils.isEmpty(binding.description.text.toString())) {
            description = binding.description.text.toString()
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

        return Book(0, bookName, authorName, description, genre, price, quantity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}