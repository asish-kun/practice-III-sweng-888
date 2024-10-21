package com.example.practice_iii

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_iii.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    // Activity for displaying selected products and sending them via email

    private lateinit var binding: ActivitySecondBinding
    private lateinit var selectedProducts: MutableList<Product>
    private lateinit var productAdapter: SelectedProductAdapter
    private var emailIntentInitiated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve selected products passed from MainActivity
        selectedProducts = intent.getParcelableArrayListExtra("SELECTED_PRODUCTS") ?: mutableListOf()

        // Set up RecyclerView to display selected products
        productAdapter = SelectedProductAdapter(selectedProducts)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SecondActivity)
            adapter = productAdapter
        }

        // Set up email button to trigger sending selected products via email
        binding.emailButton.setOnClickListener {
            sendEmail()
        }
    }

    override fun onResume() {
        super.onResume()
        if (emailIntentInitiated) {
            // If email was successfully initiated, show a success toast and clear the list
            Toast.makeText(this, "Successfully Shared Selected Products", Toast.LENGTH_SHORT).show()
            selectedProducts.clear()
            productAdapter.notifyDataSetChanged()
            emailIntentInitiated = false
        }
    }

    private fun sendEmail() {
        // Prepare product details to be sent via email
        val productDetails = selectedProducts.joinToString(separator = "\n\n") { product ->
            """
        Name: ${product.name}
        Description: ${product.description}
        Seller: ${product.seller}
        Price: $${product.price}
        """.trimIndent()
        }

        // Create an intent to send the product details via email
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("sweng888mobileapps@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Selected Products Information")
            putExtra(Intent.EXTRA_TEXT, productDetails)
            setPackage("com.google.android.gm")
        }

        try {
            emailIntentInitiated = true
            startActivity(Intent.createChooser(emailIntent, "Send email using..."))
        } catch (e: Exception) {
            // Show an error message if no email client is installed
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}

