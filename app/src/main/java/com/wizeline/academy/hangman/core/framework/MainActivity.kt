package com.wizeline.academy.hangman.core.framework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wizeline.academy.hangman.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}