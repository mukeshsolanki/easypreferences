package com.madapps.prefrences

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Environment
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays

class EasyPrefrences(appContext: Context) {

  // region Variables
  private val preferences: SharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(appContext)
  private var defaultAppImageLocation: String? = null
  /**
   * Returns the String path of the last saved image
   *
   * @return string path of the last saved image
   */
  private var savedImagePath = ""
  // endregion

  // region Config
  /**
   * Creates the path for the image with name 'imageName' in DEFAULT_APP.. directory
   *
   * @param imageName name of the image
   * @return the full path of the image. If it failed to create directory, return empty string
   */
  private fun setupFullPath(imageName: String): String {
    val mFolder = File(Environment.getExternalStorageDirectory(), defaultAppImageLocation!!)
    return if (isExternalStorageReadable && isExternalStorageWritable && !mFolder.exists() && !mFolder.mkdirs()) {
      Log.e("ERROR", "Failed to setup folder")
      ""
    } else mFolder.path + '/'.toString() + imageName
  }

  /**
   * Register SharedPreferences change listener
   *
   * @param listener listener object of OnSharedPreferenceChangeListener
   */
  fun registerOnSharedPreferenceChangeListener(
    listener: SharedPreferences.OnSharedPreferenceChangeListener
  ) = preferences.registerOnSharedPreferenceChangeListener(listener)

  /**
   * Unregister SharedPreferences change listener
   *
   * @param listener listener object of OnSharedPreferenceChangeListener to be unregistered
   */
  fun unregisterOnSharedPreferenceChangeListener(
    listener: SharedPreferences.OnSharedPreferenceChangeListener
  ) = preferences.unregisterOnSharedPreferenceChangeListener(listener)
  // endregion

  // region Get
  /**
   * Retrieve all values from SharedPreferences. Do not modify collection return by method
   *
   * @return a Map representing a list of key/value pairs from SharedPreferences
   */
  val all: Map<String, *>
    get() = preferences.all

