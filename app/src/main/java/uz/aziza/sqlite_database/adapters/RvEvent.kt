package uz.aziza.sqlite_database.adapters

import android.view.View
import android.widget.ImageView
import uz.aziza.sqlite_database.models.MyContact

interface RvEvent {
    fun menuClick(myContact: MyContact, view: ImageView)
}