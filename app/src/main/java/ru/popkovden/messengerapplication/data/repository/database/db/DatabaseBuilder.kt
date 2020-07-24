package ru.popkovden.messengerapplication.data.repository.database.db

//object DatabaseBuilder {
//
//    private var instance: ContactsDatabase? = null
//
//    fun getInstance(context: Context): ContactsDatabase {
//        if (instance == null) {
//            synchronized(ContactsDatabase::class.java) {
//                instance = buildRoomDB(context)
//            }
//        }
//
//        return instance!!
//    }
//
//    private fun buildRoomDB(context: Context) =
//        Room.databaseBuilder(context.applicationContext, ContactsDatabase::class.java, "contactsDatabase")
//            .build()
//}