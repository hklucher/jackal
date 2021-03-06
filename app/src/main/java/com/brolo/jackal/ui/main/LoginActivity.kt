package com.brolo.jackal.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.brolo.jackal.R
import com.brolo.jackal.model.LoginRequest
import com.brolo.jackal.model.User
import com.brolo.jackal.network.ApiDataService
import com.brolo.jackal.network.ApiInstance
import com.brolo.jackal.utils.AuthUtils
import com.brolo.jackal.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(),
    LoginFormFragment.LoginEventsListener,
    RegistrationFormFragment.RegistrationEventsListener {

    lateinit var userViewModel: UsersViewModel

    companion object {
        val TAG = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        handleAuthCheck()
        initViewModels()
    }

    override fun onLoginSuccess(user: User) {
        insertCurrentUser(user)
        proceedToMainApp()
    }

    override fun onSignUpClick() {
        val registrationFragment = RegistrationFormFragment.createInstance()

        val transition = supportFragmentManager.beginTransaction().replace(
            R.id.login_fragment_container,
            registrationFragment
        )

        transition.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right,
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )

        transition.commit()
    }

    override fun onSignInClick() {
        val loginFragment = LoginFormFragment.newInstance()

        val transition = supportFragmentManager.beginTransaction().replace(
            R.id.login_fragment_container,
            loginFragment
        )

        transition.commit()
    }

    private fun initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
    }

    private fun insertCurrentUser(user: User) {
        GlobalScope.launch {
            userViewModel.insertCurrentUser(user)
        }
    }

    private fun handleAuthCheck() {
        val existingToken = AuthUtils.getJWT(this)
        val userId = AuthUtils.getUserId(this)

        if (existingToken != null && userId != 0) {
            // Set the auth header on all requests on our api singleton
            ApiInstance.setAuthUtility(existingToken)

            fetchUsersProfile(userId)
        } else {
            auth_check_progress.visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .add(R.id.login_fragment_container, LoginFormFragment.newInstance())
                .commit()
        }
    }

    private fun fetchUsersProfile(userId: Int) {
        val service = ApiInstance.getInstance().create(ApiDataService::class.java)
        val response = service.getUser(userId)

        response.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                // TODO: Insert current user from response here
                proceedToMainApp()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {}
        })
    }

    private fun proceedToMainApp() {
        val intent = Intent(this, AuthenticatedActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

}
