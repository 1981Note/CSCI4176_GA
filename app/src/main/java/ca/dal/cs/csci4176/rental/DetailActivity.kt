package ca.dal.cs.csci4176.rental

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //set the toolbar named Info
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Info"
        setSupportActionBar(toolbar)

        //find the variable place for detail page
        val posting = intent.getSerializableExtra("data") as Posting
        val tvTitle = findViewById<TextView>(R.id.Title)
        val Address = findViewById<TextView>(R.id.Address)
        val category = findViewById<TextView>(R.id.category)
        val price = findViewById<TextView>(R.id.price)
        val des = findViewById<TextView>(R.id.des)

        //set the number to each variable place
        tvTitle.text = posting.title
        Address.text = posting.address
        category.text = posting.category
        price.text = posting.price
        des.text = posting.description

        //store the history
        val sp = getSharedPreferences("history", MODE_PRIVATE)
        var ids = sp.getString("datas", "")
        //Check the post id in local memory, put it in when empty
        if (TextUtils.isEmpty(ids)) {
            sp.edit().putString("datas", posting.postId).commit()
        } else {
            //saprate the local ID
            val datas = ids!!.split("#").toTypedArray()
            var isCon = false
            //judge there is no current ID in least 10 tips
            for (i in datas.indices) {
                if (datas[i] == posting.postId) {
                    isCon = true
                }
            }
            //through out it when do nnot exists
            if (isCon) {
                return
            }
            //Add the information behind it when there is less than 10 tips
            if (datas.size < 10) {
                ids = ids + "#" + posting.postId
                sp.edit().putString("datas", ids).commit()
            } else {
                //moved the earliest tips when greater then 10
                var temp = ""
                for (i in 0..8) {
                    temp = temp + "#" + datas[i]
                }
                temp = temp + "#" + posting.postId
                sp.edit().putString("datas", temp).commit()
            }
        }
    }
}