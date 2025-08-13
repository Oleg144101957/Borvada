package com.bo.va.da76.model.repo

interface DataStoreRepository {

    fun saveUrl(newGoalToSave: String)
    fun getUrl(): String
    fun saveAdb(adb: Boolean)
    fun getAdb(): Boolean?
    fun setSpeed(newSpeed: Float)
    fun getSpeed(): Float

}