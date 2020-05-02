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
import com.brolo.jackal.model.User
import com.brolo.jackal.network.ApiDataService
import com.brolo.jackal.network.ApiInstance
import com.brolo.jackal.utils.AuthUtils
import java.lang.ClassCastException
import kotlinx.android.synthetic.main.fragment_login_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFormFragment : Fragment() {

    private lateinit var listener: LoginEventsListener

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

    private fun login(loginRequest: LoginRequest) {
        setSubmittingButtonUI(true)
//
        val service = ApiInstance.getInstance().create(ApiDataService::class.java)
        val response = service.login(loginRequest)

        response.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.headers().get("Authorization")?.let { authToken ->
                    val loggedInUser = response.body() as User
                    val currentActivity = activity

                    if (currentActivity != null) {
                        AuthUtils.saveJWT(currentActivity, authToken)
                        AuthUtils.saveUserId(currentActivity, loggedInUser.id)
                        ApiInstance.setAuthUtility(authToken)

                        setSubmittingButtonUI(false)

                        listener.onLoginSuccess(loggedInUser)
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                setSubmittingButtonUI(false)
            }
        })
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
            login(getLoginRequestFromForm())
        }
    }

    private fun setSubmittingButtonUI(isSubmitting: Boolean) {
        btn_submit.isEnabled = !isSubmitting

        if (isSubmitting) {
            btn_submit.text = getString(R.string.sign_in_requesting)
        } else {
            btn_submit.text = getString(R.string.sign_in)
        }
    }

    companion object {
        fun newInstance(): LoginFormFragment {
            return LoginFormFragment()
        }
    }

    interface LoginEventsListener {
        fun onLoginSuccess(user: User)
    }

}
