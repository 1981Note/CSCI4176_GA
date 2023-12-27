package ca.dal.cs.csci4176.rental
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties

data class Posting(var address: String? = "",
                   var category: String? = "",
                   var description: String? = "",
                   var postId: String? = "",
                   var posterId: String? = "",
                   var price: String? = ""
                   , var title: String? = "",
                   var uid: String? = ""):java.io.Serializable
