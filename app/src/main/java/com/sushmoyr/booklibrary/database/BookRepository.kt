package com.sushmoyr.booklibrary.database

import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {
    val readAllData: LiveData<List<Book>> = bookDao.readAllData()


    fun addBook(book: Book) {
        bookDao.addBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Book>> {
        return bookDao.searchDatabase(searchQuery)

    }

    suspend fun deleteAllBooks() {
        bookDao.deleteAllBooks()
    }


    fun nameSortAsc(): LiveData<List<Book>> {
        return bookDao.nameSortAsc()
    }

    fun nameSortDesc(): LiveData<List<Book>> {
        return bookDao.nameSortDesc()
    }

    fun newestSort(): LiveData<List<Book>> {
        return bookDao.newest()
    }

    fun oldestSort(): LiveData<List<Book>> {
        return bookDao.oldest()
    }
}