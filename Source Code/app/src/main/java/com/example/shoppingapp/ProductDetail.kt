package com.example.shoppingapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class ProductDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_detail)
        supportActionBar?.setTitle("Product Detail")

        val product = intent.getParcelableExtra<Product>("product")
        if (product != null) {
            val productImage: ImageView = findViewById(R.id.productImageDetail)
            val productTitle: TextView = findViewById(R.id.titleDetail)
            val productPrice: TextView = findViewById(R.id.priceDetail)
            val productCategory: TextView = findViewById(R.id.categoryDetail)
            val productDescription: TextView = findViewById(R.id.description)
            val btnAddToCart: TextView = findViewById(R.id.btnAddToCart)


            val url = product.image
            Glide.with(this)
                .load(url)
                .into(productImage)

            productTitle.text = product.title
            productPrice.text = "$" + product.price
            productCategory.text = product.category
            productDescription.text = product.description

            btnAddToCart.setOnClickListener { view ->
                for (item in MainActivity.arrayList) {
                    if (item.id == product.id) {
                        item.count++
                        Toast.makeText(this, "Added to cart successfully", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
            }
        }
    }
}