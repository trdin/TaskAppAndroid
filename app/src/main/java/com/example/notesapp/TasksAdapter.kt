package com.example.notesapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.Tasks
import com.squareup.picasso.Picasso
import timber.log.Timber


class TasksAdapter(private val data: Tasks, private val onClickObject:TasksAdapter.MyOnClick) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    interface MyOnClick {
        fun onClick(p0: View?, position:Int)
    }

    lateinit var onLongClickObject: TasksAdapter.MyOnClick



    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvYear: TextView = itemView.findViewById(R.id.tvContent)
        val line: CardView = itemView.findViewById(R.id.cvLine)
        val priority: TextView = itemView.findViewById(R.id.priority)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tasks_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = data.taskList[position]
        if(ItemsViewModel.done){
            Picasso.get().load("https://cdn-icons-png.flaticon.com/512/5610/5610944.png").placeholder(R.drawable.placeholder).error(R.drawable.error).into(holder.imageView);
        }else{
            Picasso.get().load("https://cdn-icons-png.flaticon.com/512/1810/1810745.png").placeholder(R.drawable.placeholder).error(R.drawable.error).into(holder.imageView);
        }

        // sets the text to the textview from our itemHolder class
        Timber.d("MM onBindViewHolder ${data.taskList.size}")
        holder.tvName.text = ItemsViewModel.title
        holder.tvYear.text = ItemsViewModel.contents
        holder.priority.text = ItemsViewModel.priority.toString()

        holder.line.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                Timber.d("Here code comes Click on ${holder.adapterPosition}")
                holder.line.setCardBackgroundColor( Color.RED) //LOCAL ACTION
                onClickObject.onClick(p0,holder.adapterPosition) //Action from Activity
            }
        })

        holder.line.setOnLongClickListener(object:View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                /*Timber.d("Delete line ${pos}")
                adapter.notifyDataSetChanged()
                app.saveToFile()*/
                Timber.d("Here code comes Click on ${holder.adapterPosition}")
                holder.line.setCardBackgroundColor( Color.GREEN) //LOCAL ACTION
                onLongClickObject.onClick(p0,holder.adapterPosition)
                return true
            }
        })





    }

}
