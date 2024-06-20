<br/>
<p align="center">
  
  <h2 align="center">MatchMate</h2>

## ℹ️ Project Info : 
**MatchMate App** is built to demonstrate the use of Clean Architecture + MVVM in Android App Development. The app is mainly divided into 3 layers i.e. ```data```, ```domain``` & ```app``` (presentation).
It also showcases the implementation of some of the Modern Jetpack Architecture Component.

With the help of MatchMate Users can accept or decline the connect request and sync in persistent data to be accessible in offline mode.

## 👨‍💻 App Demo :
### Video
[Video Demo](https://github.com/swapnil-musale/MatchMate/assets/15209914/698ba5a3-e781-4698-b746-9209b623bd19)


## 📚 Libraries Used :
* [Kotlin][0]: Kotlin is statically typed & first-class language for Android Development.
* [Coroutines][1]: For asynchronous or non-blocking operations.
* [Flows][2]: Data Streaming API built on Kotlin Coroutines.
* [Jetpack Compose][3]: Toolkit for building native UI in a declarative way.
* [Retrofit][4]: Type-safe REST client for Android to consume RESTful web services.
* [Dagger Hilt][5]: Dependency injection library for Android.
* [Room][5]: Persistent database library - a wrapper over SQLite.
* [Coil][6]: Image loading library backed by Kotlin Coroutine.
* [Moshi][7]: Json to Kotlin Data class converter library.

[0]:  https://kotlinlang.org/
[1]:  https://kotlinlang.org/docs/coroutines-overview.html
[2]:  https://developer.android.com/kotlin/flow
[3]:  https://developer.android.com/jetpack/compose
[4]:  https://github.com/square/retrofit
[5]:  https://developer.android.com/jetpack/androidx/releases/room
[6]:  https://github.com/coil-kt/coil
[7]:  https://github.com/square/moshi

## 🧰 Other Tools :
* [Kotlin DSL][7]: Writing gradle script for Kotlin is more readable and offers better compile-time.
* [Version Catalog][8]: Declaring gradle dependencies & plugins in central place.
* [KSP][9]: Automate the build, test, and deployment pipeline through GitHub Actions.

[7]:  https://docs.gradle.org/current/userguide/kotlin_dsl.html
[8]:  https://docs.gradle.org/current/userguide/platforms.html
[9]:  https://kotlinlang.org/docs/ksp-overview.html#symbolprocessorprovider-the-entry-point

## 🏗️ App Architecture :
The app is built using a Clean Architecture design pattern. Which consists of three modules as follows :
* [**app**](https://github.com/swapnil-musale/MatchMate/tree/main/app) - This module is responsible for handling user interaction, displaying data and managing the user interface. Usually, this module is decoupled from business logic and communicates with the domain module for accessing any data.
* [**domain**](https://github.com/swapnil-musale/MatchMate/tree/main/domain) - This module contains core business logic and it doesn't contain any platform-related API usage and should only use Java/Kotlin language APIs.
* [**data**](https://github.com/swapnil-musale/MatchMate/tree/main/data) - This module is mainly responsible for accessing data from remote or local sources which is being called from the domain module only.

![Clean-Architecture-in-Android](https://github.com/swapnil-musale/JetJoke/assets/15209914/526ea05d-d3d1-49da-b034-74628871a774)

## Connect with me:
[![Github Profile](https://skillicons.dev/icons?i=github)](https://github.com/swapnil-musale)
[![LinkedIn](https://skillicons.dev/icons?i=linkedin)](https://linkedin.com/in/swapnil-musale)

## License :
```
MIT License

Copyright (c) 2023 Swapnil Musale

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

</br>
