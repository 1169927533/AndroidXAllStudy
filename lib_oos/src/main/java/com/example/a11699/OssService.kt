package com.example.a11699

import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.OSS
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask
import com.alibaba.sdk.android.oss.model.ListObjectsRequest
import com.alibaba.sdk.android.oss.model.ListObjectsResult
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult

/**
 *Create time 2020/6/18
 *Create Yu
 *description:
 */
class OssService(oss: OSS) {
    var oss: OSS = oss

    var pushImgSuccess: ((url: String) -> Unit)? = null //上传图片成功
    var pushImgError: ((request: PutObjectRequest?, clientException: ClientException?, serviceException: ServiceException?) -> Unit)? = null //传图片失败
    var getOssAllFile: ((request: ListObjectsRequest?, result: ListObjectsResult) -> Unit)? = null//获取oos里面的全部文件
    var getOssAlFileError: ((request: ListObjectsRequest?, clientExcepion: ClientException?, serviceException: ServiceException?) -> Unit)? = null //获取全部文件失败

    fun asyncSendImg(fileName: String, picturePath: String) {
        val put = PutObjectRequest(Config.BUCKET_NAME, Config.OBJECTKEY + fileName + ".png", picturePath)
        var task = oss.asyncPutObject(put, object : OSSCompletedCallback<PutObjectRequest, PutObjectResult> {
            override fun onSuccess(request: PutObjectRequest?, putResult: PutObjectResult?) {
                var fileUrl = oss.presignPublicObjectURL(Config.BUCKET_NAME, Config.OBJECTKEY + fileName + ".png")
                pushImgSuccess?.let {
                    it.invoke(fileUrl)
                }
            }

            override fun onFailure(request: PutObjectRequest?, clientException: ClientException?, serviceException: ServiceException?) {
                pushImgError?.let {
                    it.invoke(request, clientException, serviceException)
                }
            }
        })

    }

    fun asyncListObjects() {
        val listObjects = ListObjectsRequest(Config.BUCKET_NAME)
        val task: OSSAsyncTask<*> = oss.asyncListObjects(listObjects, object : OSSCompletedCallback<ListObjectsRequest?, ListObjectsResult> {
            override fun onSuccess(request: ListObjectsRequest?, result: ListObjectsResult) {
                getOssAllFile?.let {
                    it.invoke(request, result)
                }
                /* for (i in result.objectSummaries.indices) {
                    Log.d("AyncListObjects", "object: " + result.objectSummaries[i].key + " "
                            + result.objectSummaries[i].eTag + " "
                            + result.objectSummaries[i].lastModified)
                }*/
            }

            override fun onFailure(request: ListObjectsRequest?, clientExcepion: ClientException?, serviceException: ServiceException?) {
                getOssAlFileError?.let {
                    it.invoke(request, clientExcepion, serviceException)
                }
                /* // 请求异常。
                 if (clientExcepion != null) {
                     // 本地异常，如网络异常等。
                     clientExcepion.printStackTrace()
                 }
                 if (serviceException != null) {
                     // 服务异常。
                     Log.e("ErrorCode", serviceException.getErrorCode())
                     Log.e("RequestId", serviceException.getRequestId())
                     Log.e("HostId", serviceException.getHostId())
                     Log.e("RawMessage", serviceException.getRawMessage())
                 }*/
            }
        })
        task.waitUntilFinished()
    }


}