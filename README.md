# Personal Event Planner – Task 4.1

## ✨ Features

- 📅 Event Management: Create new events with a Title, Category (Work, Social, Travel), Location, and Date/Time
- 📋 Real-time Dashboard: A central list of all upcoming events, automatically sorted by date (soonest first).
- 💾 Room Persistence: Uses the Room Database to store data locally; your schedule persists even after the app is closed or the device is restarted.
- ⚓ Modern Navigation: Built using the Jetpack Navigation Component with a single-activity/multiple-fragment architecture.
- 🔵 Custom UI: Features a custom Light Blue Bottom Navigation Bar to switch between the Event List and Add Event screens.
- 🛑 Input Validation:
  - Ensures "Title" and "Date" fields are not empty before saving.
  - Logic Check: Prevents users from picking a date in the past for new events.
- 💬 User Feedback: Instant visual confirmation via Snackbars for successful saves, updates, or deletions.
- ✏️ CRUD Operations: Full capability to Create, Read, Update, and Delete events at any time.


## 🚀 How to Run the Project

### Prerequisites
- 🛠️ Android Studio (latest version)
- 📱 Android SDK (API 24 or higher)
- ☕ Java 11+

### Steps
1. 📥 **Clone the repository**  
   `git clone https://github.com/your-username/EventPlannerApp.git`
    

3. 📂 **Open in Android Studio**  
   `File → Open` → select the cloned folder

4. 🔄 **Sync Gradle**  
   Click `File → Sync Project with Gradle Files` (or the 🐘 elephant icon)

5. 🏗️ **Build the project**  
   `Build → Make Project` (Ctrl+F9)

6. ▶️ **Run the app**  
   - Connect a physical device (USB debugging enabled) or start an emulator (API 24+)  
   - Click the green **Run** button (▶️)

7. 🎮 **Use the app**
   
1.Add Event: Navigate to the "Add Event" tab, fill in the details, and pick a future date using the Date/Time picker.
2.View List: Go to the "Events" tab to see your schedule sorted chronologically.
3.Edit: Tap on any existing event card to modify its details and click "Update".
4.Delete: Click the trash icon on any event card to remove it from your database.
5.Validation: Try saving an empty event or a past date to see the error feedback in action.
