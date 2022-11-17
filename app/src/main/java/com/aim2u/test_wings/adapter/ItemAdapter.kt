package com.aim2u.test_wings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aim2u.test_wings.R
import com.aim2u.test_wings.data.model.Product
import com.aim2u.test_wings.databinding.ActivityProductListBinding
import com.aim2u.test_wings.databinding.ListItemBinding

class ItemAdapter(private val dataset: List<Product>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product: Product = dataset[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = dataset.size

    class ItemViewHolder(private val itemBinding: ListItemBinding): RecyclerView.ViewHolder(itemBinding.root){
//        val textView: TextView = ActivityProductListBinding.inflate(LayoutInflater.from(parent))
        fun bind(product: Product) {
            itemBinding.textView.text = product.productName
//            itemBinding.tvPaymentAmount.text = paymentBean.totalAmount
        }
    }
}