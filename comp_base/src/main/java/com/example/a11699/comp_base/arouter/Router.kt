package com.example.a11699.comp_base.arouter

/**
 * @Author Yu
 * @Date 2020/11/27 11:07
 * @Description TODO
 */
class RouterConstant {

    object Main {
        private const val rootPath = "/main"
        const val MAIN = rootPath + "/mainvvvvv"
    }

    object Three {
        private const val rootPath = "/main"
        const val MAIN = rootPath + "/ThreeActivity"
    }

    object Web{
        private const val rootPath = "/web"
        const val MAIN  = rootPath+"/webview"
        const val KEY_WEB_MODEL = rootPath + "/web_url_model"

    }
}