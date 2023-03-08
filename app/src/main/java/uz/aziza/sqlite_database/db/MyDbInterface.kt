package uz.aziza.sqlite_database.db

import uz.aziza.sqlite_database.models.MyContact

interface MyDbInterface {
    fun addContact(myContact: MyContact)
    fun getAllContact():List<MyContact>
    fun deleteContact(myContact: MyContact)
    fun editContact(myContact: MyContact)
}