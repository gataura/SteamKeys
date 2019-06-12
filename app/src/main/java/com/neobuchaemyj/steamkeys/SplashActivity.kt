package com.neobuchaemyj.steamkeys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ProgressBar
import com.google.android.gms.ads.*
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.neobuchaemyj.steamkeys.presenter.helper.find

class SplashActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    private var doneOnce = true

    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var mRewardedVideoAd: RewardedVideoAd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = find(R.id.progress_bar)

        progressBar.visibility = View.VISIBLE

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        val intent = Intent(this, MainActivity::class.java)

        mInterstitialAd.adListener = object: AdListener() {

            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
                startActivity(intent)
            }

            override fun onAdLoaded() {

                if (doneOnce){
                    mInterstitialAd.show()
                    doneOnce = false
                }
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
    }

    private fun loadRewardedVideoAd(){
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/1033173712", AdRequest.Builder().build())
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
