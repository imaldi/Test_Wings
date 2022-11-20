package com.aim2u.test_wings.ui.checkout_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.aim2u.test_wings.R
import com.aim2u.test_wings.WingsApplication
import com.aim2u.test_wings.adapter.CheckoutItemAdapter
import com.aim2u.test_wings.databinding.FragmentCheckoutBinding
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
 * Use the [CheckoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.transactionHeader.observe(viewLifecycleOwner){

        }

        var adapter = CheckoutItemAdapter({
            transactionDetail ->
        },{
            selected, index ->
        })
        binding.transactioDetailList.adapter = adapter
        sharedViewModel.transactionDetail.observe(viewLifecycleOwner){ listTransactionDetail ->
            listTransactionDetail.let { adapter.submitList(it) }

            Toast.makeText(context?.applicationContext, "TransDetail All Size: ${listTransactionDetail?.size}", Toast.LENGTH_SHORT)
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
         * @return A new instance of fragment CheckoutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckoutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}