# Data Model

## Overview

---

The application allows users to choose the country, create trips and organize their plan and plans will be sorted by time.

## Relationships 🧩

---

User (1) ───── N Trip <br />
Trip (1) ───── N PlannedItem<br />
Trip (N) ───── N Country <br />

## Trip ✈️

---

Represents the user's plan, such as vacation.

### Fields

- id
- name
- city

## Country 🌍

---

Represents all countries in the world, and it is a fix data. Users can not add new country, delete a country or update a country.

### Fields

- id
- name

## Trip_country

---

is used to make many-to-many relationship between trip entity and country entity.

### Fields

- id
- tripId
- countryId

## PlannedItem 📅

---

Represents an item in itinerary.

### Types

- activity
- meal
- accommodation

### Fields

- id
- type
- description
- locationText
- locationMapUrl (optional)
- cost (optional)
- dateTimeStart (required)
- dateTimeEnd (optional)
- tripId

## Design Decisions 🧠

---

### Unified PlannedItem

instead of having separate Activity, Meal and Accommodation tables, the application uses a single PlannedItem entity with a type field.

Benefits:
- Easier time-based sorting
- Simpler backend logic
- More scalable
- Avoid more SQL query for better performance

### Required dateTimeStart

the application will be time-based sorted. It makes user easier to organize their trip.



