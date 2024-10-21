package com.example.practice_iii

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_iii.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: ProductDatabaseHelper
    private lateinit var productAdapter: ProductAdapter
    private var productList = mutableListOf<Product>()
    private val selectedProducts = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = ProductDatabaseHelper(this)
        productList = dbHelper.getAllProducts()

        productAdapter = ProductAdapter(productList,
            onItemClick = { product, isChecked ->
                if (isChecked) {
                    selectedProducts.add(product)
                } else {
                    selectedProducts.remove(product)
                }
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = productAdapter
        }

        binding.goToSecondActivityButton.setOnClickListener {
            if (selectedProducts.size >= 3) {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putParcelableArrayListExtra("SELECTED_PRODUCTS", ArrayList(selectedProducts))
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select at least 3 products.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Clear the selectedProducts list and notify the adapter
        selectedProducts.clear()
        productAdapter.clearSelection()
    }
}
