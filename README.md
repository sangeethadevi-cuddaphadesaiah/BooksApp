# Books App

A simple books Android MVVM app built with Kotlin, Retrofit, RxJava, Hilt, and DataBinding.

## Features
- Fetch books from OpenLibrary API  
- Display book details in a BottomSheet  
- Pull-to-refresh and error handling
- States: Loading / Success / Error with retry.
- DI: Hilt. Async: RxJava (Single).
- Tests: Unit tests for ViewModel included.
- Note: UI implemented with XML/RecyclerView. 

##  Tech Stack
- **Kotlin**
- **Retrofit**
- **Hilt (DI)**
- **RxJava**
- **ViewModel + LiveData**
- **DataBinding + RecyclerView**

## App Flow Diagram
 A[MainActivity] --> B[BookListFragment]
 B -->|Observes| C[BookListViewModel]
 C -->|Fetches data from| D[BooksRepository]
 D -->|Makes API call| E[BooksApiService]
 E -->|Returns JSON| F[BookUiMapper]
 F -->|Maps to UI model| C
 C -->|Updates LiveData| B
 B -->|Displays list in RecyclerView| G[BooksAdapter]
 G -->|On item click| H[BookDetailBottomSheet]

## Architecture Overview
UI (Fragment, Activity)
   ↑ observes
ViewModel (BookListViewModel)
   ↑ depends on
Repository (BooksRepositoryImpl)
   ↑ calls
Remote Data Source (BooksApiService)
   ↑ maps with
Data Models (BooksResponse, Work, BookUiModel)

##  How to Run
1. Clone this repository:
   git clone https://github.com/<your-username>/BooksApp.git
   
Open the project in Android Studio.
Build and run on a device or emulator.

 ## Testing
Unit tests and UI tests for ViewModel, Repository, and UI components.

 ## Security
ProGuard enabled for release builds

Safe network communication via HTTPS

