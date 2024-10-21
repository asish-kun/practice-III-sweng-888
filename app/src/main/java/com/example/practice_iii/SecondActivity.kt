package com.example.practice_iii

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_iii.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var selectedProducts: MutableList<Product>
    private lateinit var productAdapter: SelectedProductAdapter
    private var emailIntentInitiated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedProducts = intent.getParcelableArrayListExtra("SELECTED_PRODUCTS") ?: mutableListOf()

        productAdapter = SelectedProductAdapter(selectedProducts)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SecondActivity)
            adapter = productAdapter
        }

        binding.emailButton.setOnClickListener {
            sendEmail()
        }
    }

    override fun onResume() {
        super.onResume()
        if (emailIntentInitiated) {
            Toast.makeText(this, "Successfully Shared Selected Products", Toast.LENGTH_SHORT).show()
            selectedProducts.clear()
            productAdapter.notifyDataSetChanged()
            emailIntentInitiated = false
        }
    }


    private fun sendEmail() {
        val productDetails = selectedProducts.joinToString(separator = "\n\n") { product ->
            """
        Name: ${product.name}
        Description: ${product.description}
        Seller: ${product.seller}
        Price: $${product.price}
        """.trimIndent()
        }

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("sweng888mobileapps@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Selected Products Information")
            putExtra(Intent.EXTRA_TEXT, productDetails)
        }

        try {
            emailIntentInitiated = true
            startActivity(Intent.createChooser(emailIntent, "Send email using..."))
            // Removed actions from here
        } catch (e: Exception) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}
