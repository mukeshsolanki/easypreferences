<h1 align="center">Android Shared Preferences</h1>
<p align="center">
  <a href="https://android-arsenal.com/api?level=9"> <img src="https://img.shields.io/badge/API-9%2B-blue.svg?style=flat" /></a>
  <a href="https://jitpack.io/#mukeshsolanki/Android-Shared-Preferences-TinyDB-"> <img src="https://jitpack.io/v/mukeshsolanki/Android-Shared-Preferences-TinyDB-.svg" /></a>
  <a href="http://android-arsenal.com/details/1/3805"> <https://img.shields.io/badge/Android%20Arsenal-Android--Shared--Preferences-brightgreen.svg?style=flat" /></a>
  <a href="https://travis-ci.org/mukeshsolanki/Android-Shared-Preferences-TinyDB-"> <img src="https://travis-ci.org/mukeshsolanki/Android-Shared-Preferences-TinyDB-.svg?branch=master" /></a>
  <a href="https://www.paypal.me/mukeshsolanki"> <img src="https://img.shields.io/badge/paypal-donate-yellow.svg" /></a>
  <br />
  Based on kcochibili's TinyDB (https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo). This class simplifies calls to SharedPreferences in a line of code. It can also do more like: saving a list of strings, integers and saving object and lists. All in 1 line of code!
</p>

## How to integrate into your app?

Integrating the library into you app is extremely easy. A few changes in the build gradle and your all ready to use TinyDB. Make the following changes.

Step 1. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

```java
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```
Step 2. Add the dependency
```java
dependencies {
        compile 'com.github.mukeshsolanki:Android-Shared-Preferences-TinyDB-:1.0.2'
}
```

## How to use the library?
Okay seems like you integrated the library in your project but **how do you use it**? Well its really easy just follow the steps below.

```
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
