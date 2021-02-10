[![Actions Status](https://github.com/KryptKode/CardInfoFinder/workflows/android/badge.svg)](https://github.com/KryptKode/CardInfoFinder/actions)
[![codecov](https://codecov.io/gh/KryptKode/CardInfoFinder/branch/master/graph/badge.svg?token=R1UHNXH1CZ)](https://codecov.io/gh/KryptKode/CardInfoFinder)
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.4.21-blue.svg)](http://kotlinlang.org/)
[![AGP](https://img.shields.io/badge/AGP-4.1.0-blue)](https://developer.android.com/studio/releases/gradle-plugin)
[![Gradle](https://img.shields.io/badge/Gradle-6.5-blue)](https://gradle.org)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

# Card Info Finder

A simple android application fetches details of a debit or credit card by the card number using the [Binlist API](https://binlist.net/). It  accepts  input from a user either by manual input or  scanning via OCR

### Outline

- [App Walkthrough](https://github.com/KryptKode/CardInfoFinder/README.md#app-walkthrough)
- [App Installation](https://github.com/KryptKode/CardInfoFinder/README.md#app-installation)
- [Building source](https://github.com/KryptKode/CardInfoFinder/README.md#building-source)
- [Top features](https://github.com/KryptKode/CardInfoFinder/README.md#top-features)
- [Architecture](https://github.com/KryptKode/CardInfoFinder/README.md#architecture)
- [Libraries](https://github.com/KryptKode/CardInfoFinder/README.md#libraries)
- [Testing](https://github.com/KryptKode/CardInfoFinder/README.md#testing)
- [Extras](https://github.com/KryptKode/CardInfoFinder/README.md#extras)

### App Walkthrough

The app presents a simple walkthrough screen the first time the app is run on a device. The home screen gives the user the option input the card number manually or by scanning via OCR. Once a card is scanned or the user finishes entering the card details, the app should display the card details fetched from the  [Binlist API](https://binlist.net/)


<h4 align="center">
<img alt="Light mode screenshot" src="https://user-images.githubusercontent.com/25648077/107159431-35938700-6990-11eb-84a4-00889d65e556.gif" width="35%" vspace="10" hspace="10">
Light mode
<img alt="Dark mode screenshot" src="https://user-images.githubusercontent.com/25648077/107159426-2f050f80-6990-11eb-803b-195ebd8b42b2.gif" width="35%" vspace="10" hspace="10">
 Dark mode
<br>
 

### App Installation

You can download the APK from [releases](https://github.com/KryptKode/DroidArch/releases).

### Building source

The app uses the Bouncer Cardscan SDK to scan cards. Get an api key [here](https://docs.getbouncer.com/bouncer-scan/verifying-high-risk-cards/android-integration-guide#using)

- In the `local.properties` file in your project-level directory,  add the following code to the file. Replace `YOUR_APP_KEY` with your card scan API key.

```
    SCAN_CARD_API_KEY=YOUR_API_KEY
```

To build this project, you require:

- Android Studio 4.1.0  or higher
- Gradle 6.5 or higher

### Top features

- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- Kotlin coroutines with [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html)
- Dependency injection with [Dagger-Hilt](https://dagger.dev/hilt/)
- API request with [Retrofit](http://square.github.io/retrofit) and [Moshi](https://github.com/square/moshi) for JSON serialisation
- CI  with [Github actions](https://github.com/features/actions)
- Code coverage with [jacoco](https://github.com/vanniktech/gradle-android-junit-jacoco-plugin) with reports uploaded to [codecov](https://codecov.io/gh/KryptKode/CardInfoFinder/)
- Code lint check with [Ktlint](https://github.com/pinterest/ktlint) using a [gradle plugin](https://github.com/JLLeitschuh/ktlint-gradle)
- Static code analysis with [detekt](https://github.com/detekt/detekt)
- Dependency management with [buildSrc](https://gradle.org/kotlin/)  (Kotlin DSL)
- Dependency updates with [buildSrcVersions](https://jmfayard.github.io/refreshVersions/)
- [Git hooks](https://github.com/KryptKode/CardInfoFinder/tree/master/scripts/git-hooks) to perform ktlint, detekt and lint checks before committing

### Architecture

The app follows the clean architecture concept in the most minimal way appropriate for the current state of the app. There are three layers in the app. The data layer, the domain and the presentation. 
On the data layer, an API request is done with the [Retrofit](http://square.github.io/retrofit) library. Models are defined for the expected response. A mapper class is present to convert the response to the domain layer model. 
The domain layer contains the model class and the use case for getting card info. It defines a state class to notify the presentation layer of the loading, success and error states. In the app, asynchronous operations are carried out with coroutines. The domain layer defines a class to abstract the different dispatchers.
The presentation layer is implemented with `MVVM` using `ViewModel` and `LiveData`. A view state data class is defined. The fragment observes the view state which is wrapped in a `LiveData`. The view is a `Fragment`. The card number is passed to the `ViewModel`, which then executes the use case and a reducer, which receives the old state and the results (error, failure, or loading) creates a copy of the new state. This new state is posted to the view. 

The app uses the card scan sdk to scan card details. The [Activity Results API](https://developer.android.com/training/basics/intents/result) is used to define the contact used to request and receive results of the scan. 

### Libraries Used

- [Material Components](https://github.com/material-components/material-components-android/) - comes with ready made material UI view components
- [CardScan API](https://docs.getbouncer.com/bouncer-scan/credit-card-ocr/android-integration-guide) - for scanning card details
- [Constraint Layout](https://developer.android.com/reference/android/support/constraint/ConstraintLayout) - it enables creation of layouts flat view hierarchies
- [Retrofit](http://square.github.io/retrofit) for REST api communication, it is actively developed and stable
- [Moshi](https://github.com/square/moshi) for JSON serialisation and deserialisation, it's lightweight, stable and works well with Retrofit
- [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - for  asynchronous concurrency because it's easy to use and comes out of the box with kotlin
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - to manage UI data in a lifecycle conscious way, surviving configuration change
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - to hold and observe UI state in a lifecycle aware way in the app
- [Navigation Architecture Component](https://developer.android.com/guide/navigation/navigation-getting-started) - for easy navigation, especially with fragments no need to interact with the fragment manager
- [Glide](https://github.com/bumptech/glide) - for image loading and caching with smooth scrolling. It is actively developed and stable.
- [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - to interact easily with views from XML through an auto-generated binding class.
- [LeakCanary](https://square.github.io/leakcanary/getting_started/) - to detecting memory leaks in development
- [Mockk](https://github.com/mockk/mockk) for mocking in tests.
- [Dagger-Hilt](https://dagger.dev/hilt/) for dependency injection
- [Kotlin Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) for concurrency
- [Turbine](https://github.com/cashapp/turbine) for testing flow
- [Ktlint gradle plugin](https://github.com/JLLeitschuh/ktlint-gradle) for code lint checks
- [Detekt](https://github.com/detekt/detekt) for static code analysis

## Testing

Testing is done with Junit4 testing framework, and with Google Truth for making assertions. [Mockk](https://github.com/mockk/mockk) is used to provide mocks in some of the tests. The tests run on the CI and the code coverage report is generated by [jacoco](https://github.com/vanniktech/gradle-android-junit-jacoco-plugin) can be tracked [here](https://codecov.io/gh/KryptKode/CardInfoFinder/). Instrumentation tests are written with Espresso

### Extras

The Gradle script uses Kotlin Gradle DSL ([buildSrc](https://gradle.org/kotlin/) ) which brings Kotlin's rich language features to Gradle configuration. The project also uses detekt to detect code smells and ktlint to enforce proper code style. Github actions handle continuous integration and run detekt, ktlint, lint and unit tests concurrently.  A pre-commit git hook verifies the project's code style before committing code.  Test coverage reports are uploaded to [codecov](https://codecov.io/gh/KryptKode/CardInfoFinder/). 

**Other things to do**

- Setup UI test CI with Firebase test laba
- Improve Unit test coverage
