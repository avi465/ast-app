package com.ast.app.network.utils

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<NetworkStatus>
}