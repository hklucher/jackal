package com.brolo.jackal.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brolo.jackal.R
import kotlinx.android.synthetic.main.fragment_login_form.*
import kotlinx.android.synthetic.main.fragment_registration_form.*
import java.lang.ClassCastException

class RegistrationFormFragment : Fragment() {

    private lateinit var listener: RegistrationEventsListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtons()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as RegistrationEventsListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement RegistrationEventsListener")
        }
    }

    private fun initButtons() {
        btn_sign_in.setOnClickListener {
            listener.onSignInClick()
        }
    }

    companion object {
        fun createInstance(): RegistrationFormFragment {
            return RegistrationFormFragment()
        }
    }

    interface RegistrationEventsListener {
        fun onSignInClick()
    }

}
