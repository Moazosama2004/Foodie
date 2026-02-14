# Foodie - Your Personal Comfort Food Companion
<img width="4800" height="3600" alt="Mockup 2" src="https://github.com/user-attachments/assets/ec437273-c7f7-4834-9bcd-356e7e56b5c7" />

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

| Screenshot 1 | Screenshot 2 | Screenshot 3 |
|--------------|--------------|--------------|
| <img width="300" src="https://github.com/user-attachments/assets/7b820967-54cf-4a32-af5f-381259269451" /> | <img width="300" src="https://github.com/user-attachments/assets/dbbec200-b41e-4960-945a-f302da759caa" /> | <img width="300" src="https://github.com/user-attachments/assets/cb87ae3a-8f6d-4e1d-8445-982a4098413f" /> |

| Screenshot 4 | Screenshot 5 | Screenshot 6 |
|--------------|--------------|--------------|
| <img width="300" src="https://github.com/user-attachments/assets/1f134754-0327-416d-8ce7-31bff80a1dce" /> | <img width="300" src="https://github.com/user-attachments/assets/e1e83432-01a6-4e8d-a681-7c6e26d43139" /> | <img width="300" src="https://github.com/user-attachments/assets/df9bcb07-9a17-49c4-8b33-cafc2a258c1f" /> |

| Screenshot 7 |
|--------------|
| <img width="300" src="https://github.com/user-attachments/assets/298d3482-411a-4a45-808b-6d3f6ac64e6d" /> |

