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
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_PRODUCTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_SELLER TEXT,
                $COLUMN_PRICE REAL,
                $COLUMN_PICTURE TEXT
            )
        """.trimIndent()
        db?.execSQL(createTable)
        insertInitialProducts(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    private fun insertInitialProducts(db: SQLiteDatabase?) {
        val products = listOf(
            Product(name = "Apple iPhone 14 Pro", description = "6.1-inch display, A16 Bionic chip, 128GB storage", seller = "Apple", price = 999.0, picture = "iphone14pro.png"),
            Product(name = "Samsung Galaxy S23 Ultra", description = "6.8-inch AMOLED display, Snapdragon 8 Gen 2, 256GB storage", seller = "Samsung", price = 1199.99, picture = "galaxys23ultra.png"),
            Product(name = "Sony WH-1000XM5", description = "Wireless noise-canceling headphones with up to 30-hour battery life", seller = "Sony", price = 349.99, picture = "sonywh1000xm5.png"),
            Product(name = "Dell XPS 13", description = "13.4-inch laptop with Intel i7, 16GB RAM, 512GB SSD", seller = "Dell", price = 1199.99, picture = "dellxps13.png"),
            Product(name = "Apple MacBook Pro 16", description = "16-inch display, M2 Pro chip, 512GB SSD, 16GB RAM", seller = "Apple", price = 2499.0, picture = "macbookpro16.png"),
            Product(name = "Google Pixel 7", description = "6.3-inch OLED display, Tensor G2, 128GB storage", seller = "Google", price = 599.99, picture = "pixel7.png"),
            Product(name = "Amazon Echo Dot (5th Gen)", description = "Smart speaker with Alexa, 360-degree sound", seller = "Amazon", price = 49.99, picture = "echodot5.png"),
            Product(name = "Microsoft Surface Pro 9", description = "13-inch 2-in-1 tablet, Intel i7, 16GB RAM, 256GB SSD", seller = "Microsoft", price = 1399.99, picture = "surfacepro9.png"),
            Product(name = "Sony PlayStation 5", description = "Next-gen gaming console with 825GB SSD and 4K gaming support", seller = "Sony", price = 499.99, picture = "ps5.png"),
            Product(name = "Nvidia GeForce RTX 4090", description = "High-performance gaming GPU with 24GB GDDR6X memory", seller = "Nvidia", price = 1599.99, picture = "rtx4090.png"),
            Product(name = "Apple AirPods Pro (2nd Gen)", description = "Noise-canceling wireless earbuds with spatial audio", seller = "Apple", price = 249.99, picture = "airpodspro2.png"),
            Product(name = "HP Spectre x360", description = "13.5-inch 2-in-1 laptop, Intel i7, 16GB RAM, 512GB SSD", seller = "HP", price = 1299.99, picture = "spectrex360.png"),
            Product(name = "Lenovo Legion 5 Pro", description = "16-inch gaming laptop, Ryzen 7, 16GB RAM, RTX 3070", seller = "Lenovo", price = 1499.99, picture = "legion5pro.png"),
            Product(name = "ASUS ROG Strix Scar 17", description = "17.3-inch gaming laptop, Intel i9, 32GB RAM, RTX 3080", seller = "ASUS", price = 2999.99, picture = "rogstrixscar17.png"),
            Product(name = "Google Nest Learning Thermostat", description = "Smart thermostat that learns your preferences", seller = "Google", price = 249.99, picture = "nestthermostat.png"),
            Product(name = "DJI Mini 3 Pro", description = "Compact camera drone with 4K video and 34-minute flight time", seller = "DJI", price = 759.99, picture = "dji_mini3pro.png"),
            Product(name = "Fitbit Charge 5", description = "Fitness and health tracker with built-in GPS and heart rate monitor", seller = "Fitbit", price = 149.99, picture = "fitbitcharge5.png"),
            Product(name = "Logitech MX Master 3S", description = "Wireless mouse with ergonomic design and customizable buttons", seller = "Logitech", price = 99.99, picture = "mxmaster3s.png"),
            Product(name = "Razer BlackWidow V3 Pro", description = "Wireless mechanical gaming keyboard with Razer Green switches", seller = "Razer", price = 229.99, picture = "blackwidowv3pro.png"),
            Product(name = "Canon EOS R6", description = "Full-frame mirrorless camera with 20.1 MP sensor and 4K video recording", seller = "Canon", price = 2499.0, picture = "eosr6.png")
        )

        products.forEach { product ->
            val values = ContentValues().apply {
                put(COLUMN_NAME, product.name)
                put(COLUMN_DESCRIPTION, product.description)
                put(COLUMN_SELLER, product.seller)
                put(COLUMN_PRICE, product.price)
                put(COLUMN_PICTURE, product.picture)
            }
            db?.insert(TABLE_PRODUCTS, null, values)
        }
    }

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
                    picture = getString(getColumnIndexOrThrow(COLUMN_PICTURE))
                )
                productList.add(product)
            }
            close()
        }
        db.close()
        return productList
    }
}
