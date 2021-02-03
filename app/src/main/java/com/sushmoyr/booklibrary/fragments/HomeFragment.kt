package com.sushmoyr.booklibrary.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sushmoyr.booklibrary.BookAdapter
import com.sushmoyr.booklibrary.R
import com.sushmoyr.booklibrary.database.BookViewModel
import com.sushmoyr.booklibrary.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookViewModel: BookViewModel
    private val adapter: BookAdapter by lazy {
        BookAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
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

        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete_all -> {
                val builder = MaterialAlertDialogBuilder(requireContext())
                builder.setPositiveButton("Yes") { _, _ ->
                    bookViewModel.deleteAllBooks()
                    Toast.makeText(
                        requireContext(),
                        "Successfully Removed All Data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                builder.setNegativeButton("No") { _, _ ->

                }
                builder.setTitle("Delete Everything?")
                builder.setMessage("Are you sure want to delete all data?")
                builder.create().show()
            }
            R.id.name_asc -> {
                nameSortAsc()
            }
            R.id.name_desc -> {
                nameSortDesc()
            }
            R.id.oldest -> {
                oldestSort()
            }
            R.id.newest -> {
                newestSort()
            }
        }

        /*if (item.itemId == R.id.name_az) {
            Toast.makeText(requireContext(), "Sorted By Name A to Z", Toast.LENGTH_SHORT).show()
            //sortDataAsc("name")
        }
        if (item.itemId == R.id.name_za) {
            Toast.makeText(requireContext(), "Sorted By Name Z to A", Toast.LENGTH_SHORT).show()
            //sortDataDesc("name")
        }

        if (item.itemId == R.id.newest) {
            Toast.makeText(requireContext(), "Sorted By newestSort First", Toast.LENGTH_SHORT).show()
            bookViewModel.sortByIdDesc()
        }

        if (item.itemId == R.id.oldest) {
            Toast.makeText(requireContext(), "Sorted By oldestSort First", Toast.LENGTH_SHORT).show()
            //sortDataAsc("id")
        }*/

        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchDatabase(newText)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        bookViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }

    fun nameSortAsc() {
        bookViewModel.nameSortAsc().observe(this, { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }

    fun nameSortDesc() {
        bookViewModel.nameSortDesc().observe(this, { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }

    fun newestSort() {
        bookViewModel.newestSort().observe(this, { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }

    fun oldestSort() {
        bookViewModel.oldestSort().observe(this, { list ->
            list.let {
                adapter.setData(it)
            }
        })

    }

}