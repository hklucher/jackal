package com.brolo.jackal.utils

import android.app.Activity
import android.content.Context
import com.brolo.jackal.BuildConfig

class AuthUtils {
    companion object {
        private const val AUTH_FILE_NAME = "AUTH"
        private const val JWT_KEY = "JWT_KEY"
        private const val USER_ID_KEY = "USER_ID_KEY"

        fun saveJWT(activity: Activity, token: String) {
            val sharedPref = activity.getSharedPreferences(
                getAuthFileName(),
                Context.MODE_PRIVATE
            )

            with(sharedPref.edit()) {
                putString(JWT_KEY, token)
                commit()
            }
        }

        fun getJWT(activity: Activity): String? {
            val sharedPref = activity.getSharedPreferences(
                getAuthFileName(),
                Context.MODE_PRIVATE
            )

            return sharedPref.getString(JWT_KEY, null)
        }

        fun deleteJWT(activity: Activity) {
            val editor = activity.getSharedPreferences(
                getAuthFileName(),
                Context.MODE_PRIVATE
            ).edit()

            // NOTE: Need to synchronously delete to delete data before going back to login activity
            editor.remove(JWT_KEY).commit()
        }

        fun saveUserId(activity: Activity, id: Int) {
            val sharedPref = activity.getSharedPreferences(
                getAuthFileName(),
                Context.MODE_PRIVATE
            )

            with(sharedPref.edit()) {
                putInt(USER_ID_KEY, id)
                commit()
            }
        }

        fun getUserId(activity: Activity): Int {
            val sharedPref = activity.getSharedPreferences(
                getAuthFileName(),
                Context.MODE_PRIVATE
            )

            return sharedPref.getInt(USER_ID_KEY, 0)
        }

        fun deleteUserId(activity: Activity) {
            val editor = activity.getSharedPreferences(
                getAuthFileName(),
                Context.MODE_PRIVATE
            ).edit()

            // NOTE: Need to synchronously delete to delete data before going back to login activity
            editor.remove(USER_ID_KEY).commit()
        }

        private fun getAuthFileName() = "${BuildConfig.APPLICATION_ID}.$AUTH_FILE_NAME"
    }
}
