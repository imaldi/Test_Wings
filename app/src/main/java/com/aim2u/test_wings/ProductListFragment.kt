package com.aim2u.test_wings

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aim2u.test_wings.adapter.ItemAdapter
import com.aim2u.test_wings.data.datasource.ProductDataSource
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
    private val sharedViewModel: SharedViewModel by activityViewModels{
//        viewModelFactory {
            // FIXME ini mungkin error karena null
            var application = (activity?.application as WingsApplication)
        Log.d("activityViewModels","Hello")

        SharedViewModelFactory(
                application.productRepository,
                application.transactionHeaderRepository,
                application.transactionDetailRepository,
                application.applicationScope
            )
//        }
    }
    private lateinit var myDataSet: List<Product>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_product_list, container, false)
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//            ProductDataSource().loadProducts()



        myDataSet = sharedViewModel.allProduct.value ?: listOf()
        Log.d("List Product bef", myDataSet.toString())
        sharedViewModel.simpleNumber.observe(viewLifecycleOwner){
            //fixme pakai resource
            binding.efab.text = "CHECKOUT ($it)"
            Log.d("simpleNumber:", "$it")
        }
        binding.efab.setOnClickListener{
            sharedViewModel.incrementSimpleNumber()
            Log.d("efab onClick:", "${sharedViewModel.simpleNumber.value}")
//            val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment("ABC")
//            findNavController(this).navigate(action)
            val appContext = context?.applicationContext
            Toast.makeText(appContext, "${sharedViewModel.simpleNumber.value}", Toast.LENGTH_SHORT).show()
            sharedViewModel.setAllProduct()

        }

//        if(myDataSet.isEmpty()){
//            sharedViewModel.setAllProduct()
//        }

        Log.d("List Product", myDataSet.toString())
//        val myImmutableDataSet = myDataSet

        val recyclerView = binding.productListId
        recyclerView.layoutManager = LinearLayoutManager(context)
        // cari cara amannya
        val adapter = ItemAdapter{
                product ->
            Toast.makeText(
                context,
                "${product.productName}",
                Toast.LENGTH_SHORT
            ).show()

            val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(product.productCode)
            findNavController(this).navigate(action)

        }



//        sharedViewModel.allProduct.observe(viewLifecycleOwner, Observer {
//
//        })
        recyclerView.adapter = adapter
        sharedViewModel.allProduct.observe(viewLifecycleOwner) {
                listProduct ->
            listProduct.let { adapter.submitList(it) }
        }

        // size of this RecyclerView is fixed
//        recyclerView.setHasFixedSize(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

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