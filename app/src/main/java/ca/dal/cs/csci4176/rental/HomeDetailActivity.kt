package ca.dal.cs.csci4176.rental

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class HomeDetailActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_detail)
        val data:Posting = intent.getSerializableExtra("post") as Posting
        findViewById<TextView>(R.id.tv_title).text = data.title
        findViewById<TextView>(R.id.tv_price).text = data.price
        findViewById<TextView>(R.id.tv_content).text = data.description
    }
}