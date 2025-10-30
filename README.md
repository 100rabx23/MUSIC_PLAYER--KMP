# 🎶 Kotlin Multiplatform Music Player App
***
A **cross-platform Music Player application** built with **Kotlin Multiplatform (KMP)** and secured using **GitLab OAuth Authentication**.  
Inspired by modern music apps (like Spotify/JioSaavn), this project demonstrates how KMP + Compose Multiplatform can deliver **production-ready apps** for **Android & iOS** with a shared codebase.  

<img src="https://www.animatedimages.org/data/media/562/animated-line-image-0184.gif" width="1920" />  
A decentralized web application that rewards users with cryptocurrency for staying focused using their webcam activity.


....
## 🚀 Features  

- 🎼 **Music Player**: Play, pause, skip tracks with smooth playback  
- 📂 **Playlists & Avatars**: Manage playlists and user profiles  
- 🎨 **Modern UI/UX**: Spotify-style interface with dashboards, player screen, and smooth navigation  
- 🔐 **Authentication**: Secure login with **GitLab OAuth**  
- 🌍 **Cross-Platform**: Shared business logic for Android & iOS using **Kotlin Multiplatform**  
- ⚡ **Compose Multiplatform**: Native UI on Android (Jetpack Compose) and iOS (SwiftUI)  

---

## 🛠️ Tech Stack  

- **Kotlin Multiplatform (KMP)**  
- **Compose Multiplatform** for UI  
- **Jetpack Compose** (Android) & **SwiftUI** (iOS)  
- **GitLab OAuth Authentication**  
- **Android Studio Giraffe/Hedgehog**  
- **Gradle Kotlin DSL**  

---

## 📂 Project Structure  

```music-player-kmp/
│── androidApp/ # Android-specific code (Jetpack Compose UI)
│── iosApp/ # iOS-specific code (SwiftUI integration)
│── shared/ # Shared Kotlin Multiplatform module
│ ├── auth/ # GitLab OAuth integration
│ ├── data/ # Data & networking logic
│ ├── player/ # Music playback logic
│ └── ui/ # Shared UI components
│── build.gradle.kts # Project build config
│── settings.gradle.kts
```

---

## 🔑 GitLab OAuth Setup  

1. Go to your **GitLab account → Settings → Applications**  
2. Create a new OAuth application with:  
   - Redirect URI: `myapp://auth`  
   - Scopes: `read_user`  
3. Copy **Client ID** and **Client Secret**  
4. Add them in your `local.properties` (not in version control):  

```properties
GITLAB_CLIENT_ID=your_client_id
GITLAB_CLIENT_SECRET=your_client_secret



'''
Still under Development!!...

