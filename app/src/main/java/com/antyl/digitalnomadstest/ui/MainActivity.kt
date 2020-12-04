package com.antyl.digitalnomadstest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.antyl.digitalnomadstest.R
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentList = supportFragmentManager.findFragmentByTag(ArticleListFragment::class.simpleName)
        if (fragmentList == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, ArticleListFragment(),
                        ArticleListFragment::class.simpleName)
                .commit()
        }
    }
}