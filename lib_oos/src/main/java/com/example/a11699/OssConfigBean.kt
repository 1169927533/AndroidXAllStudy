package com.example.a11699

/**
 *Create time 2020/6/19
 *Create Yu
 *description:OSS配置的全部参数
 */
class OssConfigBean {
    var OSS_ENDPOINT: String = "" // 访问的endpoint地址
    var OSS_CALLBACK_URL = "" //callback 测试地址
    var STS_SERVER_URL = ""  // STS 鉴权服务器地址 这个需要配置自己服务器的地址
    var BUCKET_NAME = ""     //oss指定的仓库名字
    var OBJECTKEY = ""      //仓库里里的文件

    /**以上为必填选项**/

    var OSS_ACCESS_KEY_ID = ""
    var OSS_ACCESS_KEY_SECRET = ""
    var SECUROITYToken = ""
    var EXPIRATION = ""

    constructor(build: Builder) {
        this.OSS_ENDPOINT = build.OSS_ENDPOINT
        this.OSS_CALLBACK_URL = build.OSS_CALLBACK_URL
        this.STS_SERVER_URL = build.STS_SERVER_URL
        this.BUCKET_NAME = build.BUCKET_NAME
        this.OBJECTKEY = build.OBJECTKEY
        this.OSS_ACCESS_KEY_ID = build.OSS_ACCESS_KEY_ID
        this.OSS_ACCESS_KEY_SECRET = build.OSS_ACCESS_KEY_SECRET
        this.SECUROITYToken = build.SECUROITYToken
        this.EXPIRATION = build.EXPIRATION
    }

    class Builder {
        var OSS_ENDPOINT: String = "" // 访问的endpoint地址
        var OSS_CALLBACK_URL = "" //callback 测试地址
        var STS_SERVER_URL = ""  // STS 鉴权服务器地址 这个需要配置自己服务器的地址
        var BUCKET_NAME = ""     //oss指定的仓库名字
        var OBJECTKEY = ""      //仓库里里的文件

        /**以上为必填选项**/

        var OSS_ACCESS_KEY_ID = ""
        var OSS_ACCESS_KEY_SECRET = ""
        var SECUROITYToken = ""
        var EXPIRATION = ""

        constructor(OSS_ENDPOINT: String, OSS_CALLBACK_URL: String, STS_SERVER_URL: String, BUCKET_NAME: String, OBJECTKEY: String) {
            this.OSS_ENDPOINT = OSS_ENDPOINT
            this.OSS_CALLBACK_URL = OSS_CALLBACK_URL
            this.STS_SERVER_URL = STS_SERVER_URL
            this.BUCKET_NAME = BUCKET_NAME
            this.OBJECTKEY = OBJECTKEY
        }

        fun build(): OssConfigBean {
            return OssConfigBean(this)
        }
    }


}