package com.sushmoyr.booklibrary.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sushmoyr.booklibrary.R
import com.sushmoyr.booklibrary.database.Book
import com.sushmoyr.booklibrary.database.BookViewModel
import com.sushmoyr.booklibrary.databinding.FragmentAddBinding
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

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

        binding.bookCover.setImageResource(R.drawable.ic_placeholder_image)
        binding.bookCover.setOnClickListener {
            pickImage()
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.save) {
            insertDataToDatabase()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDatabase() {
        hasError = false
        val book = getBookData()
        if (!hasError) {
            bookViewModel.addBook(book)
            Toast.makeText(requireContext(), "Book Information Inserted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }
    }

    private fun getBookData(): Book {
        var authorName = DEFAULT_AUTHOR_NAME
        var description = DEFAULT_DESCRIPTION
        var price = DEFAULT_PRICE
        var genre = DEFAULT_GENRE
        var quantity: Int = 0
        var bookName: String = ""
        val bookCover: Bitmap = binding.bookCover.drawToBitmap(Bitmap.Config.ARGB_8888)
        //Book name
        if (TextUtils.isEmpty(binding.bookName.text.toString())) {
            binding.bookName.error = "Name can't be empty"
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

        return Book(0, bookName, authorName, description, genre, price, quantity, bookCover)
    }

    private fun pickImage() {
        context?.let {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(4, 6)
                .setFixAspectRatio(true)
                .setRequestedSize(400, 600, CropImageView.RequestSizeOptions.RESIZE_EXACT)
                .start(it, this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val resultUri = result.uri
                binding.bookCover.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}