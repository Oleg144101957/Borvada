package com.bo.va.da76.model.repo.impl

import android.content.Context
import androidx.core.content.edit
import com.bo.va.da76.model.repo.DataStoreRepository
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val context: Context) :
    DataStoreRepository {

    private val pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun getUrl(): String {
        return pref.getString(
            GOAL,
            EMPTY
        ) ?: EMPTY
    }

    override fun saveUrl(newGoalToSave: String) {
        pref.edit { putString(GOAL, newGoalToSave) }
    }

    override fun saveAdb(adb: Boolean) {
        pref.edit { putBoolean(ADB, adb) }
    }

    override fun getAdb(): Boolean? {
        return if (pref.contains(ADB)) {
            pref.getBoolean(ADB, false)
        } else {
            null
        }
    }

    override fun setSpeed(newSpeed: Float) {
        pref.edit { putFloat(SPEED, newSpeed) }
    }


    override fun getSpeed(): Float {
        return pref.getFloat(SPEED, 5f)
    }

    companion object {
        const val PREFS_NAME = "prefs"
        private const val GOAL = "GOAL"
        private const val EMPTY = "EMPTY"
        private const val ADB = "ADB"
        private const val SPEED = "speed"
    }
}


