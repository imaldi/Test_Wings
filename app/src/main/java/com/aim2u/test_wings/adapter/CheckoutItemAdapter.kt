package com.aim2u.test_wings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aim2u.test_wings.data.model.TransactionDetail
import com.aim2u.test_wings.databinding.CheckoutItemBinding
import com.aim2u.test_wings.databinding.ListItemBinding

class CheckoutItemAdapter(
    val onClick: (TransactionDetail) -> Unit,
    val buttonOnClick: (Boolean,Int) -> Unit,
)
    : ListAdapter<TransactionDetail, CheckoutItemAdapter.CheckoutItemViewHolder>(CheckoutItemAdapter.TransactionDetailComparator())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutItemViewHolder {
        val itemBinding = CheckoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckoutItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CheckoutItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onClick(current)
        }
        holder.bind(current,position,buttonOnClick)
    }

    class CheckoutItemViewHolder(private val itemBinding: CheckoutItemBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(transactionDetail: TransactionDetail,index: Int, buttonOnClick: (Boolean,Int) -> Unit) {
            itemBinding.textView.text = transactionDetail.productCode
            itemBinding.editTextNumber.setText(transactionDetail.quantity)
//            itemBinding.productPrice.text = transactionDetail.price.toString()
//            itemBinding.isChecked = product.isSelected
//            itemBinding.checkBox.setOnClickListener{
//                buttonOnClick(itemBinding.buttonId.isPressed,index)
//            }
        }
    }

    class TransactionDetailComparator : DiffUtil.ItemCallback<TransactionDetail>() {
        override fun areItemsTheSame(oldItem: TransactionDetail, newItem: TransactionDetail): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TransactionDetail, newItem: TransactionDetail): Boolean {
            return oldItem.productCode == newItem.productCode
        }
    }

}