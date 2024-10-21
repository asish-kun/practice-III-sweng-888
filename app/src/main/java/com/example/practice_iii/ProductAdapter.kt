package com.example.practice_iii

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_iii.databinding.ItemProductBinding

class ProductAdapter(
    private val productList: List<Product>,
    private val onItemClick: (Product, Boolean) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val selectedProducts = mutableSetOf<Product>()

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.nameTextView.text = product.name
            binding.descriptionTextView.text = product.description
            binding.sellerTextView.text = product.seller
            binding.priceTextView.text = "$${product.price}"
            // Optionally load image into productImageView

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

//    method to clear selected products and notify the adapter
    fun clearSelection() {
        selectedProducts.clear()
        notifyDataSetChanged()
    }
}
