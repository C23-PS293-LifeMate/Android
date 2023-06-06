package com.example.lifemate.ui.customview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.lifemate.R
import com.example.lifemate.databinding.FragmentCustomDialogBinding

class CustomDialogFragment : DialogFragment() {

    private var _binding: FragmentCustomDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screenWidth = resources.displayMetrics.widthPixels
        val horizontalMargin = resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin)
        val dialogWidth = screenWidth - (2 * horizontalMargin)

        dialog?.window!!.apply {
            val params: WindowManager.LayoutParams = this.attributes
            params.width = dialogWidth
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            attributes.windowAnimations = android.R.transition.fade
            setGravity(Gravity.CENTER)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}