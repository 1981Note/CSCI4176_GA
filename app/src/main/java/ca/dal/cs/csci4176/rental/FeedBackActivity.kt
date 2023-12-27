package ca.dal.cs.csci4176.rental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import ca.dal.cs.csci4176.rental.R
import android.widget.RatingBar
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap

class FeedBackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "FeedBack"
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { v: View? -> finish() }
        val title = findViewById<EditText>(R.id.et_title)
        val ratingBarEn = findViewById<RatingBar>(R.id.rb_en)
        val ratingBarService = findViewById<RatingBar>(R.id.rb_service)
        val des = findViewById<EditText>(R.id.des)
        val button = findViewById<Button>(R.id.submit)
        button.setOnClickListener {
            //get the context when clicking
            val title = title.text.toString()
            val env = ratingBarEn.rating
            val service = ratingBarService.rating
            val content = des.text.toString()
            val hashMap: HashMap<String, Any> = HashMap<String, Any>()
            hashMap["title"] = title
            hashMap["environment"] = env
            hashMap["service"] = service
            hashMap["content"] = content
            //upload the information in firebase after putting the context into hashmap
            FirebaseDatabase.getInstance().getReference("Feed").push().setValue(hashMap)
        }
    }
}