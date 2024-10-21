package com.example.practice_iii

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practice_iii.databinding.ActivityEventDetailBinding

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding
    private var isReturningFromEmail = false
    private var selectedEvent: TodoItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedEvent = intent.getParcelableExtra<TodoItem>("SELECTED_EVENT")

        if (selectedEvent != null) {
            binding.titleTextView.text = selectedEvent?.title
            binding.descriptionTextView.text = selectedEvent?.description
        }
        binding.emailButton.setOnClickListener {
            sendEmail(selectedEvent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isReturningFromEmail) {
            isReturningFromEmail = false
            // Optionally, show a confirmation dialog here
            val data = Intent().apply {
                putExtra("ITEM_TO_REMOVE", selectedEvent?.id)
            }
            Toast.makeText(this, "Succesfully Sent Email", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK, data)
            finish()
        }
    }

    private fun sendEmail(event: TodoItem?) {
        if (event == null) return

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("sweng888mobileapps@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Information about ${event.title}")
            putExtra(Intent.EXTRA_TEXT, "Event Details:\nTitle: ${event.title}\nDescription: ${event.description}")
        }

        try {
            isReturningFromEmail = true
            startActivity(Intent.createChooser(emailIntent, "Send email using..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}

