package com.ener.kev

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ener.kev.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class Home : Fragment() {

     private  lateinit var binding: FragmentHomeBinding
    val adaptor = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recView.setHasFixedSize(true)

        DisplayData()


    }



    private fun DisplayData() {


        fetchUsers()

    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/CustomerData")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    Log.d(TAG, "${it}")
                    val user = it.getValue(CustomerData::class.java)

                    if (user!=null)adaptor.add(UserItem(user))
                }
                binding.recView.adapter = adaptor
            }

            override fun onCancelled(error: DatabaseError) {
                //To be implemented??
            }

        })
    }


}

class   UserItem(val user:CustomerData): Item<GroupieViewHolder>(){
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.contact_name).text = user.name + " " + user.surname
        viewHolder.itemView.findViewById<TextView>(R.id.date).text = user.date
        viewHolder.itemView.findViewById<TextView>(R.id.contact_phone).text = user.phone
        viewHolder.itemView.findViewById<TextView>(R.id.contact_email).text = user.email
    }

    override fun getLayout(): Int {
        return R.layout.activity_home_list_temp
    }

}