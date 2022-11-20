package com.aim2u.test_wings.ui.product_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.aim2u.test_wings.WingsApplication
import com.aim2u.test_wings.ui.product_fragments.ProductDetailFragmentArgs
import com.aim2u.test_wings.databinding.FragmentProductDetailBinding
import com.aim2u.test_wings.databinding.FragmentProductListBinding
import com.aim2u.test_wings.ui.shared_view_model.SharedViewModel
import com.aim2u.test_wings.ui.shared_view_model.SharedViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val safeArgs: ProductDetailFragmentArgs by navArgs()
    private var productCode: String? = null
    private var productName: String? = null
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
//    by lazy {
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productCode = safeArgs.productId
        productName = safeArgs.productName
        val immProductCode = productCode ?: ""
        val immProductName = productName ?: ""

        sharedViewModel.detailedProduct.observe(viewLifecycleOwner){
            binding.productName.text = immProductName
            binding.priceTextView.text = it.price.toString()
            binding.dimensionTextView.text = it.dimension
            binding.unitTextView.text = it.unit
        }
        sharedViewModel.setDetailedProduct(immProductCode)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}