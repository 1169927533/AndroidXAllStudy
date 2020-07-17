package com.example.a11699

import android.content.Context
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider

/**
 *Create time 2020/6/18
 *Create Yu
 *description:
 */
class Config {
    companion object {
        lateinit var ossConfigBean: OssConfigBean

        // 访问的endpoint地址
        var OSS_ENDPOINT: String = "http://oss-cn-hangzhou.aliyuncs.com"

        //callback 测试地址
        var OSS_CALLBACK_URL = "http://oss-demo.aliyuncs.com:23450"

        // STS 鉴权服务器地址 这个需要配置自己服务器的地址
        var STS_SERVER_URL = "http://192.168.137.1:7080/"
        var BUCKET_NAME = "cangkuone"
        var OBJECTKEY = "lucklive/" //你上传到阿里云文件的路径

        //初始化配置数据
        fun initData(ossConfigBean: OssConfigBean) {
            Companion.ossConfigBean = ossConfigBean
            // 访问的endpoint地址
            OSS_ENDPOINT = Companion.ossConfigBean.OSS_ENDPOINT
            OSS_CALLBACK_URL = Companion.ossConfigBean.OSS_CALLBACK_URL   //callback 测试地址
            // STS 鉴权服务器地址 这个需要配置自己服务器的地址
            STS_SERVER_URL = Companion.ossConfigBean.STS_SERVER_URL
            BUCKET_NAME = Companion.ossConfigBean.BUCKET_NAME

            OBJECTKEY = Companion.ossConfigBean.OBJECTKEY// "lucklive/" //你上传到阿里云文件的路径
        }

        fun initOss(context: Context, endpoint: String,  accessKeyId: String = "", accessKeySecret: String="", securityToken: String="", expiration: String=""): OssService {
            //使用自己的获取STSToken的类
            val credentialProvider: OSSCredentialProvider = StsGetter(accessKeyId, accessKeySecret, securityToken, expiration)
            return OssService(OSSClient(context, endpoint, credentialProvider))
        }
    }
}