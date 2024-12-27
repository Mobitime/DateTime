package com.example.datetime

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DetailActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val ivDetailPhoto = findViewById<ImageView>(R.id.ivDetailPhoto)
        val tvFirstName = findViewById<TextView>(R.id.tvFirstName)
        val tvLastName = findViewById<TextView>(R.id.tvLastName)
        val tvPhone = findViewById<TextView>(R.id.tvPhone)
        val tvAge = findViewById<TextView>(R.id.tvAge)
        val tvDaysToBirthday = findViewById<TextView>(R.id.tvDaysToBirthday)

        val firstName = intent.getStringExtra("firstName") ?: ""
        val lastName = intent.getStringExtra("lastName") ?: ""
        val birthDate = intent.getStringExtra("birthDate") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""
        val photoUri = intent.getStringExtra("photoUri")?.let { Uri.parse(it) }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        tvFirstName.text = "Имя: $firstName"
        tvLastName.text = "Фамилия: $lastName"
        tvPhone.text = "Телефон: $phone"
        ivDetailPhoto.setImageURI(photoUri)

        if (birthDate.isNotEmpty()) {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val formattedBirthDate = if (birthDate.matches(Regex("\\d{8}"))) {
                "${birthDate.substring(0, 2)}.${birthDate.substring(2, 4)}.${birthDate.substring(4)}"
            }else{
                birthDate
            }
            val birthDateParsed = LocalDate.parse(formattedBirthDate, formatter)
            val currentDate = LocalDate.now()

            val age = ChronoUnit.YEARS.between(birthDateParsed, currentDate)
            tvAge.text = "Возраст: $age лет"

            val nextBirthday = birthDateParsed.withYear(currentDate.year).let {
                if (it.isBefore(currentDate)) it.plusYears(1) else it
            }

            val daysToBirthday = ChronoUnit.DAYS.between(currentDate, nextBirthday)
            val monthsToBirthday = ChronoUnit.MONTHS.between(currentDate, nextBirthday)

            tvDaysToBirthday.text =
                "До следующего дня рождения: $monthsToBirthday месяцев и ${daysToBirthday % 30} дней"
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