  /**
   * Decodes the Bitmap from 'path' and returns it
   *
   * @param path image path
   * @return the Bitmap from 'path'
   */
  fun getImage(path: String): Bitmap? {
    var bitmapFromPath: Bitmap? = null
    try {
      bitmapFromPath = BitmapFactory.decodeFile(path)
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return bitmapFromPath
  }

  /**
   * Get int value from SharedPreferences at 'key'. If key not found, return 'defaultValue'
   *
   * @param key SharedPreferences key
   * @return int value at 'key' or 'defaultValue' if key not found
   */
  fun getInt(key: String): Int = preferences.getInt(key, 0)

  /**
   * Get parsed ArrayList of Integers from SharedPreferences at 'key'
   *
   * @param key SharedPreferences key
   * @return ArrayList of Integers
   */
  fun getListInt(key: String): ArrayList<Int> {
    val myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚")
    val arrayToList = ArrayList(Arrays.asList(*myList))
    return arrayToList.mapTo(ArrayList()) { Integer.parseInt(it) }
  }

  /**
   * Get long value from SharedPreferences at 'key'. If key not found, return 'defaultValue'
   *
   * @param key SharedPreferences key
   * @param defaultValue long value returned if key was not found
   * @return long value at 'key' or 'defaultValue' if key not found
   */
  fun getLong(key: String, defaultValue: Long): Long = preferences.getLong(key, defaultValue)

  /**
   * Get float value from SharedPreferences at 'key'. If key not found, return 'defaultValue'
   *
   * @param key SharedPreferences key
   * @return float value at 'key' or 'defaultValue' if key not found
   */
  fun getFloat(key: String): Float = preferences.getFloat(key, 0f)

  /**
   * Get double value from SharedPreferences at 'key'. If exception thrown, return 'defaultValue'
   *
   * @param key SharedPreferences key
   * @param defaultValue double value returned if exception is thrown
   * @return double value at 'key' or 'defaultValue' if exception is thrown
   */
  fun getDouble(key: String, defaultValue: Double): Double {
    val number = getString(key)
    return try {
      java.lang.Double.parseDouble(number)
    } catch (e: NumberFormatException) {
      defaultValue
    }
  }

  /**
   * Get parsed ArrayList of Double from SharedPreferences at 'key'
   *
   * @param key SharedPreferences key
   * @return ArrayList of Double
   */
  fun getListDouble(key: String): ArrayList<Double> {
    val myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚")
    val arrayToList = ArrayList(Arrays.asList(*myList))
    return arrayToList.mapTo(ArrayList()) { java.lang.Double.parseDouble(it) }
  }

  /**
   * Get String value from SharedPreferences at 'key'. If key not found, return ""
   *
   * @param key SharedPreferences key
   * @return String value at 'key' or "" (empty String) if key not found
   */
  fun getString(key: String): String = preferences.getString(key, "")

  /**
   * Get parsed ArrayList of String from SharedPreferences at 'key'
   *
   * @param key SharedPreferences key
   * @return ArrayList of String
   */
  fun getListString(key: String): ArrayList<String> = ArrayList(
    Arrays.asList(*TextUtils.split(preferences.getString(key, ""), "‚‗‚"))
  )

  /**
   * Get boolean value from SharedPreferences at 'key'. If key not found, return 'defaultValue'
   *
   * @param key SharedPreferences key
   * @return boolean value at 'key' or 'defaultValue' if key not found
   */
  fun getBoolean(key: String): Boolean = preferences.getBoolean(key, false)

  /**
   * Get parsed ArrayList of Boolean from SharedPreferences at 'key'
   *
   * @param key SharedPreferences key
   * @return ArrayList of Boolean
   */
  fun getListBoolean(key: String): ArrayList<Boolean> {
    val myList = getListString(key)
    val newList = ArrayList<Boolean>()
    myList.forEach { item ->
      when (item) {
        "true" -> newList.add(true)
        else -> newList.add(false)
      }
    }
    return newList
  }

  /**
   * Get Arraylist of Objects from SharedPreferences at 'key'
   *
   * @param  key  SharedPreferences key
   * @param  mClass  Class of the stored object
   * @param    gson  custom Gson object
   * @return      Stored ArrayList of Objects
   */
  @JvmOverloads fun getListObject(
    key: String, mClass: Class<*>,
    gson: Gson = Gson()
  ): ArrayList<Any> {
    val objStrings = getListString(key)
    return objStrings.mapTo(ArrayList()) { gson.fromJson(it, mClass) }
  }

  /**
   * Get Objects from SharedPreferences at 'key'
   *
   * @param  key    SharedPreferences key
   * @param  classOfT  Class of the stored object
   * @param    gson  custom Gson object
   * @return        Stored Object
   */
  @JvmOverloads fun getObject(key: String, classOfT: Class<*>, gson: Gson = Gson()): Any {
    val json = getString(key)
    return gson.fromJson(json, classOfT) ?: throw NullPointerException()
  }

  /**
   * Get Objects from SharedPreferences at 'key'
   *
   * @param  key    SharedPreferences key
   * @param  token  Gson token determing desired return type
   * @param    gson  custom Gson object
   * @return        Stored Object
   */
  fun <T> getObject(key: String, token: TypeToken<T>, gson: Gson): T {
    val json = getString(key)
    return gson.fromJson(json, token.type) ?: throw NullPointerException()
  }
  //endregion

  // region put
  /**
   * Saves 'theBitmap' into folder 'theFolder' with the name 'theImageName'
   *
   * @param theFolder the folder path dir you want to save it to e.g "DropBox/WorkImages"
   * @param theImageName the name you want to assign to the image file e.g "MeAtLunch.png"
   * @param theBitmap the image you want to save as a Bitmap
   * @return returns the full path(file system address) of the saved image
   */
  fun putImage(theFolder: String?, theImageName: String?, theBitmap: Bitmap?): String? {
    return when {
      theFolder == null || theImageName == null || theBitmap == null -> null
      else -> {
        this.defaultAppImageLocation = theFolder
        val mFullPath = setupFullPath(theImageName)

        if (mFullPath != "") {
          savedImagePath = mFullPath
          saveBitmap(mFullPath, theBitmap)
        }
        mFullPath
      }
    }
  }

  /**
   * Saves 'theBitmap' into 'fullPath'
   *
   * @param fullPath full path of the image file e.g. "Images/MeAtLunch.png"
   * @param theBitmap the image you want to save as a Bitmap
   * @return true if image was saved, false otherwise
   */
  fun putImageWithFullPath(fullPath: String?, theBitmap: Bitmap?): Boolean {
    return fullPath != null && theBitmap != null && saveBitmap(fullPath, theBitmap)
  }

  /**
   * Saves the Bitmap as a PNG file at path 'fullPath'
   *
   * @param fullPath path of the image file
   * @param bitmap the image as a Bitmap
   * @return true if it successfully saved, false otherwise
   */
  private fun saveBitmap(fullPath: String?, bitmap: Bitmap?): Boolean {
    when {
      fullPath == null || bitmap == null -> return false
      else -> {
        var fileCreated = false
        var bitmapCompressed: Boolean
        var streamClosed = false
        val imageFile = File(fullPath)
        if (imageFile.exists() && !imageFile.delete()) return false
        try {
          fileCreated = imageFile.createNewFile()
        } catch (e: IOException) {
          e.printStackTrace()
        }
        var out: FileOutputStream? = null
        try {
          out = FileOutputStream(imageFile)
          bitmapCompressed = bitmap.compress(CompressFormat.PNG, 100, out)
        } catch (e: Exception) {
          e.printStackTrace()
          bitmapCompressed = false
        } finally {
          when {
            out != null -> try {
              out.flush()
              out.close()
              streamClosed = true
            } catch (e: IOException) {
              e.printStackTrace()
              streamClosed = false
            }
          }
        }
        return fileCreated && bitmapCompressed && streamClosed
      }
    }
  }

  /**
   * Put int value into SharedPreferences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param value int value to be added
   */
  fun putInt(key: String, value: Int) {
    checkForNullKey(key)
    preferences.edit().putInt(key, value).apply()
  }

  /**
   * Put ArrayList of Integer into SharedPreferences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param intList ArrayList of Integer to be added
   */
  fun putListInt(key: String, intList: ArrayList<Int>) {
    checkForNullKey(key)
    val myIntList = intList.toTypedArray()
    preferences.edit().putString(key, TextUtils.join("‚‗‚", myIntList)).apply()
  }

  /**
   * Put long value into SharedPreferences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param value long value to be added
   */
  fun putLong(key: String, value: Long) {
    checkForNullKey(key)
    preferences.edit().putLong(key, value).apply()
  }

  /**
   * Put float value into SharedPreferences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param value float value to be added
   */
  fun putFloat(key: String, value: Float) {
    checkForNullKey(key)
    preferences.edit().putFloat(key, value).apply()
  }

  /**
   * Put double value into SharedPreferences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param value double value to be added
   */
  fun putDouble(key: String, value: Double) {
    checkForNullKey(key)
    putString(key, value.toString())
  }

  /**
   * Put ArrayList of Double into SharedPreferences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param doubleList ArrayList of Double to be added
   */
  fun putListDouble(key: String, doubleList: ArrayList<Double>) {
    checkForNullKey(key)
    val myDoubleList = doubleList.toTypedArray()
    preferences.edit().putString(key, TextUtils.join("‚‗‚", myDoubleList)).apply()
  }

  /**
   * Put String value into SharedPreferences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param value String value to be added
   */
  fun putString(key: String, value: String) {
    checkForNullKey(key)
    checkForNullValue(value)
    preferences.edit().putString(key, value).apply()
  }

  /**
   * Put ArrayList of String into SharedPreferences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param stringList ArrayList of String to be added
   */
  fun putListString(key: String, stringList: ArrayList<String>) {
    checkForNullKey(key)
    val myStringList = stringList.toTypedArray()
    preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply()
  }

  /**
   * Put boolean value into SharedPreferences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param value boolean value to be added
   */
  fun putBoolean(key: String, value: Boolean) {
    checkForNullKey(key)
    preferences.edit().putBoolean(key, value).apply()
  }

  /**
   * Put ArrayList of Boolean into SharedPreferences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param boolList ArrayList of Boolean to be added
   */
  fun putListBoolean(key: String, boolList: ArrayList<Boolean>) {
    checkForNullKey(key)
    val newList = ArrayList<String>()
    boolList.forEach { item ->
      when {
        item -> newList.add("true")
        else -> newList.add("false")
      }
    }
    putListString(key, newList)
  }

  /**
   * Put ObJect any type into SharedPrefrences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param obj is the Object you want to put
   * @param gson custom Gson object
   */
  @JvmOverloads fun putObject(key: String, obj: Any, gson: Gson = Gson()) {
    checkForNullKey(key)
    putString(key, gson.toJson(obj))
  }

  /**
   * Put ObJect any type into SharedPrefrences with 'key' and save
   *
   * @param key SharedPreferences key
   * @param objArray is the Object you want to put
   */
  @JvmOverloads fun putListObject(key: String, objArray: ArrayList<Any>, gson: Gson = Gson()) {
    checkForNullKey(key)
    putListString(key, objArray.mapTo(ArrayList()) { gson.toJson(it) })
  }
  // endregion

  // region Operations
  /**
   * Remove SharedPreferences item with 'key'
   *
   * @param key SharedPreferences key
   */
  operator fun contains(key: String): Boolean = preferences.contains(key)

  /**
   * Remove SharedPreferences item with 'key'
   *
   * @param key SharedPreferences key
   */
  fun remove(key: String) = preferences.edit().remove(key).apply()

  /**
   * Delete image file at 'path'
   *
   * @param path path of image file
   * @return true if it successfully deleted, false otherwise
   */
  fun deleteImage(path: String): Boolean = File(path).delete()

  /**
   * Clear SharedPreferences (remove everything)
   */
  fun clear() = preferences.edit().clear().apply()

  /**
   * null keys would corrupt the shared pref file and make them unreadable this is a preventive
   * measure
   *
   * @param key the pref key
   */
  fun checkForNullKey(key: String?) {
    key!!
  }

  /**
   * null keys would corrupt the shared pref file and make them unreadable this is a preventive
   * measure
   *
   * @param value the pref value
   */
  fun checkForNullValue(value: String?) {
    value!!
  }
  // endregion

  companion object {

    /**
     * Check if external storage is writable or not
     *
     * @return true if writable, false otherwise
     */
    val isExternalStorageWritable: Boolean
      get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

    /**
     * Check if external storage is readable or not
     *
     * @return true if readable, false otherwise
     */
    val isExternalStorageReadable: Boolean
      get() {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
      }
  }
}