package com.sushmoyr.booklibrary.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData:LiveData<List<Book>>
    private val repository:BookRepository

    init {
        val bookDao = BookDatabase.getDatabase(application).bookDao()
        repository = BookRepository(bookDao)
        readAllData = repository.readAllData
    }

    fun addBook(book:Book)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
        }
    }

    fun updateBook(book: Book)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBook(book)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Book>> {
        return repository.searchDatabase(searchQuery)

    }


    fun deleteAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllBooks()
        }
    }

    fun nameSortAsc(): LiveData<List<Book>> {
        return repository.nameSortAsc()
    }

    fun nameSortDesc(): LiveData<List<Book>> {
        return repository.nameSortDesc()
    }

    fun newestSort(): LiveData<List<Book>> {
        return repository.newestSort()
    }

    fun oldestSort(): LiveData<List<Book>> {
        return repository.oldestSort()
    }
}