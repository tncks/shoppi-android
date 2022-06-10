package com.shoppi.app.ui.tutorials.calling

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoppi.app.R
import android.app.AlertDialog
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CallActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)


        val ctAdapter = ContactAdapter({ contact ->
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NAME, contact.name)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER, contact.number)
            intent.putExtra(AddActivity.EXTRA_CONTACT_ID, contact.id)
            startActivity(intent)
        }, { contact ->
            deleteDialog(contact)
        })

        val lm = LinearLayoutManager(this)


        findViewById<RecyclerView>(R.id.main_recycleview).apply {
            adapter = ctAdapter
            layoutManager = lm
            setHasFixedSize(true)
        }


        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this) { contacts ->
            ctAdapter.setContacts(contacts!!)
        }


        findViewById<Button>(R.id.main_button).apply {
            setOnClickListener {
                val intent = Intent(this@CallActivity, AddActivity::class.java)
                startActivity(intent)
            }
        }

        findViewById<FloatingActionButton>(R.id.fdebugbtn).apply {
            setOnClickListener {
                contactViewModel.cleardropping()
                contactViewModel.getAll()
                ctAdapter.setDefault(listOf<Contact>())
            }
        }
    }

    private fun deleteDialog(contact: Contact) {
        Toast.makeText(applicationContext, "디버깅을 위해 현재 비활성화 된 클릭입니다, 삭제기능은 곧 생기게 됩니다.", Toast.LENGTH_SHORT).show()
        Log.i("dummy", contact.name)
//        val builder = AlertDialog.Builder(this)
//        builder.setMessage("Delete selected contact?")
//            .setNegativeButton("NO") { _, _ -> }
//            .setPositiveButton("YES") { _, _ ->
//                contactViewModel.delete(contact)
//            }
//        builder.show()
    }
}