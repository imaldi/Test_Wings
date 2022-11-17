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
import androidx.recyclerview.widget.RecyclerView
import com.aim2u.test_wings.R
import com.aim2u.test_wings.data.model.Product
import com.aim2u.test_wings.databinding.ListItemBinding

class ItemAdapter(
    private val context: Context,
    private val dataset: List<Product>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product: Product = dataset[position]
        holder.itemView.setOnClickListener {
            Toast.makeText(
                context,
                "${product.productName}",
                Toast.LENGTH_SHORT
            ).show()

//            Navigation.createNavigateOnClickListener(R.id.action_productListFragment_to_productDetailFragment, null)
            holder.itemView.findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment)
//            val intent = Intent(context, ProductDetailActivity::class.java)
//            context.startActivity(intent)

        }
        holder.bind(product)
    }

    override fun getItemCount(): Int = dataset.size

    class ItemViewHolder(private val itemBinding: ListItemBinding): RecyclerView.ViewHolder(itemBinding.root){
//        val textView: TextView = ActivityProductListBinding.inflate(LayoutInflater.from(parent))
        fun bind(product: Product) {
            itemBinding.productName.text = product.productName
            itemBinding.productPrice.text = product.price.toString()
//            itemBinding.tvPaymentAmount.text = paymentBean.totalAmount
        }
    }
}