package com.example.myandroidxapplication.dashboard.rewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class RewardsFragment : Fragment() {


    companion object {
        fun newInstance() : RewardsFragment = RewardsFragment()
    }

    private lateinit var presenter: RewardsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = RewardsView(activity as AppCompatActivity)
        val provider = RewardsProvider(activity as AppCompatActivity)

        presenter = RewardsPresenter(view,provider)
        return view
    }







}