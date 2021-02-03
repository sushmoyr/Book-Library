package com.sushmoyr.booklibrary.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addBook(book: Book)

    @Query("SELECT * FROM book_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Book>>

    @Query("Delete FROM book_table")
    suspend fun deleteAllBooks()

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    //Sort Queries
    @Query("SELECT * FROM book_table ORDER BY name ASC")
    fun nameSortAsc(): LiveData<List<Book>>

    @Query("SELECT * FROM book_table ORDER BY name DESC")
    fun nameSortDesc(): LiveData<List<Book>>

    @Query("SELECT * FROM book_table ORDER BY id DESC")
    fun newest(): LiveData<List<Book>>

    @Query("SELECT * FROM book_table ORDER BY id ASC")
    fun oldest(): LiveData<List<Book>>

    //Search Queries
    @Query("SELECT * FROM book_table WHERE name LIKE :searchQuery OR author LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Book>>
}