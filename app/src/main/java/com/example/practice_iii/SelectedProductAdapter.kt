package com.example.practice_iii

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_iii.databinding.ItemProductBinding

class SelectedProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<SelectedProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            // Bind selected product details to the UI
            binding.nameTextView.text = product.name
            binding.descriptionTextView.text = product.description
            binding.sellerTextView.text = product.seller
            binding.priceTextView.text = "$${product.price}"

            // Disable the checkbox for selected products since they are not selectable in this screen
            binding.checkbox.isChecked = false
            binding.checkbox.isEnabled = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(productList[position])

    override fun getItemCount() = productList.size
}

