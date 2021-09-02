package com.example.weather.util

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkManager(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    val  TAG="main"

    override fun doWork(): Result {
        Log.i(TAG, "doWork: ")
        return Result.success()
    }
}