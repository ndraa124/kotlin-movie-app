package com.id22.movieapp.presentation.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var bind: VB

    protected abstract fun createBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = createBinding()
        setContentView(bind.root)
    }

    protected inline fun <reified ClassActivity> intents(context: Context) {
        context.startActivity(Intent(context, ClassActivity::class.java))
    }
}
