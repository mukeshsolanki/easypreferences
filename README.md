# Android-Shared-Preferences

Based on kcochibili's TinyDB (https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo)

This class simplifies calls to SharedPreferences in a line of code. It can also do more like: saving a list of strings, integers and saving object and lists. All in 1 line of code!

## How to integrate into your app?
Integrating the library into you app is extremely easy. A few changes in the build gradle and your all ready to use TinyDB. Make the following changes to build.gradle inside you app.
```groovy
.....
dependencies {
  ...
  compile 'com.mukesh:tinydb:1.0.1'
}
```

## How to use the library?
Okay seems like you integrated the library in your project but **how do you use it**? Well its really easy just follow the steps below.

```java
 //Create a new instance of TinyDB
 TinyDB tinyDB=new TinyDB(appContext);
 
 //use that instance to save data
 
 tinyDB.putString(key,value); //Save's a string value in your preferences
 tinyDB.putInt(key,value); //Save's a int value in your preferences
 
 
 //use that instance to retrieve data
 tinyDB.getString(key); //retrives the data from preferences or default values if it does not exists 
 tinyDB.getBoolean(key); //retrives the data from preferences or default values if it does not exists
```
That's pretty much it. Looks like your all done here.
