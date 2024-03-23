package com.example.workmanagerdemo1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    companion object{
        const val KEY_VALUE = "key_value"
    }
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


        val newData = Data.Builder()
            .putInt("INT_KEY", 200)
            .build()



        val constraints = Constraints
            .Builder()
            .setRequiresCharging(true)
            .build()


        val uploadRequest = OneTimeWorkRequest
            .Builder(UploadWorker::class.java)
            .setInputData(newData)
            .setConstraints(constraints)
            .build()
        WorkManager
            .getInstance(applicationContext)
            .enqueue(uploadRequest)

        WorkManager
            .getInstance(applicationContext)
            .getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this, Observer {
                findViewById<TextView>(R.id.tv).text = it.state.name.toString()
                if (it.state.isFinished){
                    val data = it.outputData
                    val message = data.getString("key_string")
                    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
                }
            })

    }
}