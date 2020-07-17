package com.example.a11699

import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken

/**
 *Create time 2020/6/18
 *Create Yu
 *description:
 */
class StsGetter(accessKeyId: String, accessKeySecret: String, securityToken: String, expiration: String) : OSSFederationCredentialProvider() {

    private var accessKeyId: String = accessKeyId
    private var accessKeySecret: String = accessKeySecret
    private var securityToken: String = securityToken
    private var expiration: String = expiration

    override fun getFederationToken(): OSSFederationToken {
        return OSSFederationToken(accessKeyId, accessKeySecret, securityToken, expiration)
    }
}