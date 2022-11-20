package com.aim2u.test_wings.ui.product_fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aim2u.test_wings.R
import com.aim2u.test_wings.WingsApplication
import com.aim2u.test_wings.adapter.ItemAdapter
import com.aim2u.test_wings.data.model.Product
import com.aim2u.test_wings.databinding.FragmentProductListBinding
import com.aim2u.test_wings.ui.shared_view_model.SharedViewModel
import com.aim2u.test_wings.ui.shared_view_model.SharedViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private val sharedViewModel: SharedViewModel by activityViewModels {
        // FIXME ini mungkin error karena null
        var application = (activity?.application as WingsApplication)
        Log.d("activityViewModels", "Hello")

        SharedViewModelFactory(
            application.productRepository,
            application.transactionHeaderRepository,
            application.transactionDetailRepository,
        )
    }
    private lateinit var myDataSet: List<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_product_list, container, false)
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val isLogin = sharedPref?.getBoolean(getString(R.string.login_preference_file_key), false)

        if(isLogin!!) {
            sharedViewModel.updateLoginStatus(isLogin)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sharedViewModel.isLoggedIn.observe(viewLifecycleOwner){
            if(!(it ?: false)) {
//                Toast.makeText(requireContext(),"Shared Pref Content: $isLogin",Toast.LENGTH_SHORT).show()
                findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToLoginFragment())
            }
        }



        myDataSet = sharedViewModel.allProduct.value ?: listOf()
        Log.d("List Product bef", myDataSet.toString())
//        sharedViewModel.simpleNumber.observe(viewLifecycleOwner) {
//            //fixme pakai resource
//            binding.efab.text = "CHECKOUT"
//            Log.d("simpleNumber:", "$it")
//        }
        binding.efab.setOnClickListener {
//            sharedViewModel.incrementSimpleNumber()
            Log.d("efab onClick:", "${sharedViewModel.simpleNumber.value}")
            sharedViewModel.initTransactionHeader()
            val appContext = context?.applicationContext
            Toast.makeText(appContext, "${sharedViewModel.transactionHeader.value?.documentCode}", Toast.LENGTH_SHORT)
                .show()
            val action = ProductListFragmentDirections.actionProductListFragmentToCheckoutFragment()
            findNavController().navigate(action)

        }

        Log.d("List Product", myDataSet.toString())
        val recyclerView = binding.productListId
        recyclerView.layoutManager = LinearLayoutManager(context)
        // cari cara amannya
        val adapter = ItemAdapter( { product ->
//            Toast.makeText(
//                context,
//                "${product.productName}",
//                Toast.LENGTH_SHORT
//            ).show()

            val action =
                ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
                    product.productCode
                )
            findNavController().navigate(action)

        },{
            selected, index ->
            sharedViewModel.setSelectedProduct(index,selected)
//            Toast.makeText(context?.applicationContext, "Produk Selected: ${sharedViewModel . selectedProduct.value?.get(index)?.isSelected}", Toast.LENGTH_SHORT)
//                .show()
//            sharedViewModel.setSelectedProduct(
//                    index, selected, product
//            )
        },)

        recyclerView.adapter = adapter
        sharedViewModel.allProduct.observe(viewLifecycleOwner) { listProduct ->
//            listProduct.let { adapter.submitList(it) }
            sharedViewModel.setSelectedProductList()
        }

        sharedViewModel.selectedProduct.observe(viewLifecycleOwner){ listProduct ->
            listProduct.let { adapter.submitList(it) }

            Toast.makeText(context?.applicationContext, "selectedProduct Size: ${listProduct?.size}", Toast.LENGTH_SHORT)
                .show()
        }


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}