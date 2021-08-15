package com.kalemlisipahi.foodbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kalemlisipahi.foodbook.R
import com.kalemlisipahi.foodbook.databinding.RecycleviewRowBinding
import com.kalemlisipahi.foodbook.model.FoodModel
import com.kalemlisipahi.foodbook.view.ListFragmentDirections
import kotlinx.android.synthetic.main.recycleview_row.view.*
import java.util.ArrayList

class FoodAdapter (private var adapterFoodList : ArrayList<FoodModel>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>(), FoodOnClick {

    class FoodViewHolder(var view : RecycleviewRowBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
       val view = DataBindingUtil.inflate<RecycleviewRowBinding>(LayoutInflater.from(parent.context),R.layout.recycleview_row,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.view.foodXML = adapterFoodList[position]

        //holder.itemView.listFoodNameText.text = adapterFoodList[position].isim
        //holder.itemView.listFoodCalText.text = adapterFoodList[position].kalori

        holder.view.listener = this
        //adapterFoodList[position].gorsel?.let { holder.itemView.listImageView.downloadImage(it, myPlaceHolder(holder.itemView.context)) }

    }

    override fun getItemCount(): Int {
        return adapterFoodList.size
    }

    fun updateData(newFoodList : List<FoodModel>)
    {
        adapterFoodList.clear()
        adapterFoodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun foodClicked(view: View) {
        Navigation.findNavController(view).navigate(ListFragmentDirections.actionListFragmentToContentFragment(view.uuId.text.toString().toInt()))
    }
}