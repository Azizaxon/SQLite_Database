package uz.aziza.sqlite_database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import uz.aziza.sqlite_database.adapters.MyRvAdapter
import uz.aziza.sqlite_database.adapters.RvEvent
import uz.aziza.sqlite_database.databinding.ActivityMainBinding
import uz.aziza.sqlite_database.databinding.ItemDialogBinding
import uz.aziza.sqlite_database.db.MyDbHelper
import uz.aziza.sqlite_database.models.MyContact

class MainActivity : AppCompatActivity(), RvEvent {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var myRvAdapter: MyRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDbHelper = MyDbHelper(this)
        myRvAdapter = MyRvAdapter(myDbHelper.getAllContact(), this)

        binding.apply {
            rv.adapter = myRvAdapter

            btnAdd.setOnClickListener {
                val dialog = AlertDialog.Builder(this@MainActivity).create()
                val itemDialog = ItemDialogBinding.inflate(layoutInflater)
                dialog.setView(itemDialog.root)

                itemDialog.apply {
                    btnSave.setOnClickListener {
                        val myContact = MyContact(
                            edtName.text.toString().trim(),
                            edtNumber.text.toString().trim()
                        )

                        myDbHelper.addContact(myContact)
                        Toast.makeText(this@MainActivity, "Save", Toast.LENGTH_SHORT).show()
                        dialog.cancel()
                        myRvAdapter.list = myDbHelper.getAllContact()
                        myRvAdapter.notifyDataSetChanged()
                    }
                }

                dialog.show()
            }
        }
    }

    override fun menuClick(myContact: MyContact, view: ImageView) {
        val popoupMenu = PopupMenu(this, view)
        popoupMenu.inflate(R.menu.my_popup_menu)

        popoupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_delete->{
                    myDbHelper.deleteContact(myContact)
                    myRvAdapter.list = myDbHelper.getAllContact()
                    myRvAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()

                }
                R.id.menu_edit->{

                    val dialog = AlertDialog.Builder(this).create()
                    val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
                    dialog.setView(itemDialogBinding.root)

                    itemDialogBinding.edtName.setText(myContact.name)
                    itemDialogBinding.edtNumber.setText(myContact.number)

                    itemDialogBinding.btnSave.setOnClickListener {

                        myContact.name = itemDialogBinding.edtName.text.toString().trim()
                        myContact.number = itemDialogBinding.edtNumber.text.toString().trim()

                        myDbHelper.editContact(myContact)
                        myRvAdapter.list = myDbHelper.getAllContact()
                        myRvAdapter.notifyDataSetChanged()
                        dialog.dismiss()
                        Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show()
                    }



                    dialog.show()

                }
            }
            true
        }

        popoupMenu.show()
    }
}

