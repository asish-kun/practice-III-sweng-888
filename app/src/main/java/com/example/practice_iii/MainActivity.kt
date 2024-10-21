package com.example.practice_iii

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_iii.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Activity for displaying the list of products and handling user interactions

    // Initialize view binding, database helper, and product adapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: ProductDatabaseHelper
    private lateinit var productAdapter: ProductAdapter
    private var productList = mutableListOf<Product>()
    private val selectedProducts = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up database helper and load all products from the database
        dbHelper = ProductDatabaseHelper(this)
        productList = dbHelper.getAllProducts()

        // Set up RecyclerView with a custom adapter and handle product selection events
        productAdapter = ProductAdapter(productList,
            onItemClick = { product, isChecked ->
                if (isChecked) {
                    selectedProducts.add(product) // Add product to the selected list
                } else {
                    selectedProducts.remove(product) // Remove product from the selected list
                }
            }
        )

        // Assign adapter and layout manager to RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = productAdapter
        }

        // Handle the button click to navigate to the next activity
        binding.goToSecondActivityButton.setOnClickListener {
            if (selectedProducts.size >= 3) {
                // If the user selected at least 3 products, move to the next activity
                val intent = Intent(this, SecondActivity::class.java)
                intent.putParcelableArrayListExtra("SELECTED_PRODUCTS", ArrayList(selectedProducts))
                startActivity(intent)
            } else {
                // Otherwise, show a toast indicating the need to select at least 3 items
                Toast.makeText(this, "Please select at least 3 products.", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up filter buttons to change the displayed product categories
        setupFilterButtons()
    }

    private fun setupFilterButtons() {
        // Button click listeners to filter products by category
        binding.allButton.setOnClickListener {
            productList = dbHelper.getAllProducts()
            productAdapter.updateData(productList) // Update product list and refresh UI
        }

        binding.smartphonesButton.setOnClickListener {
            productList = dbHelper.getProductsByCategory("Smartphones")
            productAdapter.updateData(productList)
        }

        binding.laptopsButton.setOnClickListener {
            productList = dbHelper.getProductsByCategory("Laptops")
            productAdapter.updateData(productList)
        }
    }

    override fun onResume() {
        super.onResume()
        // When the activity is resumed, clear the selected products and reset UI
        selectedProducts.clear()
        productAdapter.clearSelection()
    }
}
