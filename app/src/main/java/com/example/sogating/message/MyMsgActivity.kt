package com.example.sogating.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.sogating.R
import com.example.sogating.auth.UserDataModel
import com.example.sogating.utils.FirebaseAuthUtils
import com.example.sogating.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener



class MyMsgActivity : AppCompatActivity() {

    private val TAG = "MyMsgActivity"

    lateinit var listviewAdapter : MsgAdapter
    val msgList = mutableListOf<MsgModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MyMsgActivity onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_msg)

        val listview = findViewById<ListView>(R.id.msgListView)

        listviewAdapter = MsgAdapter(this, msgList)
        listview.adapter = listviewAdapter

        getMyMsg()

    }


    private fun getMyMsg(){
        Log.d(TAG, "getMyMsg called")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()){

                }

                msgList.clear()

                for (dataModel in dataSnapshot.children) {
                    val msg = dataModel.getValue(MsgModel::class.java)
                    if (msg != null) {
                        msgList.add(msg!!)
                        Log.d(TAG, msg.toString())
                    }
                }
                msgList.reverse()

                listviewAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userMsgRef.child(FirebaseAuthUtils.getUid()).addValueEventListener(postListener)

    }

}

