package com.example.workmanagerdemo1

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(context: Context,parameters: WorkerParameters):Worker(context,parameters) {
    override fun doWork(): Result {
        try {
            for(i in 0..600){
                Log.i("MYTAG", "uploading $i")
            }
            return Result.success()
        }
        catch (e:Exception){
            return Result.failure()
        }
    }
}