package com.brolo.jackal.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brolo.jackal.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        setupSubmitButton()
    }

    private fun setupSubmitButton() {
        btn_submit.setOnClickListener {
            btn_submit.text = getString(R.string.submitting)
            btn_submit.isEnabled = false
            btn_submit.setBackgroundColor(resources.getColor(R.color.colorGreyBackground))

            val email = login_field_email_input.text.toString()
            val password = login_field_password_input.text.toString()
        }
    }
}