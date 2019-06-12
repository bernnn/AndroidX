package com.example.myandroidxapplication.dashboard.rewards

import android.app.ProgressDialog
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myandroidxapplication.R

class RewardsView (val activity: AppCompatActivity) : FrameLayout(activity), RewardsContract.View {

    private lateinit var progressDialog: ProgressDialog

    init {
        initialize()
    }

    override fun initialize() {
        val view = View.inflate(context, R.layout.fragment_reward, this)


        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Processing. . .")
        progressDialog.setCancelable(false)
    }

    override fun showProgress() {
        if( ! activity.isFinishing || ! activity.isDestroyed){
            activity.runOnUiThread{
                progressDialog.show()
            }
        }
    }

    override fun hideProgress() {
        if( ! activity.isFinishing || ! activity.isDestroyed){
            activity.runOnUiThread{
                progressDialog.dismiss()
            }
        }
    }


}