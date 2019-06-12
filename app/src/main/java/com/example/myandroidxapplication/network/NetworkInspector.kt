package com.example.myandroidxapplication.network

import com.example.myandroidxapplication.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor

class NetworkInspector {
    companion object {
        fun getChuckNetworkInspector(): ChuckInterceptor{
            return ChuckInterceptor(ContextProvider.context)
                .showNotification(BuildConfig.DEBUG)
        }
    }
}