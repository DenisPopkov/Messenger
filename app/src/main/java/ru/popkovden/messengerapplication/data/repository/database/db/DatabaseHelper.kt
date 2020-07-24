package ru.popkovden.messengerapplication.data.repository.database.db

interface DatabaseHelper {

    suspend fun getContacts(): List<ContactsEntities>
    suspend fun insertContacts(contacts: ContactsEntities): ContactsEntities
}