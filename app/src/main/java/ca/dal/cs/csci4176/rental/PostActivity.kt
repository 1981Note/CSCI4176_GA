package ca.dal.cs.csci4176.rental

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PostActivity : AppCompatActivity() {
    //initialize the recycler view, database reference, adapter, list
    private lateinit var recyclerview: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAdapter: HomeAdapter
    private val list: MutableList<Posting?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //set the toolbar title "View my post"
        toolbar.title = "View my post"
        setSupportActionBar(toolbar)
        recyclerview = findViewById(R.id.mRecyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        mAdapter = HomeAdapter(this, list)
        recyclerview.adapter = mAdapter
        //set the click listener to the adapter
        mAdapter.setOnItemClickListener(object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val posting = list[position]
                val intent = Intent(this@PostActivity, DetailActivity::class.java)
                intent.putExtra("data", posting)
                startActivity(intent)
            }

            override fun onItemLongClick(position: Int) {}
        })
        init()
    }

    private fun init() {
        //get the data from the firebase child named Posting
        databaseReference = FirebaseDatabase.getInstance().reference.child("Posting")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (snapshot in dataSnapshot.children) {
                    val social = snapshot.getValue(Posting::class.java)
                    social?.let {
                        social.postId = snapshot.key
                        if (it.uid.equals(FirebaseAuth.getInstance().uid)){
                            list.add(social)
                        }

                    }

                }
                mAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}