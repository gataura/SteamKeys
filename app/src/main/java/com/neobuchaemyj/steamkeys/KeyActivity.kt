package com.neobuchaemyj.steamkeys

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.neobuchaemyj.steamkeys.presenter.KeysPresenter
import com.neobuchaemyj.steamkeys.presenter.KeysView
import com.neobuchaemyj.steamkeys.presenter.helper.find
import android.content.ActivityNotFoundException



class KeyActivity : AppCompatActivity(), KeysView {

    private lateinit var mAdView : AdView
    private lateinit var keyText: TextView
    private lateinit var copyButton: ImageView
    private lateinit var shareButton: ImageView
    private lateinit var rateApp: Button

    private lateinit var presenter: KeysPresenter

    private lateinit var clipboard: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key)

        presenter = KeysPresenter()
        presenter.bind(this)

        clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        mAdView = find(R.id.adView_keys)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object: AdListener() {
            override fun onAdClosed() {
                mAdView.loadAd(adRequest)
            }
        }

        keyText = find(R.id.steam_key)
        copyButton = find(R.id.copy_button)
        shareButton = find(R.id.share_button)
        rateApp = find(R.id.rate_app)

        val uri = Uri.parse("market://details?id=" + this.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )

        rateApp.setOnClickListener {
            try {
                startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.packageName)
                    )
                )
            }

        }

        copyButton.setOnClickListener {
            presenter.copyText(keyText.text.toString(), clipboard)
        }

        shareButton.setOnClickListener {
            presenter.onShareClickAction(keyText.text.toString())
        }
    }

    override fun onError(t: Throwable?) {

    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun startIntent(sharingIntent: Intent) {
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }
}
