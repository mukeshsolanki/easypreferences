package `in`.madapps.easypreferences.example

import `in`.madapps.prefrences.EasyPreferences
import `in`.madapps.prefrences.EasyPreferences.get
import `in`.madapps.prefrences.EasyPreferences.set
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {
  private val KEY = "TestKey"
  private val VALUE = "HelloWorkd"
  private val DEFAULT_VALUE = "Hello"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val prefs = EasyPreferences.defaultPrefs(this)
    prefs[KEY] = VALUE
    val value: String? = prefs[KEY]
    Log.d("value=>", value)
    prefs.edit()
        .clear()
        .apply()
    val defaultVal: String? = prefs[KEY, DEFAULT_VALUE]
    Log.d("defaultValue=>", defaultVal)
  }
}
