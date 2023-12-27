package ca.dal.cs.csci4176.rental

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*
//use the kotlin-android-extension to allows us to access views from our XML directly in activities, fragments and views.
//URL: https://www.folkstalk.com/2022/09/import-kotlinx-android-synthetic-main-activity_main-with-code-examples.html

class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var homeListAdapter: HomeListAdapter
    private val list: MutableList<Posting> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            homeListAdapter =  HomeListAdapter(it,list);
        }

        rv_list?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeListAdapter
        }
        tv_home.setOnClickListener(this@HomeFragment)
        tv_apartment.setOnClickListener(this@HomeFragment)
        tv_contact.setOnClickListener(this@HomeFragment)
        tv_smile.setOnClickListener(this@HomeFragment)
        iv_search.setOnClickListener(this@HomeFragment)
        iv_location.setOnClickListener(this@HomeFragment)
        et_search.setOnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                search()
            }
            false
        }
        homeListAdapter.setOnItemClickListener(object : HomeListAdapter.OnItemClickListener {
            override fun onItemClick(index: Int) {
                var intent = Intent(
                    activity,
                    HomeDetailActivity::class.java
                )
                intent.putExtra("post", list[index])
                startActivity(
                    intent
                )
            }
        })

        FirebaseDatabase.getInstance().getReference("Users").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
        initData();
    }

    private fun initData() {
        var databaseReference = FirebaseDatabase.getInstance().reference.child("Posting")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (snapshot in dataSnapshot.children) {
                    val posting = snapshot.getValue(Posting::class.java)
                    posting?.let { list.add(it) }
                }
                homeListAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onClick(p0: View?) {
        when (p0) {
            iv_location -> startActivity(Intent(activity, MapsActivity::class.java))
            iv_search -> search()
            tv_smile -> filter("smile")
            tv_home -> filter("home")
            tv_apartment -> filter("apartment")
            tv_contact -> filter("contact")
        }
    }

    /**
     * filter
     */
    fun filter(filter: String) {

    }

    private fun search() {
        hideSoftInput()
    }

    private fun hideSoftInput() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et_search?.windowToken, 0)
    }
}