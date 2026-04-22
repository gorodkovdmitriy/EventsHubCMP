package com.gorodkovdmitriy.eventshub.common.network

import com.gorodkovdmitriy.eventshub.common.network.response.AuthResponseEntity
import eu.anifantakis.lib.ksafe.KSafe

class TokenManager(private val ksafe: KSafe) {
    private companion object {
        const val ACCESS_TOKEN_KEY = "authToken"
        const val REFRESH_TOKEN_KEY = "refreshToken"
    }

    suspend fun saveFromAuthResponse(authResponseEntity: AuthResponseEntity) {
        ksafe.put(ACCESS_TOKEN_KEY, authResponseEntity.accessToken)
        ksafe.put(REFRESH_TOKEN_KEY, authResponseEntity.refreshToken)
    }

    fun getAccessToken(): String {
        return ksafe.getDirect(key = ACCESS_TOKEN_KEY, defaultValue = "")
    }

    fun getRefreshToken(): String {
        return ksafe.getDirect(key = ACCESS_TOKEN_KEY, defaultValue = "")
    }
}