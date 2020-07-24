package ru.popkovden.messengerapplication.data.repository.database.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactsEntities(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "full_contactsName") val contactName: String,
    @ColumnInfo(name = "reference_contactPhoto") val contactsPhoto: String
)