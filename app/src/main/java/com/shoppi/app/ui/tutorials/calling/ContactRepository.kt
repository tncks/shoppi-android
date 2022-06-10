package com.shoppi.app.ui.tutorials.calling

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactRepository(application: Application) {

    private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return contacts
    }

    fun insert(contact: Contact) {
        try {
            val thread = Thread {
                contactDao.insert(contact)
            }
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete(contact: Contact) {
        try {
            val thread = Thread {
                contactDao.delete(contact)
            }
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun cleardropping() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.IO) {
                    contactDao.cleardropping()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}