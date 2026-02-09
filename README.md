# TransitApp — Android Transit Application

![Kotlin](https://img.shields.io/badge/Kotlin-1.9-orange)
![Android](https://img.shields.io/badge/Platform-Android-blue)
![Retrofit](https://img.shields.io/badge/Retrofit-API-yellow)
![Local Storage](https://img.shields.io/badge/Storage-Internal%20Storage-green)

TransitApp is a Kotlin-based Android app that displays nearby bus routes and alerts using GTFS transit data. The app demonstrates **API integration, local storage, asynchronous data handling, and user notifications** on a mobile platform.

---

## Overview

TransitApp allows users to:
- View nearby bus routes on a map
- Search and save favorite routes
- Receive local notifications for route alerts
- Persist saved routes using internal storage

This project emphasizes **mobile backend integration and data-driven features** rather than frontend complexity.

---

## Core Features

- Fetches transit data asynchronously via **Retrofit API**
- Displays user location and nearby routes on an interactive map
- Enables users to **save favorite routes locally**
- Sends **local notifications** for alerts using GTFS schedule updates
- Handles Android permissions and lifecycle for location and notifications

---

## Technology Stack

- Kotlin / Android SDK  
- Retrofit for API requests  
- GTFS public transit data  
- Internal storage for user data  
- Local notifications for alerts  
- Android Maps integration  

---

## Screenshots

### User Interface & Functionality
![Location Permission](SCREENSHOTS/REQUEST_PERMISSIONS.png)
![Map with User Routes](SCREENSHOTS/HOME_FRAGMENT_WITH_USER_ROUTES.png)
![Search Routes](SCREENSHOTS/SEARCHING_ROUTES.png)
![Saved Routes](SCREENSHOTS/ROUTES_ADDED.png)
![Local Alerts](SCREENSHOTS/ALERTS.png)

---

## Key Takeaways

- Mobile development with Kotlin and Android SDK  
- API integration and asynchronous network handling  
- Local data persistence and notifications  
- Demonstrates breadth beyond web-based backend projects
