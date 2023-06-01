package com.example.lifemate.ui.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.lifemate.databinding.FragmentCustomDialogBinding

class CustomDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentCustomDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val parameter = arguments?.getString(param)
        binding.apply {
            tvDesc.text = parameter
            btnOk.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    companion object {
        private const val param = "PARAM"
        fun newInstance(parameter: String): CustomDialogFragment {
            val fragment = CustomDialogFragment()
            val args = Bundle()
            args.putString(param, parameter)
            fragment.arguments = args
            return fragment
        }
    }

}