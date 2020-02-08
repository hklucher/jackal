package com.brolo.jackal.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brolo.jackal.R
import com.brolo.jackal.model.LoginRequest
import com.brolo.jackal.model.LoginUser
import java.lang.ClassCastException
import kotlinx.android.synthetic.main.fragment_login_form.*

class LoginFormFragment : Fragment() {
    private lateinit var listener: LoginEventsListener

    interface LoginEventsListener {
        fun onLoginSubmit(loginRequest: LoginRequest)
    }

    companion object {
        fun newInstance(): LoginFormFragment {
            return LoginFormFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSubmitButton()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as LoginEventsListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LoginEventsListener")
        }
    }

    private fun getLoginRequestFromForm(): LoginRequest {
        val loginUser = LoginUser(
            login_field_email_input.text.toString(),
            login_field_password_input.text.toString()
        )

        return LoginRequest(loginUser)
    }

    private fun setupSubmitButton() {
        btn_submit.setOnClickListener {
            val loginUser = getLoginRequestFromForm()

            listener.onLoginSubmit(loginUser)
        }
    }
}
