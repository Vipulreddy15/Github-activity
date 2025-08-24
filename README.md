## GitHub User Activity Tracker

A simple **Java command-line application** that fetches and displays recent GitHub user activity using the **GitHub REST API**.  
This project demonstrates working with APIs, JSON parsing, and handling HTTP requests in Java.

---

Features
- Fetches recent public activity of any GitHub user
- Supports multiple event types (Push, Issues, Stars, etc.)
- Lets you choose how many events to display
- Simple and easy-to-run CLI program

---

Tech Stack
- **Java 11+**
- **HTTP Client (java.net.http)**
- **org.json** library for parsing JSON

---



Example Output:
Enter the GitHub username: octocat
Enter how many activities to show: 3
Status code: 200
- Pushed 2 commits to octocat/Hello-World
- Opened an issue in octocat/Spoon-Knife
- Starred octocat/Hello-World

