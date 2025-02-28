package com.example.practica9

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

/*
Eduardo Talavera Ramos 00000245244
 */
class MainActivity : AppCompatActivity() {
    private val userRef = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnSave: Button = findViewById(R.id.save_button)

        btnSave.setOnClickListener {
            saveMarkFromForm()
        }

        userRef.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                try {
                    val usuario = dataSnapshot.getValue(User::class.java)
                    if(usuario != null) writeMark(usuario)
                } catch (e: Exception) {
                    Log.e("Firebase", "Error menso", e)
                }
            }
        })
    }

    private fun saveMarkFromForm(){
        val name: EditText = findViewById(R.id.et_name)
        val lastName: EditText = findViewById(R.id.et_lastName)
        val age: EditText = findViewById(R.id.et_age)

        val usuario = User(
            name.text.toString(),
            lastName.text.toString(),
            age.text.toString()
        )

        userRef.push().setValue(usuario)
    }

    private fun writeMark(mark: User){
        val listV: TextView = findViewById(R.id.list_textView)
        val text = listV.text.toString() + mark.toString() + "\n"
        listV.text = text
    }
}
