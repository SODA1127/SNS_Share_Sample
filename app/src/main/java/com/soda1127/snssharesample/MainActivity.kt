package com.soda1127.snssharesample

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.internal.ShareFeedContent
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.soda1127.snssharesample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val facebookCallbackManager by lazy { CallbackManager.Factory.create() }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        Log.e(TAG, Utility.getKeyHash(this))
        initViews()
    }

    private fun initViews() {
        initKakaoButton()
        initFacebookButton()
        initInstaButton()
    }

    private fun initKakaoButton() {
        binding.kakaoButton.setOnClickListener {
            shareKakaoLink()
        }
    }

    private fun initFacebookButton() {
        binding.facebookButton.setOnClickListener {
            shareFacebookLink()
        }
    }

    private fun initInstaButton() {
        binding.instaButton.setOnClickListener {
            shareInstaLink()
        }
    }

    private fun shareKakaoLink() {
        // 공유할 웹페이지 URL
        //  * 주의: 개발자사이트 Web 플랫폼 설정에 공유할 URL의 도메인이 등록되어 있어야 합니다.
        val url = "https://www.banksalad.com"

        if (LinkClient.instance.isKakaoLinkAvailable(this)) {
            LinkClient.instance.scrapTemplate(this, url) { linkResult, error ->
                if (error != null) {
                    Log.e(TAG, "카카오링크 보내기 실패", error)
                } else if (linkResult != null) {
                    Log.d(TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                    startActivity(linkResult.intent)

                    // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w(TAG, "Warning Msg: ${linkResult.warningMsg}")
                    Log.w(TAG, "Argument Msg: ${linkResult.argumentMsg}")
                }
            }
        } else {
            WebSharerClient.instance.scrapTemplateUri(url).let {

                KakaoCustomTabsClient.openWithDefault(this, it)
                // 또는
                //startActivity(Intent(Intent.ACTION_VIEW, it))
            }
        }
    }

    private fun shareFacebookLink() {
        val imageUrl = "https://imgur.com/c0C2CQq.jpg"
        val url = "https://www.banksalad.com"
        val content = ShareFeedContent.Builder().setLink(url).setPicture(imageUrl).build()
        val shareDialog = ShareDialog(this).apply {
            registerCallback(facebookCallbackManager, object : FacebookCallback<Sharer.Result> {
                override fun onSuccess(result: Sharer.Result?) {
                }

                override fun onCancel() {
                }

                override fun onError(error: FacebookException?) {
                }
            })
        }
        shareDialog.show(content)
    }

    private fun shareInstaLink() {

    }
}
