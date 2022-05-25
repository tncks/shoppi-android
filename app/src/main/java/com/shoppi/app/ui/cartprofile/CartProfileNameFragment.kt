package com.shoppi.app.ui.cartprofile

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shoppi.app.R
import com.shoppi.app.common.BFLAG
import com.shoppi.app.databinding.FragmentCartProfileNameBinding

class CartProfileNameFragment : Fragment() {
    private lateinit var binding: FragmentCartProfileNameBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCartProfileNameBinding.inflate(inflater,container,false)

        binding.ivUnrealBackSimple.setOnClickListener {
            val builder = AlertDialog.Builder(context, R.style.MDialogTheme)
            fillDialogContents(builder)
            setDialogLayoutStyleAndShow(builder)
        }

        binding.tvSimpleCompleteEditSubmit.setOnClickListener {
            BFLAG = true
            findNavController().navigate(R.id.action_cart_profile_name_to_cart_profile_detail)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun fillDialogContents(builder: AlertDialog.Builder) {
        builder.setTitle(
            "작성 중인 내용이 있습니다." + "\n" +
                    "나가시겠습니까?"
        )
            .setMessage("지금까지 작성한 내용이 사라집니다.")
            .setPositiveButton("나가기",
                DialogInterface.OnClickListener { dialog, id ->

                    this.activity?.finish()
                })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, id ->
                    Log.i("dummy", "dummy")
                })
    }

    private fun setDialogLayoutStyleAndShow(builder: AlertDialog.Builder) {
        val aD = builder.show()
        /*--------------------*/
        val btnPositive = aD.getButton(AlertDialog.BUTTON_POSITIVE)
        val btnNegative = aD.getButton(AlertDialog.BUTTON_NEGATIVE)
        val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
        layoutParams.marginEnd = 40
        btnNegative.layoutParams = layoutParams
    }
}