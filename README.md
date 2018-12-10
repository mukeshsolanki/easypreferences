<h1 align="center">Android Shared Preferences</h1>
<p align="center">
  <a class="badge-align" href="https://www.codacy.com/app/mukeshsolanki/easypreferences?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mukeshsolanki/easypreferences&amp;utm_campaign=Badge_Grade"><img src="https://api.codacy.com/project/badge/Grade/4829a852b0284e91ac2c1394519a5c1e"/></a>
  <a href="https://jitpack.io/#mukeshsolanki/easypreferences"> <img src="https://jitpack.io/v/mukeshsolanki/easypreferences/month.svg" /></a>
  <a href="https://jitpack.io/#mukeshsolanki/easypreferences"> <img src="https://jitpack.io/v/mukeshsolanki/easypreferences.svg" /></a>
  <a href="https://circleci.com/gh/mukeshsolanki/easypreferences/tree/master"> <img src="https://circleci.com/gh/mukeshsolanki/easypreferences/tree/master.svg?style=shield" /></a>
  <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"/></a>
  <br /><br />
  A simple easy to use library that helps you quickly store and retrive data from shared preferences
</p>

# Supporting Android Easy Preferences

Android Easy Preferences is an independent project with ongoing development and support made possible thanks to donations made by [these awesome backers](BACKERS.md#sponsors). If you'd like to join them, please consider:

  - [Become a backer or sponsor on Patreon](https://www.patreon.com/mukeshsolanki).
  - [One-time donation via PayPal](https://www.paypal.me/mukeshsolanki)

<a href="https://www.patreon.com/bePatron?c=935498" alt="Become a Patron"><img src="https://c5.patreon.com/external/logo/become_a_patron_button.png" /></a>

## How to integrate into your app

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
        implementation 'com.github.mukeshsolanki:easypreferences:<latest-version>'
}
```

## How to use the library
Okay seems like you integrated the library in your project but **how do you use it**? Well its really easy just follow the steps below.

```java
 //Create a new instance of EasyPreferences
 val prefs = EasyPreferences.defaultPrefs(this)

 //use that instance to save data
 prefs["TestKey"] = "HelloWorld"; //Save's a string value in your preferences
 prefs["Key2"] = 1; //Save's a int value in your preferences

 //use that instance to retrieve data
 val value: String? = prefs["TestKey"]; //retrives the data from preferences
 val defaultVal: Int? = prefs["Key2", 100] //retrives the data from preferences or default values if it does not exists
```
That's pretty much it. Looks like your all done here.

## Author
Maintained by [Mukesh Solanki](https://www.github.com/mukeshsolanki)

## Contribution
[![GitHub contributors](https://img.shields.io/github/contributors/mukeshsolanki/easypreferences.svg)](https://github.com/mukeshsolanki/easypreferences/graphs/contributors)

  - Bug reports and pull requests are welcome.
  - Make sure you use [square/java-code-styles](https://github.com/square/java-code-styles) to format your code.

  ## License
  ```
  MIT License

  Copyright (c) 2018 Mukesh Solanki

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
  ```
