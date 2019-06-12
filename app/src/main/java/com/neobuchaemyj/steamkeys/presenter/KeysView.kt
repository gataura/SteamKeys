package com.neobuchaemyj.steamkeys.presenter

import android.content.Intent

interface KeysView: BaseView {

    fun onError(t:Throwable?)
    fun showToast(text: String)
    fun startIntent(sharingIntent: Intent)

}