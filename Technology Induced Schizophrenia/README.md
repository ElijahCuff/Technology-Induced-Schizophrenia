# Invisidroid
Invisidroid was inspired by personally tested hidden applications on Androids Play store,    
the concept was to help developers with the difficulties of building their own hidden     
applications on Android.    

## What is a Hidden Application ?
* Hidden applications are not launched by the App Launcher but by calling a secret number in your dialer.    
* A proper Hidden application will allow you to make the app completely disappear from the App Launcher or Homescreen.

## How does it work ?
* First the user Launches the application as a Normal app using the intent "android.intent.category.LAUNCHER"
* Second, the user Allows the hidden Application Feature and this changes the Activity launch intent to
"android.intent.category.DEFAULT"
* This Disables the Launcher Icon because the activity is now considered something similar to a Widget, a 
Widget doesn't require a Launcher Icon.
* The app changes the activity Launch intent using Activity Aliasing.


Usually This is a cumbersome and annoying process with changing API's but     
i have built this to work for Android 9 and still not need the     
permission acceptance for everything.    
 
This application is specifically for Security Research development and     
education, however considering i have released it under MIT license terms     
you are free to modify and distribute it for educational purposes.    
    
- Kind Regards, Woke.   


![screen](IMG_20200616_120406.jpg)


## Technology
* Android
* Java
* XML

## Installation
* You can choose to test the application by downloading the Test.apk file,    
  otherwise building instructions are below.    
* Launch the application from your secret code.   

## Building Instructions
* Download and Open the project in Android Studio    
* Customise the Project and give it a test    


## Development Notes
* Activity Aliasing was used over Application Aliasing for security reasons.    


### Test the Application in a Pre-built APK
[![demo button](https://i.imgur.com/3Ugm8J7.jpg)](https://github.com/WokeWorld/Invisidroid/blob/master/Invisidroid.apk?raw=true) 

