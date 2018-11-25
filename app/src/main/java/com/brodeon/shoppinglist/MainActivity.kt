package com.brodeon.shoppinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Enum określający czy FAB jest widoczny czy też nie
 */
enum class FabState {HIDDEN, VISIBLE}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.tag = FabState.VISIBLE
    }


}
