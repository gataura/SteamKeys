package com.neobuchaemyj.steamkeys.presenter.helper

import android.app.Activity
import androidx.annotation.IdRes
import android.view.View

fun <T : View> Activity.find(@IdRes id: Int, clickListener: ((view: View) -> Unit)? = null): T {
    val res = findViewById<T>(id)
    clickListener?.let { res.setOnClickListener(it) }

    return res
}