package com.shoppi.app.ui.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
    val ft: FragmentTransaction = fragmentManager.beginTransaction()
    ft.detach(fragment).attach(fragment).commit()
    fragmentManager.executePendingTransactions()
}