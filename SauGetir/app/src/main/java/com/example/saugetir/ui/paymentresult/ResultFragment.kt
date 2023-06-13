package com.example.saugetir.ui.paymentresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.saugetir.R
import com.example.saugetir.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentResultBinding.inflate(inflater, container, false)

        val result = (activity as PaymentResultActivity).getResult()!!

        if(result){
            val action = ResultFragmentDirections.actionResultFragmentToPaymentCompletedResultFragment()
            findNavController().navigate(action)
        }
        else{
            val action = ResultFragmentDirections.actionResultFragmentToPaymentResultNotCompletedFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }


}