package com.example.datetime


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var selectedPhotoUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etBirthDate = findViewById<EditText>(R.id.etBirthDate)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnUploadPhoto = findViewById<Button>(R.id.btnUploadPhoto)
        val ivPhoto = findViewById<ImageView>(R.id.ivPhoto)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {uri : Uri? ->
            if (uri != null){
                selectedPhotoUri = uri
                ivPhoto.setImageURI(uri)
            }
        }

            btnUploadPhoto.setOnClickListener{
                getContent.launch("image/*")
            }

            btnSave.setOnClickListener{
                val firstName = etFirstName.text.toString()
                val lastName = etLastName.text.toString()
                val birthDate = etBirthDate.text.toString()
                val phone = etPhone.text.toString()

                if (firstName.isEmpty() || lastName.isEmpty() || birthDate.isEmpty() || phone.isEmpty() || selectedPhotoUri == null){
                    Toast.makeText(this, "Заполниете все поля и загрузите фото", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val intent = Intent(this, DetailActivity::class.java).apply {

                    putExtra("firstName", firstName)
                    putExtra("lastName", lastName)
                    putExtra("birthDate", birthDate)
                    putExtra("phone", phone)
                    putExtra("photoUri", selectedPhotoUri.toString() )
                }
                startActivity(intent)
            }
    }

}
