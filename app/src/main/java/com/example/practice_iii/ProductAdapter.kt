package com.example.practice_iii

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_iii.databinding.ItemProductBinding

class ProductAdapter(
    private var productList: List<Product>,
    private val onItemClick: (Product, Boolean) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val selectedProducts = mutableSetOf<Product>()

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            // Bind product details to the UI
            binding.nameTextView.text = product.name
            binding.descriptionTextView.text = product.description
            binding.sellerTextView.text = product.seller
            binding.priceTextView.text = "$${product.price}"

            // Setup checkbox and handle product selection
            binding.checkbox.setOnCheckedChangeListener(null)
            binding.checkbox.isChecked = selectedProducts.contains(product)

            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onItemClick(product, isChecked)
                if (isChecked) {
                    selectedProducts.add(product)
                } else {
                    selectedProducts.remove(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(productList[position])

    override fun getItemCount() = productList.size

    // Update product list and notify the adapter
    fun updateData(newProductList: List<Product>) {
        productList = newProductList
        notifyDataSetChanged()
    }

    fun clearSelection() {
        // Clear selected products and update the UI
        selectedProducts.clear()
        notifyDataSetChanged()
    }
}

