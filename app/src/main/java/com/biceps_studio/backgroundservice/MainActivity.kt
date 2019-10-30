package com.biceps_studio.backgroundservice

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxView.clicks(tvButton).subscribe {
            TimerDown(this).execute()
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class TimerDown(private val context: Context) : AsyncTask<Int, Int, Int>() {

        private val max = 10
        private var time = 0
        private lateinit var progressDialog: ProgressDialog

        override fun onPreExecute() {
            super.onPreExecute()

            progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Please wait...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)

            if (result == max - 1){
                progressDialog.dismiss()
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)

            val update: Int = (max - 1) - values[0]!!

            progressDialog.setMessage("$update seconds remaining")
        }

        override fun doInBackground(vararg p0: Int?): Int? {
            while (time < max){
                publishProgress(time)

                time++

                Thread.sleep(1000)
            }

            return time
        }
    }
}