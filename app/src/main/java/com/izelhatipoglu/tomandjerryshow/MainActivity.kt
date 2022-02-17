package com.izelhatipoglu.tomandjerryshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


class MainActivity : AppCompatActivity() {
    var score=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun imageClick(view: View) {}

    /*
    fun increaseScore(view: View){
        score=score +1
        scoreText.text = score.toString()


    }

     */
/*
    fun restartFragment(fragmentId: Int) {
        val currentFragment = this.supportFragmentManager.findFragmentById(fragmentId)!!

        this.supportFragmentManager.beginTransaction()
            .detach(currentFragment)
            .commit()
        this.supportFragmentManager.beginTransaction()
            .attach(currentFragment)
            .commit()
    }

 */



}