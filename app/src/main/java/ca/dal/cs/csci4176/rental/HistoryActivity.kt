package ca.dal.cs.csci4176.rental

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class HistoryActivity : AppCompatActivity() {
    private lateinit  var recyclerview: RecyclerView
    private lateinit var mAdapter: HomeAdapter
    private val list: MutableList<Posting?> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "View rent History"
        setSupportActionBar(toolbar)
        recyclerview = findViewById(R.id.mRecyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        mAdapter = HomeAdapter(this, list)
        recyclerview.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //passing a posting
                // transfer the event more specific when clicking the button
                val posting = list[position]
                val intent = Intent(this@HistoryActivity, DetailActivity::class.java)
                intent.putExtra("data", posting)
                startActivity(intent)
            }

            override fun onItemLongClick(position: Int) {}
        })
        init()
    }

    private fun init() {
        //record recent 10 tips in local
        val sp = getSharedPreferences("history", MODE_PRIVATE)
        val ids = sp.getString("datas", "")
        if (TextUtils.isEmpty(ids)) {
            return
        }
        val datas = ids!!.split("#").toTypedArray()
        if (datas.isEmpty()) {
            return
        }
        var databaseReference = FirebaseDatabase.getInstance().reference.child("Posting")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                //judge if the data is same as the data i saved(id)
                // add them into dataframe
                for (snapshot in dataSnapshot.children) {
                    val posting = snapshot.getValue(Posting::class.java)
                    posting!!.postId = snapshot.key
                    for (i in datas.indices) {
                        if (datas[i] == posting.postId) {
                            list.add(posting)
                        }
                    }
                }
                //reflesh
                mAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}