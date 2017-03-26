package io.zetaphase.hackerup

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button

/**
 * Created by Dave Ho on 3/26/2017.
 */

class PopAbout : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_about)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout(width.toInt(), height.toInt())

        val homeButton = findViewById(R.id.homeButton) as Button

        homeButton.setOnClickListener { finish() }

    }
}
