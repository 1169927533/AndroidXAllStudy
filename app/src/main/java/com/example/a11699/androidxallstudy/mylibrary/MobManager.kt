package com.example.util

import android.content.Context
import android.util.Log
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.wechat.friends.Wechat

import com.mob.MobSDK

/**
 * @Author Yu
 * @Date 2020/11/30 10:44
 * @Description TODO
 */
class MobManager private constructor() {
    lateinit var context: Context

    companion object {
        val instance: MobManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { MobManager() }
    }
    fun init(context: Context) {
        this.context = context
        MobSDK.init(context)
        MobSDK.submitPolicyGrantResult(true, null)
    }
    fun authorization(authorizationStatusListener:((token:String?,unionid:String?,code:Int)->Unit)){
        val wechat: Platform = ShareSDK.getPlatform(Wechat.NAME)
        wechat.platformActionListener = object : PlatformActionListener {
            override fun onComplete(platform: Platform, i: Int, hashMap: HashMap<String?, Any?>?) {
                val token = platform.db.token
                val unionid = hashMap?.get("unionid").toString()
                authorizationStatusListener.invoke(token,unionid,0)
            }

            override fun onError(platform: Platform?, i: Int, throwable: Throwable) {
                authorizationStatusListener.invoke(null,null,i)
            }

            override fun onCancel(platform: Platform?, i: Int) {
                var s = platform
                Log.i("zkc","onCancel")
            }
        }
        wechat.SSOSetting(false)
        wechat.showUser(null)
    }

}