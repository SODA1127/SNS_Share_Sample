package com.soda1127.snssharesample

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class SampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.kakao_app_native_key))
    }

}
