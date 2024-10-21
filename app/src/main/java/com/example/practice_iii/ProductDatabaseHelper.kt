package com.example.practice_iii

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "product_database.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_PRODUCTS = "products"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_SELLER = "seller"
        const val COLUMN_PRICE = "price"
        const val COLUMN_PICTURE = "picture"
        const val COLUMN_CATEGORY = "category"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create table for storing products in the SQLite database
        val createTable = """
            CREATE TABLE $TABLE_PRODUCTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_SELLER TEXT,
                $COLUMN_PRICE REAL,
                $COLUMN_PICTURE TEXT,
                $COLUMN_CATEGORY TEXT
            )
        """.trimIndent()
        db?.execSQL(createTable)
        insertInitialProducts(db) // Insert some initial products into the database
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Placeholder for database upgrade logic
    }

    private fun insertInitialProducts(db: SQLiteDatabase?) {
        // Insert a predefined set of products into the database (smartphones, laptops, accessories, etc.)
        val products = listOf(
            Product(name = "Apple iPhone 14 Pro", description = "6.1-inch display, A16 Bionic chip...", seller = "Apple", price = 999.0, picture = "iphone14pro.png", category = "Smartphones"),
            Product(name = "Dell XPS 13", description = "13.4-inch laptop with Intel i7, 16GB RAM...", seller = "Dell", price = 1199.99, picture = "dellxps13.png", category = "Laptops"),
            // Additional products...
        )

        // Insert each product into the database using ContentValues
        products.forEach { product ->
            val values = ContentValues().apply {
                put(COLUMN_NAME, product.name)
                put(COLUMN_DESCRIPTION, product.description)
                put(COLUMN_SELLER, product.seller)
                put(COLUMN_PRICE, product.price)
                put(COLUMN_PICTURE, product.picture)
                put(COLUMN_CATEGORY, product.category)
            }
            db?.insert(TABLE_PRODUCTS, null, values)
        }
    }

    // Retrieve all products from the database
    fun getAllProducts(): MutableList<Product> {
        val productList = mutableListOf<Product>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val product = Product(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    seller = getString(getColumnIndexOrThrow(COLUMN_SELLER)),
                    price = getDouble(getColumnIndexOrThrow(COLUMN_PRICE)),
                    picture = getString(getColumnIndexOrThrow(COLUMN_PICTURE)),
                    category = getString(getColumnIndexOrThrow(COLUMN_CATEGORY))
                )
                productList.add(product)
            }
            close()
        }
        db.close()
        return productList
    }

    // Retrieve products from the database by category
    fun getProductsByCategory(category: String): MutableList<Product> {
        val productList = mutableListOf<Product>()
        val db = this.readableDatabase
        val selection = "category = ?"
        val selectionArgs = arrayOf(category)
        val cursor = db.query(TABLE_PRODUCTS, null, selection, selectionArgs, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val product = Product(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    seller = getString(getColumnIndexOrThrow(COLUMN_SELLER)),
                    price = getDouble(getColumnIndexOrThrow(COLUMN_PRICE)),
                    picture = getString(getColumnIndexOrThrow(COLUMN_PICTURE)),
                    category = getString(getColumnIndexOrThrow(COLUMN_CATEGORY))
                )
                productList.add(product)
            }
            close()
        }
        db.close()
        return productList
    }
}

