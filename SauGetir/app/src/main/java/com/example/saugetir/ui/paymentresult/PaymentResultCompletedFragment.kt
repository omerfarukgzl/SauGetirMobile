package com.example.saugetir.ui.paymentresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.saugetir.R
import com.example.saugetir.databinding.FragmentPaymentCompletedResultBinding


class PaymentResultCompletedFragment : Fragment() {
    private var _binding: FragmentPaymentCompletedResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPaymentCompletedResultBinding.inflate(inflater, container, false)

        binding.button4.setOnClickListener {
            val action = PaymentResultCompletedFragmentDirections.actionPaymentCompletedResultFragmentToMainActivity()
            it.findNavController().navigate(action)
            activity?.finish()
        }

        return binding.root
    }
}