package com.example.workmanagerdemo1

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.Date

class UploadWorker(context: Context,parameters: WorkerParameters):Worker(context,parameters) {
    override fun doWork(): Result {
        try {

            val intValue = inputData.getInt("INT_KEY", 20)
            for(i in 0..intValue){
                Log.i("MYTAG", "uploading $i")
            }

            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currDate =  time.format(Date())
            val outputData = Data.Builder()
                .putString("key_string",currDate)
                .build()


            return Result.success(outputData)
        }
        catch (e:Exception){
            return Result.failure()
        }
    }
}