package ca.dal.cs.csci4176.rental

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_home_list.view.*

class HomeListAdapter(private val context: Context,var dataList:List<Posting>) :
    RecyclerView.Adapter<HomeListAdapter.HomeListViewHolder>() {
    private var listener: OnItemClickListener? = null

    class HomeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {
        return HomeListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_home_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeListViewHolder, position: Int) {
        holder.itemView.tv_text.text = dataList[position].title
        holder.itemView.setOnClickListener { listener?.onItemClick(position) }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        listener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick( index:Int)
    }

}