package com.neobuchaemyj.steamkeys.presenter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent

class KeysPresenter: BasePresenter<KeysView>() {

    fun copyText(text: String, clipboard: ClipboardManager) {
        val clip: ClipData = ClipData.newPlainText("copied key", text)
        clipboard.primaryClip = clip
        view?.showToast("Copied to clipboard")
    }

    fun onShareClickAction(text: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I got this steam key in Steam Keys App: ")
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text)
        view?.startIntent(sharingIntent)
    }

}