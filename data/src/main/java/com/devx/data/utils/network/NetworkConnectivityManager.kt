package com.devx.data.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NetworkConnectivityManager(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnected: Flow<Boolean> =
        callbackFlow {
            val callback =
                object : ConnectivityManager.NetworkCallback() {
                    override fun onUnavailable() {
                        trySend(element = false)
                        super.onUnavailable()
                    }

                    override fun onAvailable(network: Network) {
                        trySend(element = true)
                        super.onAvailable(network)
                    }

                    override fun onLost(network: Network) {
                        trySend(element = false)
                        super.onLost(network)
                    }
                }
            val request =
                NetworkRequest
                    .Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .build()

            connectivityManager.requestNetwork(request, callback, 2000)
            connectivityManager.registerNetworkCallback(request, callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
}
