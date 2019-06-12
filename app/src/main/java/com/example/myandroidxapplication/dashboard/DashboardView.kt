package com.example.myandroidxapplication.dashboard

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myandroidxapplication.R
import com.example.myandroidxapplication.dashboard.history.HistoryFragment
import com.example.myandroidxapplication.dashboard.profile.ProfileFragment
import com.example.myandroidxapplication.dashboard.rewards.RewardsFragment
import com.example.myandroidxapplication.dashboard.task.TaskFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardView (val activity: AppCompatActivity) : FrameLayout(activity), DashboardContract.View {

    private var bottomNav : BottomNavigationView? =null


    private val bottomNavClickListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.nav_task -> {
                val taskFragment = TaskFragment.newInstance()
                openFragment(taskFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_rewards -> {
                val rewardsFragment = RewardsFragment.newInstance()
                openFragment(rewardsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                val profileFragment = ProfileFragment.newInstance()
                openFragment(profileFragment)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun initialized() {
        val view = View.inflate(context, R.layout.activity_dashboard, this)

        bottomNav = view?.findViewById<BottomNavigationView>(R.id.navigationView)
        bottomNav?.setOnNavigationItemSelectedListener(bottomNavClickListener)
        bottomNav?.selectedItemId = R.id.nav_task
    }

    private fun openFragment(fragment : Fragment){
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }




}