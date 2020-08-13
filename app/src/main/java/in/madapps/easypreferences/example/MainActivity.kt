package `in`.madapps.easypreferences.example

import `in`.madapps.prefrences.EasyPreferences
import `in`.madapps.prefrences.EasyPreferences.get
import `in`.madapps.prefrences.EasyPreferences.set
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val KEY = "TestKey"
    private val DEFAULT_VALUE = "John Doe"
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = EasyPreferences.defaultPrefs(this)
        initializeUI()
    }

    private fun initializeUI() {
        saveButton.setOnClickListener {
            prefs[KEY] = nameEditText.text.toString()
            Toast.makeText(this, "Value saved to prefs", Toast.LENGTH_SHORT).show()
        }
        loadButton.setOnClickListener {
            nameEditText.setText(prefs[KEY, DEFAULT_VALUE])
        }
    }
}
