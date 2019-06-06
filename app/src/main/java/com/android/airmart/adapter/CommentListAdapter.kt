package com.android.airmart.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.airmart.data.entity.Comment
import com.android.airmart.databinding.RecyclerCommentItemBinding

class CommentListAdapter: ListAdapter<Comment,CommentListAdapter.ViewHolder>(CommentDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = getItem(position)
        holder.apply {
            bind(comment)
            itemView.tag = comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerCommentItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    class ViewHolder(
        private val binding: RecyclerCommentItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment) {
            binding.apply {
                comment = item
                executePendingBindings()
            }
        }
    }
}
private class CommentDiffCallback : DiffUtil.ItemCallback<Comment>() {

    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }
}