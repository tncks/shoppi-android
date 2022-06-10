package com.shoppi.app.ui.tutorials.calling

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.shoppi.app.R

class AddActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        if (intent != null && intent.hasExtra(EXTRA_CONTACT_NAME) && intent.hasExtra(EXTRA_CONTACT_NUMBER)
            && intent.hasExtra(EXTRA_CONTACT_ID)
        ) {
            findViewById<EditText>(R.id.add_edittext_name).setText(intent.getStringExtra(EXTRA_CONTACT_NAME))
            findViewById<EditText>(R.id.add_edittext_number).setText(intent.getStringExtra(EXTRA_CONTACT_NUMBER))

            id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
        }


        findViewById<Button>(R.id.add_button).setOnClickListener {


            val name = findViewById<EditText>(R.id.add_edittext_name).text.toString().trim()
            val number = findViewById<EditText>(R.id.add_edittext_number).text.toString()

            if (name.isEmpty() || number.isEmpty()) {
                Toast.makeText(this, "Please enter name and number.", Toast.LENGTH_SHORT).show()
            } else {
                val initial = name[0].toUpperCase()
                val contact = Contact(id, name, number, initial)
                contactViewModel.insert(contact)
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_CONTACT_NAME = "EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"
    }
}