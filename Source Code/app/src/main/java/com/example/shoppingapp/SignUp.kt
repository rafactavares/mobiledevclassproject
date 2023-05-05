package com.example.shoppingapp

import android.app.Activity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class SignUp : AppCompatActivity(), View.OnClickListener {
    var email: String? = null
    var userName: String? = null
    var password: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var emailField: EditText? = null
    var userNameField: EditText? = null
    var passwordField: EditText? = null
    var firstNameField: EditText? = null
    var lastNameField: EditText? = null
    var showPassword: ImageView? = null
    var signIn: TextView? = null
    var signUp: Button? = null
    var currentActivity: Activity = this
    var user: User? = null

    /* access modifiers changed from: protected */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        supportActionBar!!.title = "Sign Up"

        val textView = findViewById<View>(R.id.loginsignup) as TextView
        signIn = textView
        textView.setOnClickListener(this)

        val button = findViewById<View>(R.id.signUpNow) as Button
        signUp = button
        button.setOnClickListener(this)

        val editText = findViewById<View>(R.id.emailSignUp) as EditText
        emailField = editText


        val editText2 = findViewById<View>(R.id.passwordSignUp) as EditText
        passwordField = editText2

        val editText3 = findViewById<View>(R.id.userNameSignUp) as EditText
        userNameField = editText3

        val editText4 = findViewById<View>(R.id.firstNameSignUp) as EditText
        firstNameField = editText4

        val editText5 = findViewById<View>(R.id.lastNameSignUp) as EditText
        lastNameField = editText5

        val showPasswordSignUp = findViewById<View>(R.id.showPasswordSignUp) as ImageView
        showPassword = showPasswordSignUp
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
        if (view.id == R.id.signUpNow) {
            lastName = lastNameField!!.text.toString()
            firstName = firstNameField!!.text.toString()
            userName = userNameField!!.text.toString()
            password = passwordField!!.text.toString()
            email = emailField!!.text.toString()

            if (firstNameField!!.text.toString().isEmpty()) {
                firstNameField!!.error = "First Name is required"
                firstNameField!!.requestFocus()
                return
            } else if (lastNameField!!.text.toString().isEmpty()) {
                lastNameField!!.error = "Last Name is required"
                lastNameField!!.requestFocus()
                return
            } else if (emailField!!.text.toString().isEmpty()) {
                emailField!!.error = "Email is required"
                emailField!!.requestFocus()
                return
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailField!!.text.toString()).matches()) {
                emailField!!.error = "Please enter a valid email"
                emailField!!.requestFocus()
                return
            } else if (passwordField!!.text.toString().isEmpty()) {
                passwordField!!.error = "Password is required"
                passwordField!!.requestFocus()
                return
            }else {
                lastName = lastNameField!!.text.toString()
                firstName = firstNameField!!.text.toString()
                userName = userNameField!!.text.toString()
                password = passwordField!!.text.toString()
                email = emailField!!.text.toString()

                MainActivity.loggedInUserId = MainActivity.userArrayList.size
                user = User(MainActivity.userArrayList.size,email!!, userName!!, password!!, Name(firstName!!, lastName!!), MainActivity.userArrayList[0].address, MainActivity.userArrayList[0].phone)
                MainActivity.userArrayList.add(user!!)

                GlobalScope.launch(Dispatchers.Main) {
                    try {

                        //Storing in the API
                        val client = OkHttpClient()
                        val gson = Gson()
                        val json = gson.toJson(user)
                        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

                        val request = Request.Builder()
                            .url("https://fakestoreapi.com/users")
                            .post(requestBody)
                            .build()

                        val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }

                        if (response.isSuccessful) {
                            val jsonResponse = response.body?.string()
                            val newUser: User = gson.fromJson(jsonResponse, User::class.java)
                            // Do something with the new user object
                        } else {
                            // Handle error
                        }

                    } catch (e: Exception) {
                        Toast.makeText(
                            this@SignUp,
                            "Error: Internet not connected",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show()
                currentActivity.finish()
            }
        } else if (view.id == R.id.loginsignup) {
            currentActivity.finish()
        } else if (view.id == R.id.showPasswordSignUp) {
            if (passwordField!!.transformationMethod != HideReturnsTransformationMethod.getInstance()) {
                passwordField!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
                showPassword!!.setImageResource(R.drawable.hide)
            } else {
                passwordField!!.transformationMethod = PasswordTransformationMethod.getInstance()
                showPassword!!.setImageResource(R.drawable.unhide)
            }
        }
    }
}