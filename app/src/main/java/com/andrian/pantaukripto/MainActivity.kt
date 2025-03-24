package com.andrian.pantaukripto

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.andrian.pantaukripto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.favoriteFragment, R.id.aboutFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    setStatusBarColor(R.color.black, lightIcons = false)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.favoriteFragment -> {
                    val uri = Uri.parse("pantaukripto://favorite")
                    navController.navigate(uri)
                    true
                }

                R.id.aboutFragment -> {
                    navController.navigate(R.id.aboutFragment)
                    true
                }

                else -> false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.homeFragment
    }

    private fun setStatusBarColor(colorRes: Int, lightIcons: Boolean) {
        window.statusBarColor = ContextCompat.getColor(this, colorRes)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
            lightIcons
    }
}