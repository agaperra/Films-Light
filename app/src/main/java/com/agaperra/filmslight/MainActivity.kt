package com.agaperra.filmslight

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

//@SuppressLint("InflateParams")
//override fun onBackPressed() {
//
//    if (supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount!! > 0)
//    {
//        if (Constants.backPress + Constants.exitPress > System.currentTimeMillis()) {
//            finish()
//        } else {
//            val snackbar =
//                Snackbar.make(binding.root, getString(R.string.try_exit), Snackbar.LENGTH_LONG)
//            val customSnackView: View =
//                layoutInflater.inflate(R.layout.rounded_card, null)
//            snackbar.view.setBackgroundColor(Color.TRANSPARENT)
//            val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
//
//            snackbarLayout.setPadding(20, 20, 20, 20)
//            snackbarLayout.addView(customSnackView, 0)
//            snackbar.show()
//        }
//        Constants.backPress = System.currentTimeMillis()
//    }
//    else {
//        super.onBackPressed()
//    }
//
//}