package com.example.module_webview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.example.a11699.comp_base.activity.BaseFragment
import com.example.a11699.comp_base.util.toast.ToastUtil
import kotlinx.android.synthetic.main.fragment_webview.*
import org.json.JSONException
import org.json.JSONObject

/**
 * @Author Yu
 * @Date 2020/12/1 16:02
 * @Description
 */
class WebFragment : BaseFragment() {
    private val appInterface: AppInterface by lazy {
        AppInterface()
    }

    val disableParentScrollWebView: DisableParentScrollWebView by lazy {
        DisableParentScrollWebView(requireActivity())
    }
    lateinit var mWebTransportModel: WebTransportModel
    private var url: String? = null

    fun setWebTransportModel(webTransportModel: WebTransportModel) {
        mWebTransportModel = webTransportModel
        url = mWebTransportModel.url
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_webview
    }

    override fun initView() {
    }

    override fun observeLiveData() {
    }

    override fun initViewData() {

        val layoutParams: ViewGroup.LayoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        )
        disableParentScrollWebView.layoutParams = layoutParams
        layou.addView(disableParentScrollWebView)
        setView()
        loadUrl()
    }


    @SuppressLint("AddJavascriptInterface", "SetJavaScriptEnabled", "JavascriptInterface")
    private fun setView() {
        /***設置webView */
        if (Build.VERSION.SDK_INT >= 21) {
            disableParentScrollWebView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        disableParentScrollWebView.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        //設置支持js代码
        disableParentScrollWebView.settings.javaScriptEnabled = true
        disableParentScrollWebView.settings.defaultTextEncodingName = "UTF-8"
        disableParentScrollWebView.addJavascriptInterface(appInterface, "appInterface")
        //設置支持插件
        disableParentScrollWebView.settings.setPluginState(WebSettings.PluginState.ON)
        //支持缩放
//        disableParentScrollWebView.getSettings().setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
//        disableParentScrollWebView.getSettings().setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
//        disableParentScrollWebView.getSettings().setAllowFileAccessFromFileURLs(true);
//        disableParentScrollWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        disableParentScrollWebView.settings.allowFileAccess = false
        disableParentScrollWebView.settings.allowFileAccessFromFileURLs = false
        disableParentScrollWebView.settings.allowUniversalAccessFromFileURLs = false
        disableParentScrollWebView.settings.setSupportZoom(true)
        //支持缓存
        disableParentScrollWebView.settings.setAppCacheEnabled(true)
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //            WebView.setWebContentsDebuggingEnabled(true);
        //        }
        //設置多窗口
        // webSettings.setSupportMultipleWindows(true);
        disableParentScrollWebView.setDownloadListener(MyWebViewDownLoadListener())
        disableParentScrollWebView.isHorizontalScrollBarEnabled = false //水平不显示
        disableParentScrollWebView.isVerticalScrollBarEnabled = false //垂直不显示
        disableParentScrollWebView.settings.useWideViewPort = true
        disableParentScrollWebView.settings.loadWithOverviewMode = true
        disableParentScrollWebView.setBackgroundColor(0) // 設置背景色
        disableParentScrollWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        disableParentScrollWebView.settings.domStorageEnabled = true
        disableParentScrollWebView.settings.mediaPlaybackRequiresUserGesture = false
        Log.d("hhq", disableParentScrollWebView.settings.userAgentString)
        try {
            val jsonObject = JSONObject()
            jsonObject.put("LanguageCode", "")
            jsonObject.put("PlatForm", "Android")
            val appInfoJsonObject = JSONObject()
            appInfoJsonObject.put("NetType", "0")
            jsonObject.put("AppInfo", appInfoJsonObject)
            Log.d("hhq", "user-Agent--" + disableParentScrollWebView.settings.getUserAgentString())
            disableParentScrollWebView.settings.setUserAgentString(jsonObject.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        /**WebViewClient主要幫助WebView处理各种通知、请求事件 */
        disableParentScrollWebView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

            }

            //页面加载结束
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                if (disableParentScrollWebView == null) {
                    return
                }
                //                appInterface.login();
//                appInterface.JsUserInfo();
//
//                if (mWebTransportModel.diamond != null && !mWebTransportModel.diamond.isEmpty()) {
//                    appInterface.load(mWebTransportModel.diamond);
//                }
//                String dd = "function invite(str) {window.appInterface.invite(str)}";
//                disableParentScrollWebView.loadUrl("javascript:" + dd);//注入js函数，
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url == null) {
                    return false
                }
                if (mOverrideUrlCallBack != null) {
                    val override: Boolean = mOverrideUrlCallBack!!.shouldOverrideUrlLoading(view, url)
                    if (override) {
                        return true
                    }
                }
                try {
                    if (url.startsWith("weixin://") || url.startsWith("alipays://") || url.startsWith("mailto://") || url.startsWith("tel://")) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    }
                    if (url.startsWith("tbopen://")) {
                        // dns 劫持，会被定位到淘宝上，这边拦截下
                        ToastUtil.show(context, "网络异常请重新连接")
                        return false
                    }
                } catch (e: Exception) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    ToastUtil.show(context, "您还没有安装支付宝或者微信客户端")
                    return false
                }
                return false
            }
        }

        //* WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等比如 **/
        disableParentScrollWebView.setWebChromeClient(object : WebChromeClient() {
            override fun onShowFileChooser(disableParentScrollWebView: WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: FileChooserParams): Boolean {
//                if (uploadMessage != null) {
//                    uploadMessage.onReceiveValue(null);
//                    uploadMessage = null;
//                }
                uploadMessage = filePathCallback
                openImageChooserActivity()
                return true
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                myProgressBar.setProgress(newProgress)
                if (newProgress == 100) {
                    myProgressBar.setVisibility(View.GONE)
                }
            }
        })
    }


    private val REQUEST_FILE_PICKER = 30001
    var uploadMessage: ValueCallback<Array<Uri>>? = null

    /**
     * 开启相册
     */
    private fun openImageChooserActivity() {
        getLocalPicture(REQUEST_FILE_PICKER)
    }

    //取本地相册
    private fun getLocalPicture(reqCode: Int) {
        var photointent: Intent? = null
        photointent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        photointent.type = "image/*"
        startActivityForResult(photointent, reqCode)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != REQUEST_FILE_PICKER || uploadMessage == null) {
            return
        }
        var results: Array<Uri?>? = null
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                val dataString = intent.dataString
                val clipData = intent.clipData
                if (clipData != null) {
                    results = arrayOfNulls(clipData.itemCount)
                    for (i in 0 until clipData.itemCount) {
                        val item = clipData.getItemAt(i)
                        results[i] = item.uri
                    }
                }
                if (dataString != null) {
                    results = arrayOf(Uri.parse(dataString))
                }
            }
        }
        uploadMessage!!.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent))
    }

    private inner class MyWebViewDownLoadListener : DownloadListener {
        override fun onDownloadStart(url: String, userAgent: String, contentDisposition: String, mimetype: String, contentLength: Long) {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    fun loadUrl() {
        if (url == null) {
            return
        }
        Log.d("zjc", url)
        disableParentScrollWebView.loadUrl(url)
    }

    override fun onDestroy() {
        if (disableParentScrollWebView != null) {
            disableParentScrollWebView.webChromeClient = null
            disableParentScrollWebView.setDownloadListener(null)
            disableParentScrollWebView.webChromeClient = null
            var parent = disableParentScrollWebView.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(disableParentScrollWebView)
            }
            disableParentScrollWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            disableParentScrollWebView.stopLoading()
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            disableParentScrollWebView.settings.javaScriptEnabled = false
            disableParentScrollWebView.clearCache(true)
            disableParentScrollWebView.clearHistory()
            disableParentScrollWebView.removeAllViews()
            disableParentScrollWebView.destroy()
        }
        if (layou != null) {
            layou.removeAllViews()
        }
        super.onDestroy()
    }


    fun keyBack(): Boolean {
        if (disableParentScrollWebView.canGoBack() && !disableParentScrollWebView.url.contains(mWebTransportModel.url)) {
            disableParentScrollWebView.goBack()
        } else {
            requireActivity().finish()
        }
        return true
    }

    /**
     * Android与JS通信的接口
     */
    internal class AppInterface() {
        /**
         * 跳转客服
         */
        @JavascriptInterface
        fun CustomService() {
        }

        /**
         * 查看用户资料
         */
        @JavascriptInterface
        fun showUserInfo(json: String?) {
            try {
                Log.d("hhq", "dddd")
                val jsonObject = JSONObject(json)
                val userId = jsonObject.getString("userId")
                //这里可以做跳转页面啥的操作
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }


    /**
     * 添加额外拦截器
     */
    private var mOverrideUrlCallBack: OverrideUrlCallBack? = null

    fun setOverrideUrlCallBack(overrideUrlCallBack: OverrideUrlCallBack?) {
        mOverrideUrlCallBack = overrideUrlCallBack
    }

    interface OverrideUrlCallBack {
        fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean
    }
}