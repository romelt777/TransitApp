# TransitApp — Android Transit Application

![Kotlin](https://img.shields.io/badge/Kotlin-1.9-orange)
![Android](https://img.shields.io/badge/Platform-Android-blue)
![Retrofit](https://img.shields.io/badge/Retrofit-API-yellow)
![Local Storage](https://img.shields.io/badge/Storage-Internal%20Storage-green)

TransitApp is a Kotlin-based Android application that displays nearby bus routes and service alerts using GTFS transit data.

---

## Overview

The app allows users to:

- View nearby bus routes on a map
- Search for specific routes
- Save favorite routes locally
- Receive local notifications for route alerts

Transit data is fetched from a public API and rendered dynamically based on the user’s location.

---

## Implementation Details

- Network requests handled asynchronously using **Retrofit**
- User location retrieved via Android location services
- Favorite routes persisted using internal storage
- Local notifications triggered for GTFS alert updates
- Runtime permissions handled for location and notifications
- Map integration for displaying nearby routes

---

## Tech Stack

- Kotlin
- Android SDK
- Retrofit
- GTFS transit data
- Internal storage
- Android Maps API

---

## Screenshots

![Location Permission](SCREENSHOTS/REQUEST_PERMISSIONS.png)
![Map with User Routes](SCREENSHOTS/HOME_FRAGMENT_WITH_USER_ROUTES.png)
![Search Routes](SCREENSHOTS/SEARCHING_ROUTES.png)
![Saved Routes](SCREENSHOTS/ROUTES_ADDED.png)
![Local Alerts](SCREENSHOTS/ALERTS.png)
