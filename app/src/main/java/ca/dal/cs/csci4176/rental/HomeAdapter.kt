package ca.dal.cs.csci4176.rental

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter(private var context: Context, var notices: List<Posting?>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val posting = notices[position]
        holder.tvTitle.text = posting!!.title
        holder.tvDes.text = posting.description
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(position)
            }
        }
        holder.itemView.setOnLongClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemLongClick(position)
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return notices.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_pic: ImageView
        var tvDes: TextView
        var tvTitle: TextView

        init {
            iv_pic = itemView.findViewById(R.id.iv_pic)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDes = itemView.findViewById(R.id.tvDes)
        }
    }

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }
}