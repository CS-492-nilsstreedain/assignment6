# Assignment 6: Mobile Treasure Hunt
## Introduction
Successfully create an Android project in Android Studio that utilizes Jetpack Compose and Kotlin to create an app that let's the user search for treasure in the real world. This is Geocaching Links to an external site. combined with a Treasure Hunt.

Important:  Make sure to consult the Exploration: Sensors and Location Services before starting this assignment.

This is based on a story I read once. A girl is visiting her late Grandfather's farm and while cleaning out a shed she finds a piece of paper. It's a clue written by her Grandfather. Solving the clue led to some other place in the house or yard, where she finds another clue that leads to a different place. Eventually she completes the treasure hunt and finds a piece of candy. One more game with her Grandfather..

This is the tech version of that story. One clue leads to another and eventually the "treasure" is found. Only one clue is presented at a time, and location services (gps) are used to tell if the clue is solved.

Demonstrate proficiency of prior material, and challenge yourself to demonstrate how to apply new concepts to meet functional requirements. Practice applying the concepts of location services, maps, permissions, forms, views and navigation, lists, and asynchronous programming. Enhance your application with analytics, crash reporting, accessibility, internationalization, debugging and automated testing.

## Tasks
- Note:  You should ALWAYS start with a new project in this course.
    - Choose the "Empty Activity" template for your new project.
    - Starting from anything else may result in hard-to-debug errors.
- This assignment leverages material from most of the Modules of the course, and especially Module 8 - Sensors and Location Services.
- The app will follow the general requirements described below, but you have a fair amount of latitude in how it is implemented, especially in regards to names of things and the visual presentation of information.
    - If you would like to deviate further than this, you should ask the Instructor for permission.
- The app must meet the following requirements:
    - ALL information for the treasure hunt should be loaded from a resource file.
    - The app should present a permissions page first, for the user to approve the various permissions required to run the game.
        - Make sure to read through this page:  https://developer.android.com/develop/sensors-and-location/location/permissions Links to an external site.
    - The app will present a Start page that contains the title, rules for the game, and a start button. The rules should be scrollable to present all information.
    - The Start Button takes you to the "Clue" page. There are (at least) two clues to solve in the app. The Clue Page contains:
        - A textual clue to a real-world location.
            - Example Clue: "Grab some things for dinner but keep an eye on the time" - Real-world Location:  An open-air Market with a large clock.
        - A hint button
            - Gives further helpful info. Example Hint: "Fish sounds good for dinner" (perhaps the Market is known for several fish-sellers).
        - An animated count-up timer. Shows the time elapsed since the Start Button was pressed.
        - A "Found It!" button. User presses this when they think they are at the location referenced by the clue.
            - Note:  The GPS receiver in your phone is typically accurate to about 5 meters under open sky. Buildings, trees, etc will degrade this accuracy. You should make allowances for this in your app.
        - A quit button that exits the treasure hunt and takes the user to the Start Page.
    - The "Found It!" button will cause the app to check if the user has indeed found the correct location:
        - How to know if the user is near enough to the correct location? Use the Haversine formula with Location Updates. Look up "Haversine formula for Kotlin" to find the code/algorithm.  Here is an implementation Links to an external site..
        - If not the correct location, a popup informs the user. The timer continues to run.
        - If the user IS at the correct location:
            - If this IS NOT the final Clue (there are more clues to solve), then the "Clue Solved" page is presented. The page will have the following:
                - The count-up timer will be PAUSED while on the Clue Solved page, but the elapsed time since Start was pressed will still be shown. This allows the user to take time to read the info about the location.
                - More info about the location. Example: "The Pike Place Market was founded in 1907..."
                - A Continue button which will take the user back to the Clue page and present the next clue.
            - If this IS the final Clue (no more clues to solve), then the "Treasure Hunt Completed" page is presented.
        - The "Treasure Hunt Completed" page presents:
            - A congratulatory message
            - The total elapsed time since the Start button was pressed (and of course the timer is stopped).
            - Info about the final Clue location.
            - A home button that takes the user to the Start Page.
        - Structurally, your app should be configured similarly to Assignment 4, using ViewModels and StateFlow.
        - When you have implemented the app in accordance with requirements, run it on an Android emulator and record your screen per the instructions below.
            - Testing locations using the emulator is a bit tricky. Here are some resources:  
                - https://developer.android.com/studio/run/emulator-extended-controls Links to an external site.
                    - Use Single Points to set the Emulator location (Routes can also be used, but you have to remember to speed them up enough or they will take too much time for the video).
                    - Single points
                    - In the Single points tab, you can use the Google Maps webview to search for points of interest, just as you would when using Google Maps on a phone or in a browser. When you search for (or click) a location in the map, you can save the location by selecting Save point near the bottom of the map. All your saved locations are listed on the right side of the Extended controls window.
                    - To set the emulator's location to the location you have selected on the map, click the Set location button near the bottom right of the Extended controls window.
                    - Android — Test Location Updates With Mock Locations (fake GPS data) in emulators and real devices Links to an external site.
                    - Make sure to show all screens in the app. This means you will need to complete the treasure hunt using the Emulator and Extended controls window.
                - Once recorded, upload your demo video as well as the code folder (details below) per the instructions provided

The app has to run without crashing.

## Notes
This is meant to be a fun, but also extremely useful project. Accessing location services and geofencing are an important use-case for mobile devices.

Also, please be aware that when writing code, naming files, etc. it is crucial that everything be to specification.  Please know that any deviation from the instructions on any work in this course may lead to a deduction.

## What to turn in
When complete, submit:
1. A video recording of your computer screen that includes all of the following:
    1. Code editor displaying a part of the app’s file structure to demonstrate conformance with the required package structure listed above.
    2. Do a quick scroll through the entirety of the code in:
        1. MainActivity, ViewModel derived, Navigation code, Location Services code, and your composable UI elements.
        2. Have a visible comment block in all code files in the format shown in the previous Assignments
    3. The app already running in the emulator.
    4. A display of interaction to include an entire Treasure Hunt sequence as outlined above.
2. A .zip file of your project's java directory with a name formatted like LNAME_FNAME.zip (e.g., SCOVIL_RANDY.zip)

**This video for this assignment only needs to be long enough to show all of the required elements.**

Use the same format demo used in Assignment 4.

## Grading criteria
Be sure to consult the rubric for expectations regarding your submission.
