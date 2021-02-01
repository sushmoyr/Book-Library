package com.sushmoyr.booklibrary.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var name:String,
    var author:String,
    var description:String,
    var genre:String,
    var price:Double,
    var quantity:Int)