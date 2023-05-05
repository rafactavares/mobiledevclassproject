package com.example.shoppingapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Cart : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var topHeader: TextView
    private lateinit var totalItemsView: TextView
    private lateinit var adapter: ProductAdapter
    var cartArrayList: ArrayList<Product> = ArrayList()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart)
        supportActionBar?.setTitle("Rafael Cunha Soares Tavares")

        var totalPrice = 0.0;
        var totalItems = 0;
        for (i in 0 until MainActivity.arrayList.size) {
            if (MainActivity.arrayList[i].count > 0) {
                cartArrayList.add(MainActivity.arrayList[i])
                totalItems += MainActivity.arrayList[i].count
                totalPrice += MainActivity.arrayList[i].price * MainActivity.arrayList[i].count
            }
        }

        topHeader = findViewById(R.id.totalItems)
        topHeader.text = "Cart of user : " + MainActivity.loggedInUserId

        val formattedPrice = String.format("%.2f", totalPrice)

        totalItemsView = findViewById(R.id.totalItems)
        totalItemsView.text = "Total items : " + totalItems + "\nTotal price : " + formattedPrice


        val CHANNEL_ID = "my_channel_id"
        val notificationId = 0


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "My Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }


        val notificationContent = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Order Placed")
            .setContentText("Your order has been placed successfully.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()


        var placeOrderButton = findViewById<TextView>(R.id.placeOrder)
        placeOrderButton.setOnClickListener {

            totalItemsView.text = "Total items : " + 0 + "\nTotal price : " + 0.0
            adapter.notifyDataSetChanged()
            Toast.makeText(this@Cart, "Order placed successfully", Toast.LENGTH_SHORT).show()

            MainActivity.orderArrayList.add(cartArrayList)

            for (i in 0 until MainActivity.arrayList.size) {
                MainActivity.arrayList[i].count = 0
            }

            val context = this@Cart
            if (context is Activity) {
                context.finish()
                context.overridePendingTransition(0, 0)
            }
            val intent = Intent(context, Cart::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.notify(notificationId, notificationContent)
        }

        recyclerView = findViewById(R.id.cartViewCategory)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this@Cart, 2)
        adapter = ProductAdapter(cartArrayList)
        recyclerView.adapter = adapter

        adapter.onItemClick = {
            val intent = Intent(this@Cart, ProductDetail::class.java)
            MainActivity.productObject = it
            intent.putExtra("product", it)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the RecyclerView with updated data (in my case, remaining seats on the homepage will be updated)
        adapter?.notifyDataSetChanged()
    }
}