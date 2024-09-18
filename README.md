MyMarket 🛒

MyMarket is a modern Android application designed to provide a seamless shopping experience. Built with cutting-edge technologies, MyMarket delivers a highly responsive, user-friendly interface, ensuring smooth navigation and efficient performance. This application features real-time product updates, shopping cart management, and the ability to favorite items, all while ensuring persistence and offline capabilities.

Features ✨

Modern UI with Jetpack Compose: Fast, responsive, and declarative UI using Jetpack Compose for a smooth and native user experience.
Real-Time Data Updates: Fetch and display real-time data from APIs with the help of Retrofit.
Room Database Integration: Persistent local storage for seamless offline support and quick data retrieval.
Favorite Items: Easily mark products as favorites with real-time updates from the local Room database.
Shopping Cart Management: Add, remove, and track products in your cart with live updates.
Dark Mode: Enjoy a visually pleasing experience in both light and dark themes.

Tech Stack 🛠

Kotlin: The core programming language used to build the application, offering a modern and concise codebase.
Jetpack Compose: A new UI toolkit that allows you to write beautiful and responsive UI code in a declarative manner.
Flow & Coroutine: Used for handling asynchronous programming, enabling efficient background tasks and reactive programming.
Retrofit: A powerful library for making API requests and handling responses seamlessly.
Room: Local database management with Room to handle offline capabilities and persistent data storage.
DataStore: Securely storing user preferences with Jetpack's DataStore for efficient data persistence.
Dependency Injection (Hilt): For managing dependencies efficiently and ensuring a scalable architecture.
Coil: For image loading and caching, ensuring that product images load smoothly.
ViewModel: For lifecycle-aware data handling, ensuring smooth screen transitions and state management.

Architecture 🏗

MyMarket follows the MVVM (Model-View-ViewModel) architecture pattern to separate concerns and ensure easy scalability and maintainability.
Model: Handles the business logic and data sources (API, local database).
View: The UI built with Jetpack Compose.
ViewModel: Mediates between the View and Model, exposing state to the UI via Flow and LiveData.

Setup and Installation
Clone this repository:
git clone https://github.com/tolganacar/android-kotlin-english-cards.git
Open the project in Android Studio.
Sync the project with Gradle files.
Run the project on an emulator or physical device.
