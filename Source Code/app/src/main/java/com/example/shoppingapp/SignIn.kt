package com.example.shoppingapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.Serializable

class SignIn : AppCompatActivity(), View.OnClickListener {
    var userName: String? = null
    var password: String? = null
    var userNameField: EditText? = null
    var passwordField: EditText? = null
    var showPassword: ImageView? = null
    var signIn: Button? = null
    var signUp: TextView? = null
    var user: User? = null
    var currentActivity: Activity = this

    companion object {
        var userObject: Product? = null
    }

    /* access modifiers changed from: protected */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        supportActionBar!!.title = "Sign In"

        val button = findViewById<View>(R.id.signInNow) as Button
        signIn = button
        button.setOnClickListener(this)
        val textView = findViewById<View>(R.id.loginsignup) as TextView
        signUp = textView
        textView.setOnClickListener(this)
        val editText = findViewById<View>(R.id.userNameSignIn) as EditText
        userNameField = editText
        editText.setOnClickListener(this)
        userName = userNameField!!.text.toString()
        val editText2 = findViewById<View>(R.id.passwordSignIn) as EditText
        passwordField = editText2
        editText2.setOnClickListener(this)
        password = passwordField!!.text.toString()
        val showPasswordSignIn = findViewById<View>(R.id.showPasswordSignIn) as ImageView
        showPassword = showPasswordSignIn
        showPassword!!.setOnClickListener(this)


        //Code to hide keyboard when we touch anywhere on the screen
        val parentLayout = findViewById<View>(android.R.id.content)
        parentLayout.setOnTouchListener { v, event ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            parentLayout.requestFocus()
            false
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.signInNow) {
            if (userNameField!!.text.toString().isEmpty()) {
                userNameField!!.error = "userName is required"
                userNameField!!.requestFocus()
                return
            } else if (passwordField!!.text.toString().isEmpty()) {
                passwordField!!.error = "Password is required"
                passwordField!!.requestFocus()
                return
            } else if (!isEmailAndPasswordValid(userNameField!!.text.toString())) {
                userNameField!!.error = "InValid UserName or Password"
                userNameField!!.requestFocus()
                return
            } else if (isEmailAndPasswordValid(userNameField!!.text.toString())) {

                val dbHelper = DatabaseHelper(this@SignIn)
                dbHelper.updateLoggedInStatus("status", user!!.id, true)

                MainActivity.loggedInUserId = user!!.id
                MainActivity.isLoggedIn = true

                Toast.makeText(this, "Welcome ", Toast.LENGTH_SHORT).show()
                currentActivity.finish()

            }
        } else if (view.id == R.id.loginsignup) {
            startActivity(Intent(this, SignUp::class.java))
        } else if (view.id == R.id.showPasswordSignIn) {
            if (passwordField!!.transformationMethod != HideReturnsTransformationMethod.getInstance()) {
                passwordField!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
                showPassword!!.setImageResource(R.drawable.hide)
            } else {
                passwordField!!.transformationMethod = PasswordTransformationMethod.getInstance()
                showPassword!!.setImageResource(R.drawable.unhide)
            }
        }
    }

    fun isEmailAndPasswordValid(userName: String): Boolean {
        for (userMain in MainActivity.userArrayList) {
            if (userMain.username == userName) {
                this.user = userMain

                this.user!!.name = userMain.name
                this.user!!.username = userMain.username
                this.user!!.password = userMain.password
                this.user!!.email = userMain.email
                this.user!!.phone = userMain.phone
                this.user!!.address = userMain.address
                this.user!!.id = userMain.id

                return isPasswordValid(passwordField!!.text.toString())
            }
        }
        return false
    }

    fun isPasswordValid(password: String?): Boolean {
        return user!!.password == passwordField!!.text.toString()
    }
}