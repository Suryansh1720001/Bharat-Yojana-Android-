package com.yojana.bharat.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yojana.bharat.Heading
import com.yojana.bharat.R
import com.yojana.bharat.databinding.ItemSchemeBinding

//class scheme_item_Adapter(private val OpenListener:(id:Int)->Unit,
//                          private val items: ArrayList<Heading>,
//
//                          ) : RecyclerView.Adapter<scheme_item_Adapter.ViewHolder>()  {
//
//
//
//    class ViewHolder(binding: ItemSchemeBinding): RecyclerView.ViewHolder(binding.root){
//        val tvSchemeName= binding?.tvSchemeName
//        val Scheme_item= binding?.schemeItem
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(ItemSchemeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
//
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        val item = items[position]
//            holder.tvSchemeName?.text =item.title
//
//        holder.Scheme_item?.setOnClickListener{
//            OpenListener.invoke(item.id)
//        }
//    }
//}


class scheme_item_Adapter(private val schemeList: ArrayList<Heading>, private val onItemClicked: (Int) -> Unit) :
    RecyclerView.Adapter<scheme_item_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_scheme, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scheme = schemeList[position]
        holder.bind(scheme)
        holder.itemView.setOnClickListener {
            onItemClicked(scheme.id)
        }
    }

    override fun getItemCount(): Int {
        return schemeList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_scheme_name)

        fun bind(scheme: Heading) {
            titleTextView.text = scheme.title
        }
    }
}