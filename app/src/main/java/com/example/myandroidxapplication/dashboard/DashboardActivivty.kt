package com.example.myandroidxapplication.dashboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DashboardActivivty : AppCompatActivity(), View.OnClickListener {


    lateinit var view : DashboardView
    lateinit var presenter: DashboardPresenter
    lateinit var provider: DashboardProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = DashboardView(this)
        provider = DashboardProvider(this)
        setContentView(view)
        view.initialized()

        presenter = DashboardPresenter(view,provider)
    }



    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}