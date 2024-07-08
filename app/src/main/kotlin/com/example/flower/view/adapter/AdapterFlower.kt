package com.example.flower.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.flower.R
import com.example.flower.databinding.ItemFlowerBinding
import com.example.flower.model.ModelFlower

class AdapterFlower : RecyclerView.Adapter<AdapterFlower.MyViewHolder>() {

    private var list = emptyList<ModelFlower>()

    private var onItemClick: ((ModelFlower) -> Unit)? = null
    fun setOnItemClickListener(listener: (ModelFlower) -> Unit) {
        onItemClick = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemFlowerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])

        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(list[position])
        }
    }

    fun setData(data: List<ModelFlower>) {
        list = data
        notifyDataSetChanged()

    }

    override fun getItemCount() = list.size

    class MyViewHolder(val binding: ItemFlowerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ModelFlower) {
            binding.textView.text = data.name
            binding.imageView.load(data.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.app_icon)
            }
        }
    }
}
