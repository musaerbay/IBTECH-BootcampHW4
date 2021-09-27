package com.mr.bootcampweek4.ui


import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.navigation.fragment.findNavController
import com.mr.bootcampweek4.R
import com.mr.bootcampweek4.base.BaseFragment
import com.mr.bootcampweek4.response.LoginResponse
import com.mr.bootcampweek4.service.BaseCallBack
import com.mr.bootcampweek4.service.ServiceConnector
import com.mr.bootcampweek4.utils.USER_TOKEN
import com.mr.bootcampweek4.utils.getString
import com.mr.bootcampweek4.utils.saveDataAsString
import com.mr.bootcampweek4.utils.toast
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment() {

    override fun getLayoutID() = R.layout.fragment_login


    override fun prepareView() {
        button_login?.setOnClickListener {
            hitLoginEndpoint()
        }
    }

    private fun hitLoginEndpoint() {

        val email = activity?.email?.getString()
        val password = activity?.password?.getString()
        val name = name.getString()
        //       val age = ageET.getString()

        if (allFieldsAreValid(email!!, password!!)) {  //, name, age
            //isteği at

            val params = mutableMapOf<String, String>().apply {
                put("name", name)
                put("email", email)
                put("password", password)
                //             put("age", age)
            }

            ServiceConnector.restInterface.login(params)
                .enqueue(object : BaseCallBack<LoginResponse>() {
                    override fun onSuccess(LoginResponse: LoginResponse) {
                        super.onSuccess(LoginResponse)

                        Log.e("crated user token ", LoginResponse.token)
                        saveDataAsString(USER_TOKEN, LoginResponse.token)
                        findNavController().navigate(R.id.action_LoginFragment_to_TodoFragment)
                    }

                    override fun onFailure() {
                        super.onFailure()
                        toast("Bir hata oluştu!")
                    }
                })
        } else {
            toast("Lütfen alanları kontrol ediniz.")
        }

    }

    private fun allFieldsAreValid(
        email: String,
        password: String//name: String, age: String
    ): Boolean {
        var allFieldsAreValid = true

        // for values rules control
        if (email.isEmpty() || !isValidEmail(email)) {
            allFieldsAreValid = false
        }
        if (email.isEmpty() || email.length < 2) allFieldsAreValid = false
        if (password.isEmpty() || password.length < 7) allFieldsAreValid = false

        return allFieldsAreValid
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}