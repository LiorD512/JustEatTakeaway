package com.liordahan.justeattakeaway.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showErrorToast(title: String, length: Int = Toast.LENGTH_LONG) {
    val toast = Toast(requireContext())
    toast.duration = length
    toast.setText(title)
    toast.show()
}