package ca.dal.cs.csci4176.rental

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var rootVIew: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //if click the sign out button then dump to the main activity
        rootVIew.findViewById<Button>(R.id.signOUt).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@ProfileFragment.context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        //if click the my post page then dump to the postActivity page
        rootVIew.findViewById<Button>(R.id.my_post).setOnClickListener {
            startActivity(Intent(activity,PostActivity::class.java))
        }
        //if click the button feedback then dump to the feedback page
        rootVIew.findViewById<Button>(R.id.feed).setOnClickListener {
            startActivity(Intent(activity,FeedBackActivity::class.java))
        }
        //if click the history button then dump to the history page
        rootVIew.findViewById<Button>(R.id.his).setOnClickListener {
            startActivity(Intent(activity,HistoryActivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootVIew =  inflater.inflate(R.layout.fragment_profile, container, false)
        return rootVIew;
    }


}

