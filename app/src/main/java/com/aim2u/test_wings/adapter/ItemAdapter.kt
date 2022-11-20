package com.aim2u.test_wings.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
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
    val onClick: (Product) -> Unit,
    val buttonOnClick: (Boolean,Int) -> Unit,
    ) :
ListAdapter<Product, ItemAdapter.ItemViewHolder>(ProductComparator())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onClick(current)
        }
        holder.bind(current,position,buttonOnClick)
    }

    class ItemViewHolder(private val itemBinding: ListItemBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(product: Product,index: Int, buttonOnClick: (Boolean,Int) -> Unit) {
//            var buttonSelected = product.isSelected
//            var liveDataText = MutableLiveData<String>(product.isSelected.toString())
//                liveData<String> { product.isSelected.toString() }
//            itemBinding.lifecycleOwner = itemBinding.root
            itemBinding.productName.text = product.productName
            itemBinding.productPrice.text = product.price.toString()
            itemBinding.isChecked = product.isSelected
//            itemBinding.textButton = liveDataText.value
//            itemBinding.buttonId.text = buttonSelected.toString()
//                isTheButtonPressed(index)
            itemBinding.checkBox.setOnClickListener{
//                buttonSelected = !buttonSelected
//                itemBinding.textButton = product.isSelected.toString()
                buttonOnClick(itemBinding.buttonId.isPressed,index)
            }
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