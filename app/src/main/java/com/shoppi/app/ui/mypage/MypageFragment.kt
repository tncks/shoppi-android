package com.shoppi.app.ui.mypage


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.shoppi.app.R
import com.shoppi.app.ui.common.MoveToGalleryCameraPickOneActivity
import com.shoppi.app.ui.feed.PictureWithMemoActivity
import com.shoppi.app.ui.feed.PictureWithoutMemoActivity
import com.shoppi.app.ui.schedule.ScheduleWriteEditActivity
import com.shoppi.app.ui.schedule.TheLocationActivity
import com.shoppi.app.ui.signstep.*

class MypageFragment : Fragment() {

    private var viewProfile: View? = null


    /*-------------------------------------------------------------------*/


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewProfile = inflater.inflate(R.layout.fragment_mypage, container, false)

        return viewProfile
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setTestRestEventsAtOnce(view)

    }

    private fun setTestRestEventsAtOnce(view: View) {

        view.findViewById<Button>(R.id.btn_move_test_1).setOnClickListener {
            val intent = Intent(activity, MoveToGalleryCameraPickOneActivity::class.java)
            startActivity(intent)
        }



        view.findViewById<Button>(R.id.btn_move_test_2).setOnClickListener {
            val intent = Intent(activity, PictureWithMemoActivity::class.java)
            startActivity(intent)
        }



        view.findViewById<Button>(R.id.btn_move_test_3).setOnClickListener {
            val intent = Intent(activity, PictureWithoutMemoActivity::class.java)
            startActivity(intent)
        }



        view.findViewById<Button>(R.id.btn_move_test_4).setOnClickListener {
            val intent = Intent(activity, JoinNormalNewActivity::class.java)
            startActivity(intent)
        }



        view.findViewById<Button>(R.id.btn_move_test_5).setOnClickListener {
            val intent = Intent(activity, ForgotPwNeedEmailAddressActivity::class.java)
            startActivity(intent)
        }



        view.findViewById<Button>(R.id.btn_move_test_6).setOnClickListener {
            val intent = Intent(activity, ForgotPwConfirmEmailCheckValidActivity::class.java)
            startActivity(intent)
        }




        view.findViewById<Button>(R.id.btn_move_test_7).setOnClickListener {
            val intent = Intent(activity, JoinEntireViewOfTermsAgreeActivity::class.java)
            startActivity(intent)
        }



        view.findViewById<Button>(R.id.btn_move_test_8).setOnClickListener {
            val intent = Intent(activity, ScheduleWriteEditActivity::class.java)
            startActivity(intent)
        }



        view.findViewById<Button>(R.id.btn_move_test_9).setOnClickListener {
            val intent = Intent(activity, ForgotResetPasswordRenewActivity::class.java)
            startActivity(intent)
        }



        view.findViewById<Button>(R.id.btn_move_test_10).setOnClickListener {
            val intent = Intent(activity, JoinCompleteSuccessActivity::class.java)
            startActivity(intent)
        }



        view.findViewById<Button>(R.id.btn_move_test_11).setOnClickListener {
            val intent = Intent(activity, JoinIncompleteInvalidorfailActivity::class.java)
            startActivity(intent)
        }



        view.findViewById<Button>(R.id.btn_move_test_12).setOnClickListener {
            val intent = Intent(activity, TheLocationActivity::class.java)
            startActivity(intent)
        }


    }


}