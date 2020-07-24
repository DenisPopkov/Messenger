package ru.popkovden.messengerapplication.data.repository.database.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contactsTable")
    suspend fun getAllContacts(): List<ContactsEntities>

    @Insert
    suspend fun insertNewContacts(contactsEntities: ContactsEntities): ContactsEntities

    @Update
    suspend fun updateContacts(): List<ContactsEntities>
}