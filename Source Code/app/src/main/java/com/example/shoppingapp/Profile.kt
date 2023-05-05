package com.example.shoppingapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.shoppingapp.MainActivity.Companion.loggedInUserId

class Profile : AppCompatActivity() {
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        supportActionBar?.setTitle("Profile")

        var flag : Boolean = false

        for (user in MainActivity.userArrayList) {
            if (user.id == loggedInUserId) {
                this.user = user
                flag = true
                break
            }
        }
        if (!flag) {
            this.user = MainActivity.userArrayList[5]
            loggedInUserId = 5
        }

        val name: TextView = findViewById(R.id.nameProfile)
        name.text = "Name: "+ user?.name?.firstname.toString() + " " + user?.name?.lastname.toString()

        val email: TextView = findViewById(R.id.emailProfile)
        email.text = "Email: " + user?.email.toString()

        val userName: TextView = findViewById(R.id.userNameProfile)
        userName.text = "Username: "+ user?.username.toString()

        val phone: TextView = findViewById(R.id.phoneProfile)
        phone.text = "Phone no: "+user?.phone.toString()

        val address: TextView = findViewById(R.id.addressProfile)
        address.text = "Address: \nCity: "+ user?.address?.city.toString() + "\nStreet: " + user?.address?.street.toString() + ", St. no: " + user?.address?.number.toString() + "\nZip code:" + user?.address?.zipcode.toString() + "\nLat(" + user?.address?.geolocation?.lat.toString() + ") , Long(" + user?.address?.geolocation?.long.toString() + ")\n"

        val profileImage: ImageView = findViewById(R.id.profileImage)
        val url = "https://i.pravatar.cc/150?u=" + user?.id.toString()
        Glide.with(this)
            .load(url)
            .into(profileImage)

        val btnAboutApp: Button = findViewById(R.id.btnAboutApp)
        btnAboutApp.setOnClickListener { view ->
            Toast.makeText(this, "Copyright claim This app is developed by Rafael Cunha Soares Tavares", Toast.LENGTH_LONG).show()
        }
    }
}
