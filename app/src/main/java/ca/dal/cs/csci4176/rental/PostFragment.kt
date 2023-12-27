package ca.dal.cs.csci4176.rental

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase

class PostFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var title: EditText
    private lateinit var postal: EditText
    private lateinit var spinner: Spinner
    private lateinit var price: EditText
    private lateinit var multi: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_post, container, false)
        auth = Firebase.auth
        title = view.findViewById(R.id.Title)
        spinner = view.findViewById(R.id.spinner)
        multi = view.findViewById(R.id.textMultiLine)
        postal = view.findViewById(R.id.postalAddress)
        spinner = view.findViewById(R.id.spinner)
        price = view.findViewById(R.id.price)
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.postType,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }

        //val upload: Button = view.findViewById(R.id.uploadImage)
        //upload.setOnClickListener {
        //   gallery()

        //}
        val post: Button = view.findViewById(R.id.postButton)
        post.setOnClickListener {
            post()
        }
        return view
    }

    /* private fun galleryA() {
     var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
     intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
     intent.addCategory(Intent.CATEGORY_OPENABLE)
     intent.type = "image/*"
     startActivityForResult(intent, 200);
     }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         }
 */

     */
    private fun post(){
        val titl = title.text.toString().trim()
        val multiT = multi.text.toString().trim()
        val posta = postal.text.toString()
        val spinS = spinner.selectedItem.toString()
        val pri = price.text.toString().trim()

        if (titl.isBlank()){
            title.error = "Title Required"
            title.requestFocus()
            return
        }
        if (multiT.isBlank()){
            multi.error = "Description Required"
            multi.requestFocus()
            return
        }
        if (posta.isBlank()){
            postal.error = "Address Required"
            postal.requestFocus()
            return
        }
        if (pri.isBlank()){
            price.error = "Price Required"
            price.requestFocus()
            return
        }
        if (!isNumeric(pri)){
            price.error = "Invalid Price"
            price.requestFocus()
            return
        }

        if(spinS == "Choose Posting Type"){
            val error:TextView = spinner.selectedView as TextView
            error.error = "Select an Option"
        }

        val postId = FirebaseDatabase.getInstance().getReference("Posting").push().key!!

        val pos: Posting? = auth.uid?.let {
            Posting(posta, spinS, multiT, postId, postId, pri,titl,it)
        }

        FirebaseDatabase.getInstance().getReference("Posting").child(postId).setValue(pos)

        val transaction = HomeFragment()
        fragmentManager?.beginTransaction()?.replace(R.id.flFragment, transaction)?.commit()
    }

    //check the input string is number
    private fun isNumeric(toCheck: String): Boolean {
        return toCheck.all { char -> char.isDigit() }
    }
}
