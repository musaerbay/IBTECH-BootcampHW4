package com.mr.bootcampweek4.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mr.bootcampweek4.R
import com.mr.bootcampweek4.model.User
import com.mr.bootcampweek4.service.BaseCallBack
import com.mr.bootcampweek4.service.ServiceConnector
import com.mr.bootcampweek4.utils.USER_TOKEN
import com.mr.bootcampweek4.utils.gone
import com.mr.bootcampweek4.utils.toast
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_login.*


class SplashActivity : AppCompatActivity() {

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // control start splashactivity
        val intent = Intent(this@SplashActivity, HomeActivity::class.java).apply {
            putExtra("isLoggedIn", isLoggedIn())
        }

        if (isLoggedIn()) {

            //HOMEPAGE SHOULD BE SEEN

            User.getCurrentInstance().token = token
            ServiceConnector.restInterface.getMe().enqueue(object : BaseCallBack<User>() {
                override fun onSuccess(data: User) {
                    super.onSuccess(data)
                    splash_progressbar.gone()
                    User.getCurrentInstance().setUser(data)
                    toast("User is logged in successfully")

                    startActivity(intent)
                    finish()
                }

                override fun onFailure() {
                    super.onFailure()
                    Log.e("something went", "wrong")
                    toast("something wong")
                }
            })
        } else {

            //Login SCREEN SHOULD BE SEEN
            splash_progressbar.gone()
            startActivity(intent)
            finish()

        }
    }

    private fun isLoggedIn(): Boolean {
        val token = getToken()
        return token.isNotEmpty()   // token is empty
    }

    private fun getToken(): String {
        val shared = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        token = shared.getString(USER_TOKEN, "")!!

        return token!!
    }
}