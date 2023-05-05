package com.example.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpecificCategory : AppCompatActivity() {

    public var arrayList: ArrayList<Product> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var topHeader: TextView
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.specific_category)
        supportActionBar?.setTitle("Rafael Cunha Soares Tavares")

        val category = intent.getStringExtra("category")

        topHeader = findViewById(R.id.totalItems)
        topHeader.text = "Category : "+ category

        for (i in 0 until MainActivity.arrayList.size) {
            if (MainActivity.arrayList[i].category == category) {
                arrayList.add(MainActivity.arrayList[i])
            }
        }

        recyclerView = findViewById(R.id.recyclerViewCategory)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this@SpecificCategory, 2)
        adapter = ProductAdapter(arrayList)
        recyclerView.adapter = adapter

        adapter.onItemClick = {
            val intent = Intent(this@SpecificCategory, ProductDetail::class.java)
            MainActivity.productObject = it
            intent.putExtra("product", it)
            startActivity(intent)
        }
    }
}