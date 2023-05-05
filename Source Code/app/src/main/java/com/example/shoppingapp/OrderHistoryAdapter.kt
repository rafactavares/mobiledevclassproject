package com.example.shoppingapp

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class OrderHistoryAdapter(val productList: ArrayList<Product>) : RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder>() {

    var onItemClick : ((Product) -> Unit)? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.titleDetail)
        val category: TextView = itemView.findViewById(R.id.category)
        val price: TextView = itemView.findViewById(R.id.price)
        val image: ImageView = itemView.findViewById(R.id.image)
        val count: TextView = itemView.findViewById(R.id.count)
        val plus: ImageView = itemView.findViewById(R.id.plus)
        val minus: ImageView = itemView.findViewById(R.id.minus)
        val remove : TextView = itemView.findViewById(R.id.remove)
        val mainLayout : ConstraintLayout = itemView.findViewById(R.id.constraintLayout3)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_display, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList[position]
        holder.name.text = product.title
        holder.category.text = product.category
        holder.price.text = product.price.toString()+"$"

        val url = product.image
        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.image)

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(product)
        }
    }

}

