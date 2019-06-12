package com.neobuchaemyj.steamkeys

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.ads.*
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.neobuchaemyj.steamkeys.presenter.KeysPresenter
import com.neobuchaemyj.steamkeys.presenter.KeysView
import com.neobuchaemyj.steamkeys.presenter.helper.find

class MainActivity : AppCompatActivity(), KeysView {

    private lateinit var presenter: KeysPresenter
    private lateinit var getButton: Button

//    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var mAdView : AdView
    private lateinit var mRewardedVideoAd: RewardedVideoAd

    private var doneOnce = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = KeysPresenter()
        presenter.bind(this)


        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")

//        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
//        mInterstitialAd.loadAd(AdRequest.Builder().build())
        val intent = Intent(this, KeyActivity::class.java)

//        mInterstitialAd.adListener = object: AdListener() {
//
//            override fun onAdClosed() {
//                mInterstitialAd.loadAd(AdRequest.Builder().build())
//            }
//
//            override fun onAdLoaded() {
//                if (doneOnce){
//                    mInterstitialAd.show()
//                    doneOnce = false
//                }
//
//            }
//
//        }

        mAdView = find(R.id.adView_main)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object: AdListener() {
            override fun onAdClosed() {
                mAdView.loadAd(adRequest)
            }

        }

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", AdRequest.Builder().build())
        mRewardedVideoAd.rewardedVideoAdListener = object: RewardedVideoAdListener {
            override fun onRewardedVideoAdLeftApplication() {
            }

            override fun onRewardedVideoAdLoaded() {
            }

            override fun onRewardedVideoAdOpened() {
            }

            override fun onRewardedVideoCompleted() {
            }

            override fun onRewarded(p0: RewardItem?) {
            }

            override fun onRewardedVideoStarted() {
            }

            override fun onRewardedVideoAdFailedToLoad(p0: Int) {
            }

            override fun onRewardedVideoAdClosed() {
                startActivity(intent)
            }

        }

        loadRewardedVideoAd()


        getButton = find(R.id.get_button)

        getButton.setOnClickListener {
            if (mRewardedVideoAd.isLoaded) {
                mRewardedVideoAd.show()
            } else {
                Log.d("Rewarded", "err")
            }
        }

    }

    override fun onError(t: Throwable?) {

    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun loadRewardedVideoAd(){
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/1033173712", AdRequest.Builder().build())
    }

    override fun startIntent(sharingIntent: Intent) {
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putBoolean("doOnce", doneOnce)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null){
            doneOnce = savedInstanceState.getBoolean("doOnce")
        }
    }

}
