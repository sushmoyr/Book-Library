package com.sushmoyr.booklibrary.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sushmoyr.booklibrary.BookAdapter
import com.sushmoyr.booklibrary.R
import com.sushmoyr.booklibrary.database.BookViewModel
import com.sushmoyr.booklibrary.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookViewModel: BookViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
        val adapter = BookAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        bookViewModel.readAllData.observe(viewLifecycleOwner, { book ->
            adapter.setData(book)
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }



        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.delete_all) {
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->

            }
            builder.setNegativeButton("No") { _, _ ->

            }
            builder.setTitle("Delete Everything?")
            builder.setMessage("Are you sure want to delete all data?")
            builder.create().show()
        }

        if (item.itemId == R.id.name_az) {
            Toast.makeText(requireContext(), "Sorted By Name A to Z", Toast.LENGTH_SHORT).show()
        }
        if (item.itemId == R.id.name_za) {
            Toast.makeText(requireContext(), "Sorted By Name Z to A", Toast.LENGTH_SHORT).show()
        }

        if (item.itemId == R.id.newest) {
            Toast.makeText(requireContext(), "Sorted By Newest First", Toast.LENGTH_SHORT).show()
        }

        if (item.itemId == R.id.oldest) {
            Toast.makeText(requireContext(), "Sorted By Oldest First", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}