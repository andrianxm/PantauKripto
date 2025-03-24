package com.andrian.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andrian.core.databinding.ItemMarketBinding
import com.andrian.core.domain.model.Kripto
import com.andrian.core.utils.formatToRupiah
import com.bumptech.glide.Glide

class KriptoAdapter : ListAdapter<Kripto, KriptoAdapter.ListViewHolder>(DIFF_CALLBACK) {
    var onItemClick: ((Kripto) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            ItemMarketBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemMarketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Kripto) {
            Glide.with(itemView.context)
                .load(data.logoUrl)
                .into(binding.imageItemLogo)
            binding.textBaseCurrency.text = data.description
            binding.textLastPrice.text = "Harga Terakhir: ${formatToRupiah(data.last)}"
            binding.textBuySell.text = "Beli: ${formatToRupiah(data.buy)} | Jual: ${formatToRupiah(data.sell)}"
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Kripto> =
            object : DiffUtil.ItemCallback<Kripto>() {
                override fun areItemsTheSame(oldItem: Kripto, newItem: Kripto): Boolean {
                    return oldItem.kriptoId == newItem.kriptoId
                }

                override fun areContentsTheSame(oldItem: Kripto, newItem: Kripto): Boolean {
                    return oldItem == newItem
                }
            }
    }
}