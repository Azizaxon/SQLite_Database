package uz.aziza.sqlite_database.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.aziza.sqlite_database.databinding.ItemRvBinding
import uz.aziza.sqlite_database.models.MyContact

class MyRvAdapter(var list: List<MyContact> = emptyList(), val rvEvent: RvEvent): RecyclerView.Adapter<MyRvAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){

        fun onBind(myContact: MyContact, position: Int){
            itemRvBinding.tvId.text = myContact.id.toString()
            itemRvBinding.tvName.text = myContact.name
            itemRvBinding.tvNumber.text = myContact.number
            itemRvBinding.menuImage.setOnClickListener {
                rvEvent.menuClick(myContact, itemRvBinding.menuImage)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)

    }

    override fun getItemCount(): Int = list.size


}