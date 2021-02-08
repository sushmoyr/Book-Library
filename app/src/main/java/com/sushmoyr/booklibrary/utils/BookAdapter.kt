package com.sushmoyr.booklibrary.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sushmoyr.booklibrary.R
import com.sushmoyr.booklibrary.database.Book
import com.sushmoyr.booklibrary.databinding.CustomRowBinding
import com.sushmoyr.booklibrary.fragments.HomeFragmentDirections

class BookAdapter : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {
    private var bookList = emptyList<Book>()

    class MyViewHolder(val binding: CustomRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CustomRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bookList[position]

        holder.binding.itemRoot.animation =
            AnimationUtils.loadAnimation(holder.binding.itemRoot.context, R.anim.custom_animation)

        holder.binding.bookTitle.text = currentItem.name
        holder.binding.bookAuthor.text = currentItem.author
        holder.binding.bookDesc.text = currentItem.description
        holder.binding.bookGenre.text = currentItem.genre
        holder.binding.bookCover.setImageBitmap(currentItem.cover)

        //CLICK EVENT FOR ITEMS = FROM HOME TO VIEW
        holder.binding.itemRoot.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToViewFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

        holder.binding.itemRoot.setOnLongClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
            true
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun setData(bookList: List<Book>) {
        this.bookList = bookList
        notifyDataSetChanged()
    }
}