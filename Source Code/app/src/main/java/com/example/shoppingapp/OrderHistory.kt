package com.example.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderHistory : AppCompatActivity() {

    var orderText: TextView? = null
    var searchBar: EditText? = null
    var searchButton: Button? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_history)
        supportActionBar?.setTitle("Rafael Cunha Soares Tavares")

        orderText = findViewById(R.id.orderText)
        orderText?.text = "Total orders : " + MainActivity.orderArrayList.size + "\n\n"

        searchBar = findViewById(R.id.searchBar) as EditText

        searchButton = findViewById(R.id.btnSearchOrder) as Button
        searchButton!!.setOnClickListener {
            if (MainActivity.orderArrayList.size == 0) {
                Toast.makeText(
                    this@OrderHistory,
                    "You don't have any orders yet",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (searchBar!!.text.toString().isEmpty()) {
                Toast.makeText(
                    this@OrderHistory,
                    "Please enter a valid order id",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (searchBar!!.text.toString()
                    .toInt() > MainActivity.orderArrayList.size || searchBar!!.text.toString()
                    .toInt() <= 0
            ) {
                Toast.makeText(
                    this@OrderHistory,
                    "Please enter a valid order id between 1 and " + MainActivity.orderArrayList.size,
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                orderText?.visibility = TextView.GONE
                searchButton?.visibility = Button.GONE
                searchBar?.visibility = EditText.GONE

                System.out.println("Showing main size" +MainActivity.orderArrayList.size)
                System.out.println("Showing inner size" +MainActivity.orderArrayList[0].size)


                recyclerView = findViewById(R.id.cartViewCategory)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = GridLayoutManager(this@OrderHistory,2)
                adapter = OrderHistoryAdapter(
                    MainActivity.orderArrayList[searchBar!!.text.toString().toInt() - 1]
                )
                recyclerView.adapter = adapter

                adapter.onItemClick = {
                    val intent = Intent(this@OrderHistory, ProductDetail::class.java)
                    MainActivity.productObject = it
                    intent.putExtra("product", it)
                    startActivity(intent)
                }
            }
        }
    }
}