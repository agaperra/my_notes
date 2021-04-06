package com.agaperra.mynotes.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.agaperra.mynotes.R
import com.agaperra.mynotes.databinding.MainActivityBinding
import com.agaperra.mynotes.ui.main.MainFragment
import com.jaeger.library.StatusBarUtil


class MainActivity : AppCompatActivity() {


    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setTransparent(this)
            binding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        }

    }

    override fun onBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        if (navHostFragment.childFragmentManager.fragments[0]::class.java.toString() == MainFragment::class.java.toString()) {
            finish()
        } else {
            super.onBackPressed()
        }
        return
    }
}