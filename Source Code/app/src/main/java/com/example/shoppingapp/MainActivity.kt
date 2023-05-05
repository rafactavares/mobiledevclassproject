package com.example.shoppingapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    private lateinit var electronics: Button
    private lateinit var jewelery: Button
    private lateinit var mens: Button
    private lateinit var womens: Button
    var currentActivity: Activity = this

    companion object {
        var productObject: Product? = null

        // Declare arrayList as public
        @JvmStatic
        public var arrayList: ArrayList<Product> = ArrayList()

        @JvmStatic
        public var userArrayList: ArrayList<User> = ArrayList()

        @JvmStatic
        public var orderArrayList: ArrayList<ArrayList<Product>> = ArrayList()

        @JvmStatic
        public var loggedInUserId : Int = -1

        @JvmStatic
        public var isLoggedIn : Boolean = false
    }

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setTitle("Rafael Cunha Soares Tavares")


        //Creating database if not already exists
        val dbHelper1 = DatabaseHelper(this@MainActivity)
        val db1 = dbHelper1.readableDatabase

        val cursor1 = db1.rawQuery("SELECT COUNT(*) FROM mytable", null)
        cursor1.moveToFirst()
        val count = cursor1.getInt(0)
        cursor1.close()
        val isTableEmpty = count == 0
        db1.close()

        if (isTableEmpty) {
            dbHelper1.insertLoggedInStatus(false, -1)
            Intent(this, SignIn::class.java).also {
                Toast.makeText(this@MainActivity, "Please Sign In First", Toast.LENGTH_LONG).show()
                startActivity(it)
            }
        }


        val dbHelper = DatabaseHelper(this@MainActivity)

        val loggedInStatus = dbHelper.getLoggedInStatus("status")
        if (loggedInStatus != null) {
            loggedInUserId = loggedInStatus.first
            isLoggedIn = loggedInStatus.second

            if (!isLoggedIn && loggedInUserId == -1) {
                Intent(this, SignIn::class.java).also {
                    Toast.makeText(this@MainActivity, "Please Sign In First", Toast.LENGTH_LONG).show()
                    startActivity(it)
                }
            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://fakestoreapi.com/products")
                    .build()

                val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
                val json = response.body?.string()

                val gson = Gson()
                val productListType = object : TypeToken<List<Product>>() {}.type
                val productList: List<Product> = gson.fromJson(json, productListType)

                arrayList = ArrayList(productList)

                recyclerView = findViewById(R.id.recyclerView)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = ProductAdapter(arrayList)
                recyclerView.adapter = adapter

                adapter.onItemClick = {
                    val intent = Intent(this@MainActivity, ProductDetail::class.java)
                    productObject = it
                    intent.putExtra("product", it)
                    startActivity(intent)
                }

                electronics = findViewById(R.id.electronics)
                electronics.setOnClickListener {
                    val intent = Intent(this@MainActivity, SpecificCategory::class.java)
                    intent.putExtra("category", "electronics")
                    startActivity(intent)
                }

                jewelery = findViewById(R.id.jewelery)
                jewelery.setOnClickListener {
                    val intent = Intent(this@MainActivity, SpecificCategory::class.java)
                    intent.putExtra("category", "jewelery")
                    startActivity(intent)
                }

                mens = findViewById(R.id.mens)
                mens.setOnClickListener {
                    val intent = Intent(this@MainActivity, SpecificCategory::class.java)
                    intent.putExtra("category", "men's clothing")
                    startActivity(intent)
                }

                womens = findViewById(R.id.womens)
                womens.setOnClickListener {
                    val intent = Intent(this@MainActivity, SpecificCategory::class.java)
                    intent.putExtra("category", "women's clothing")
                    startActivity(intent)
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: Internet not connected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://fakestoreapi.com/users")
                    .build()

                val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
                val json = response.body?.string()

                val gson = Gson()
                val productListType =
                    object : com.google.gson.reflect.TypeToken<List<User>>() {}.type
                val userList: List<User> = gson.fromJson(json, productListType)

                userArrayList = ArrayList(userList)
                userArrayList.add(
                    User(
                        userArrayList[0].id,
                        userArrayList[0].email,
                        "admin",
                        "admin",
                        userArrayList[0].name,
                        userArrayList[0].address,
                        userArrayList[0].phone
                    )
                )

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Internet Not Connected!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        try {
            super.onCreateOptionsMenu(menu)
            menuInflater.inflate(R.menu.main, menu)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var selectedOption = "";
        when (item?.itemId) {
            R.id.option_view_profile -> selectedOption = "profile"
            R.id.option_view_cart -> selectedOption = "cart"
            R.id.option_view_history -> selectedOption = "history"
            R.id.option_logout -> selectedOption = "logout"
        }

        if (selectedOption.equals("profile")) {
            val intent = Intent(this@MainActivity, Profile::class.java)
            startActivity(intent)
        } else if (selectedOption.equals("cart")) {
            val intent = Intent(this@MainActivity, Cart::class.java)
            startActivity(intent)
        } else if (selectedOption.equals("history")) {
            val intent = Intent(this@MainActivity, OrderHistory::class.java)
            startActivity(intent)
        } else if (selectedOption.equals("logout")) {
            val dbHelper = DatabaseHelper(this@MainActivity)
            dbHelper.updateLoggedInStatus("status",-1, false)
            Intent(this, SignIn::class.java).also {
                Toast.makeText(this@MainActivity, "Please Sign In To Get Continue", Toast.LENGTH_LONG).show()
                startActivity(it)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
