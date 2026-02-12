# Foodie - Your Personal Comfort Food Companion

Foodie is a modern Android application designed to help users discover, save, and plan their favorite meals. Whether you're looking for a quick recipe or planning your weekly menu, Foodie provides a seamless and beautiful user experience.

## ✨ Features

- **User Authentication:** Secure sign-up and login with Email/Password and Google Sign-In. A guest mode is also available for anonymous browsing.
- **Meal Discovery:**
    - A "Meal of the Day" feature on the home screen to inspire your next cooking adventure.
    - Browse a list of popular meals.
- **Powerful Search:**
    - Search for meals by **Category**, **Ingredient**, or **Country**.
    - Dynamic filtering and search-as-you-type functionality.
- **Detailed Meal View:**
    - View comprehensive meal details including ingredients and step-by-step instructions.
    - Watch embedded YouTube videos for a visual cooking guide.
- **Personalized Experience:**
    - **Favorites:** Save your favorite meals for quick access.
    - **Meal Planner:** Add meals to a calendar to plan your food for the week.
    - **Profile Management:** View your profile information and sign in or out.
- **Modern & Responsive UI:**
    - A clean, intuitive, and modern user interface built with Material Design components.
    - Smooth animations and responsive layouts.

## 🛠 Tech Stack & Architecture

- **Language:** **Java**
- **Architecture:** **Model-View-Presenter (MVP)**
    - **Model:** Data layer consisting of repositories that handle data from both network (Retrofit) and local (Room) sources.
    - **View:** Activities/Fragments responsible for displaying the UI and passing user actions to the Presenter.
    - **Presenter:** Handles the business logic and acts as the bridge between the View and the Model.
- **Core Libraries:**
    - **UI:** AndroidX (AppCompat, ConstraintLayout, RecyclerView, CardView), Material Components, ViewBinding
    - **Navigation:** AndroidX Navigation Component
    - **Networking:** Retrofit & Gson
    - **Asynchronous Programming:** RxJava 3
    - **Image Loading:** Glide
    - **Local Storage:** Room Persistence Library & SharedPreferences
    - **Backend & Authentication:** Firebase (Authentication, Firestore), Google Sign-In
    - **Video Playback:** android-youtube-player

## 🚀 Setup and Installation

1.  **Clone the repository.**
2.  **Firebase Setup:**
    - Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project.
    - Add an Android app to the project with the package name `com.example.foodie`.
    - Download the `google-services.json` file and place it in the `app/` directory (`C:/ITI - Foodie/app/`).
3.  **Open in Android Studio:**
    - Open the project in Android Studio.
    - Let Gradle sync the dependencies.
4.  **Build and Run:**
    - Run the app on an emulator or a physical device.

## 📸 Screenshots

*(Add your app screenshots here to showcase the beautiful UI!)*
