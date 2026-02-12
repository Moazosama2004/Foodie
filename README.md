
# 🍽️ Foodie – Android Meal Planner App

Foodie is a modern Android application built using **MVP Architecture** and **RxJava3** that allows users to explore meals, manage favorites, and plan meals using a calendar-based system.

The app supports both **Guest Mode** and **Authenticated Mode** with remote synchronization.

---

# 📱 Features

## 🔐 Authentication
- Firebase Authentication
- Guest Mode support
- Login required for favorites & planning

## 🌍 Meal Browsing
- Browse meals by categories
- View meal details
- View meal images

## 🔎 Search
- Search meals dynamically

## ❤️ Favorites
- Add meals to favorites
- Remove meals from favorites
- Sync favorites with remote database
- Local caching support

## 📅 Meal Planning
- Plan meals by date
- View meals per selected day
- Delete planned meals
- Sync between local & remote

---

# 🏗️ Architecture

This project follows **MVP (Model - View - Presenter)** architecture with clear separation of responsibilities.


### Responsibilities

- **View** → UI only (Fragments / Activities)
- **Presenter** → Business logic & Rx handling
- **Repository** → Data source abstraction
- **Local** → Room / SharedPrefs
- **Remote** → Firebase / API

---

## 🛠️ Tech Stack

- ✅ Java
- ✅ MVP Architecture
- ✅ RxJava3
- ✅ Firebase Authentication
- ✅ Firebase Firestore
- ✅ Room Database
- ✅ RecyclerView
- ✅ ViewBinding
- ✅ Material Design

---

## 🔄 Reactive Programming (RX-uesd)

All asynchronous operations are handled using **RxJava3**:

- `Single`
- `Completable`
- `CompositeDisposable`
- Proper thread management:
  - `Schedulers.io()`
  - `AndroidSchedulers.mainThread()`

---

## 👤 User Modes

### Guest Mode
- Can browse meals
- Cannot add favorites
- Cannot plan meals
- Alert dialog prompts login

### Logged-in Mode
- Full access to:
  - Favorites
  - Calendar planning
  - Remote synchronization

---

## 📂 Project Structure

```bash
com.example.foodie
│
│
│── config
│   ├── db
│   ├── network
│   └── networkconnection
│
│
│── core
│
│
├── data
│   ├── home
│   ├── fav
│   ├── calender
│   └── core
│
├── presentation
│   ├── home
│   ├── fav
│   ├── calender
│   ├── search
│   └── details
│
├── utils
│   ├── firebase
│   ├── services
│   └──sharedPrefrences

```




---

## ⚙️ How to Run

1. Clone the repository

```bash
git clone https://github.com/your-username/foodie.git
```

2️⃣ Open Project in Android Studio

Open Android Studio

Select Open Project

Choose the cloned repository folder

3️⃣ Configure Firebase

Go to Firebase Console

Create a new Firebase project

Add an Android app using your package name:

```
com.example.foodie
```


Download the google-services.json file

Place it inside:
```
app/google-services.json
```



4️⃣ Sync Gradle

Click Sync Now when prompted
OR

Go to:

File → Sync Project with Gradle Files

5️⃣ Run the Application

Connect a real device OR start an emulator

Click Run ▶



✅ Requirements

Android Studio Hedgehog or newer

Minimum SDK: 21+

Java 8+

Internet connection (for remote data & Firebase)

🧪 Test Accounts

You can:

Create a new account inside the app
OR

Use Guest Mode (limited features)

