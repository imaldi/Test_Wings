package com.aim2u.test_wings.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aim2u.test_wings.ProductListFragmentDirections
import com.aim2u.test_wings.R
import com.aim2u.test_wings.data.model.Product
import com.aim2u.test_wings.databinding.ListItemBinding

class ItemAdapter(
//    private val dataset: List<Product>,
    val onClick: (Product) -> Unit
    ) :
//    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>()
ListAdapter<Product, ItemAdapter.ItemViewHolder>(ProductComparator())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)

//        val product: Product = dataset[position]
//        holder.itemView.setOnClickListener {
//            onClick(product)
//        }
//        holder.bind(product)
    }
//
//    override fun getItemCount(): Int = dataset.size

    class ItemViewHolder(private val itemBinding: ListItemBinding): RecyclerView.ViewHolder(itemBinding.root){
//        val textView: TextView = ActivityProductListBinding.inflate(LayoutInflater.from(parent))
        fun bind(product: Product) {
            itemBinding.productName.text = product.productName
            itemBinding.productPrice.text = product.price.toString()
//            itemBinding.tvPaymentAmount.text = paymentBean.totalAmount
        }
    }

    class ProductComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productCode == newItem.productCode
        }
    }

}