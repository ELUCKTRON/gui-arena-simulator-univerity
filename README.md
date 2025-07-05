# ⚔️ Arena Simulation Game

Welcome to the **Arena Simulation Game** – a thrilling, real-time battle simulator developed in Java as part of a **Semester 3 Programming Technology** university course! 🧠💻

In this interactive simulation, mighty **Orcs**, fearsome **Dragons**, and a lone **Main Character** battle it out in an epic arena. Only the strongest will survive! The game showcases real-time combat using multithreading, GUI interactions, and strategic AI behavior.

---

## 🎮 Gameplay Overview

- 👤 **Your Role**: You control the simulation setup – choose characters, start the battle, and witness the carnage!
- 🧙 **Characters**:
  - **Orcs**:
    - 🪓 **Fighter** – Balanced attacker.
    - 🛡️ **Defender** – High health, low damage.
    - ⚔️ **Berserker** – Low health, high damage.
  - **Dragons**:
    - 🔥 **Red Dragon** – Moderate HP, powerful bursts.
    - 🐉 **Black Dragon** – Tanky with consistent damage.
  - 👑 **Main Character** – A legendary warrior with high stats and durability.
- 🤖 **AI Logic**:
  - Characters move and attack in real time using Random behavior.
  - They target only enemies from other factions (unless free-for-all mode is enabled).
  - All combat behavior is governed by values in the `SimulationConfig` file.

---

## 🖥️ Features

- 🧵 **Multithreading**  
  Each character is controlled via a separate thread, simulating real-time battle mechanics using Java's `ScheduledThreadPoolExecutor`.

- 🧠 **Battle Engine**  
  - Characters auto-attack based on faction and proximity.
  - Health and damage stats are dynamically calculated.
  - Eliminated characters are automatically removed from play.

- 📊 **Real-Time Health Bars**  
  Each fighter has a dynamic health bar displayed via `JProgressBar` in the GUI.

- 📜 **Combat Log**  
  Live updates show which character attacked whom, how much damage was dealt, and who fell in battle.

- 🕹️ **Interactive GUI (Swing)**  
  Simple and intuitive user interface to start simulations and view real-time stats.

---

## ⚙️ Technologies Used

- ☕ Java (JDK 17+)
- 🖼️ Swing (GUI)
- ⏱️ ScheduledThreadPoolExecutor (Multithreading)
- 📦 Maven (Project management and build tool)

---

## 🏁 How to Play

- 🎯 Download the latest executable JAR from the [Releases] page  
  **or**
- 🛠️ Clone and build the project using Maven to generate the executable

Once the JAR is ready, just double-click it or run via command line to enjoy the simulation!

---

## 🎓 Educational Context

This project was developed as a university assignment for the **Programming Technology** course in **Semester 3**. It demonstrates:

- ✅ Object-Oriented Design Principles  
- ✅ Real-time Simulation with Thread Safety  
- ✅ GUI Development with Java Swing  
- ✅ Clean Architecture and Modular Codebase  

---

## 👥 Author

Developed with ❤️ by **Saeed Khanloo**.  
We hope this game brings you as much joy as it brought us during development!

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

Feel free to fork, play, contribute, and learn! 🧠🎮🔥
