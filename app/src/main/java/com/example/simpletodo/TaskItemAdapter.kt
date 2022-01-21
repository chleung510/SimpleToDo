package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *  A bridge to tell the recyclerview how to display the data we give it
 */
class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickedListener: OnLongClickListener) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    // An interface allows MainActivity to implement onItemClicked function
    interface OnLongClickListener {
        fun onItemLongClicked(postion: Int)
    }
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get the data model based on position
        val item = listOfItems.get(position)

        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size // To get number of items
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    // itemView is every single view(task) on the list.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        // For storing references to elements on our layout view
        val textView : TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnClickListener {
                longClickedListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}