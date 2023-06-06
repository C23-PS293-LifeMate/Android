package com.example.lifemate.ui.customview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import com.example.lifemate.R
import com.example.lifemate.databinding.FragmentConnectionFailedDialogBinding
import com.example.lifemate.databinding.FragmentCustomDialogBinding

class ConnectionFailedDialogFragment :  DialogFragment()  {
    private var _binding: FragmentConnectionFailedDialogBinding? = null
    private val binding get() = _binding!!
    private var refreshListener: RefreshListener? = null

    interface RefreshListener {
        fun onRefresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentConnectionFailedDialogBinding.inflate(inflater,container,false)
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
        binding.apply {
            btnOk.setOnClickListener {
                refreshListener?.onRefresh()
                dialog?.dismiss()
            }
        }
    }



    fun setRefreshListener(listener: RefreshListener) {
        refreshListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}