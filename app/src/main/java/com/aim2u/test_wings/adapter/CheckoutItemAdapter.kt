package com.aim2u.test_wings.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aim2u.test_wings.data.model.Product
import com.aim2u.test_wings.data.model.TransactionDetail
import com.aim2u.test_wings.databinding.CheckoutItemBinding
import com.aim2u.test_wings.databinding.ListItemBinding

class CheckoutItemAdapter(
    val onClick: (TransactionDetail) -> Unit,
    val selectedProduct: (String) -> Product,
    val onEditTextEdited: (Int, String) -> Unit,
) : ListAdapter<TransactionDetail, CheckoutItemAdapter.CheckoutItemViewHolder>(CheckoutItemAdapter.TransactionDetailComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutItemViewHolder {
        val itemBinding =
            CheckoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckoutItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CheckoutItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onClick(current)
        }
        holder.bind(current, position, selectedProduct, onEditTextEdited)
    }

    class CheckoutItemViewHolder(private val itemBinding: CheckoutItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(
            transactionDetail: TransactionDetail,
            index: Int,
            selectedProduct: (String) -> Product,
            onEditTextEdited: (Int, String) -> Unit
        ) {
            itemBinding.textView.text = selectedProduct(transactionDetail.productCode).productName
            itemBinding.editTextNumber.setText(transactionDetail.quantity)
            itemBinding.editTextNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    var stringg: String = p0?.toList()?.joinToString("") ?: ""
                    onEditTextEdited(index, stringg)
                }

                override fun afterTextChanged(p0: Editable?) {
                    var stringg = p0.toString()
                    onEditTextEdited(index, stringg)
                }

            })
//            itemBinding.productPrice.text = transactionDetail.price.toString()
//            itemBinding.isChecked = product.isSelected
//            itemBinding.checkBox.setOnClickListener{
//                buttonOnClick(itemBinding.buttonId.isPressed,index)
//            }
        }
    }

    class TransactionDetailComparator : DiffUtil.ItemCallback<TransactionDetail>() {
        override fun areItemsTheSame(
            oldItem: TransactionDetail,
            newItem: TransactionDetail
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: TransactionDetail,
            newItem: TransactionDetail
        ): Boolean {
            return oldItem.productCode == newItem.productCode
        }
    }

}