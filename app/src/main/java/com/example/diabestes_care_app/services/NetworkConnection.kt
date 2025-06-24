package com.example.diabestes_care_app.services

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData

class NetworkConnection(private val context: Context) : LiveData<Boolean>() {

    private var connectionManger: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        updateCondition()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectionManger.registerDefaultNetworkCallback(connectionMangerCallback())
            }
            true -> {
                lollipopNetworkRequest()
            }
            else -> {
                context.registerReceiver(
                    networkReciter, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                )
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectionManger.unregisterNetworkCallback(connectionMangerCallback())
    }

    private fun lollipopNetworkRequest() {
        val requestBuilder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        connectionManger.registerNetworkCallback(
            requestBuilder.build(), connectionMangerCallback()
        )
    }

    private fun connectionMangerCallback(): ConnectivityManager.NetworkCallback {
        networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onLost(network: Network) {
                super.onLost(network)
                postValue(false)
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                postValue(true)
            }
        }
        return networkCallback
    }

    private val networkReciter = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateCondition()
        }
    }

    private fun updateCondition() {
        val activeNetwork: NetworkInfo? = connectionManger.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)
    }
}