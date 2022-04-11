package com.agaperra.mynotes

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.agaperra.mynotes.databinding.MainActivityBinding
import com.agaperra.mynotes.presentation.ui.main.MainFragment
import com.jaeger.library.StatusBarUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private var _binding: MainActivityBinding? = null
//    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setTransparent(this)
            //binding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }

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
