package com.example.workmanagerdemo1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.button).setOnClickListener{
            oneTimeWorkRequest()
        }
    }
    private fun oneTimeWorkRequest() {
        val uploadRequest = OneTimeWorkRequest
            .Builder(UploadWorker::class.java)
            .build()
        WorkManager
            .getInstance(applicationContext)
            .enqueue(uploadRequest)

        WorkManager
            .getInstance(applicationContext)
            .getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this, Observer {
                findViewById<TextView>(R.id.tv).text = it.state.name.toString()
            })

    }
}