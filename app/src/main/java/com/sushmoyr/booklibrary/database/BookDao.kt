package com.sushmoyr.booklibrary.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addBook(book : Book)

    @Query("SELECT * FROM book_table ORDER BY id ASC")
    fun readAllData() : LiveData<List<Book>>

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}