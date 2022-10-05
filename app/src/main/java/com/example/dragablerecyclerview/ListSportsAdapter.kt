package com.example.dragablerecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dragablerecyclerview.databinding.ItemRowSportsBinding

class ListSportsAdapter(private val listSport: ArrayList<Sports>) :
    RecyclerView.Adapter<ListSportsAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemCLickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemCLickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowSportsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, info, image,  news) = listSport[position]

        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(image)
                .into(holder.binding.imgAvatar)
            tvTitle.text = title
            tvSubTitle.text = info

            cvItem.setOnClickListener {
                onItemClickCallBack.onItemClicked(listSport[holder.adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return listSport.size
    }

    class ListViewHolder(var binding: ItemRowSportsBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemCLickCallBack {
        fun onItemClicked(data: Sports)
    }
}