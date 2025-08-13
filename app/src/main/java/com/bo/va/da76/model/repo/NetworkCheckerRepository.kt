package com.bo.va.da76.model.repo

import android.content.Context

interface NetworkCheckerRepository {
    fun isConnectionAvailable(): Boolean
    fun isAdbEnabled(context: Context): Boolean
}