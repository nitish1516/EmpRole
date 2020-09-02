package com.example.emprole.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.emprole.R
import com.example.emprole.data.model.Data
import com.example.emprole.data.model.EmpDetailsPojo
import com.example.emprole.ui.main.view.EmpDetails
import com.google.gson.*
import kotlinx.android.synthetic.main.emplist_layout.view.*
import org.json.JSONObject
import java.lang.Exception
import java.lang.StringBuilder


class EmpAdapter (private val empsList:ArrayList<EmpDetailsPojo>,val mContext: Context) : RecyclerView.Adapter<EmpAdapter.DataViewHolder>(){

    class DataViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(empData: EmpDetailsPojo, context: Context, empsListAll:ArrayList<EmpDetailsPojo>) {

            itemView.textViewEmpName.text = empData.preferredFullName ?: "Name Missing"
            itemView.textViewEmpAge.text = empData.employeeAge ?: "0"
            Glide.with(itemView.imageViewAvatar.context)
                .load(empData.profileImage)
                .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.default_user))
                .into(itemView.imageViewAvatar)

            itemView.container.setTag(empData)

            itemView.container.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, EmpDetails::class.java)
                intent.putExtra("EmpData", empData)
                intent.putExtra("EmpDataAll", empsListAll)
                context.startActivity(intent)
            })
        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=  DataViewHolder(
    LayoutInflater.from(parent.context).inflate(
    R.layout.emplist_layout, parent,
    false
    )
    )

    override fun getItemCount(): Int =empsList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(empsList[position],mContext,empsList)

    fun addData(list: List<EmpDetailsPojo>)
    {
        empsList.addAll(list)
    }



}