# OUcare – Hospital Appointment Booking System

OUcare is a desktop application that allows patients to book medical appointments with doctors across different hospital departments, while providing administrators with full management tools for departments, doctors, and appointments.

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 17 |
| Desktop UI Framework | JavaFX 17.0.1 |
| UI Markup | FXML |
| Database | MySQL 8.0 |
| Database Driver | MySQL Connector/J 8.0.27 |
| Build Tool | Apache Maven 3 |
| Testing | JUnit 5 (Jupiter) |
| Security | AES encryption for stored credentials |

---

## Features

### Authentication
- User registration with real-time password validation (minimum 8 characters, uppercase, lowercase, digit, and special character required)
- Email-based login with AES-encrypted passwords
- Role-based access control – each role loads its own dedicated UI on login

### Customer / Patient
- Browse available medical departments
- Select a doctor within a chosen department
- Pick an appointment date (future dates only) and available time slot
- Automatic conflict detection – prevents double-booking on the same slot
- View personal appointment history with doctor name and department

### Doctor
- View all scheduled appointments with patient details
- Profile display with department assignment

### Administrator
- **Department Management** – create, update, and delete hospital departments
- **Doctor Management** – register new doctors, edit profiles (name, email, phone, address, birthday, department), delete doctors, and search/filter by name, department, email, or phone
- **Patient Management** – view all registered patients and search by name
- **Appointment Management** – view all system appointments and filter by date, department, doctor, or patient name

---

## Project Structure

```
oucare/
├── pom.xml                         # Maven build configuration
└── src/main/
    ├── java/com/example/oucare/
    │   ├── HelloApplication.java   # Application entry point
    │   ├── Controllers/            # JavaFX controllers (Login, Register, Admin, Booking)
    │   ├── model/                  # Data models (user, ticket, department, time, roles)
    │   ├── services/               # Business logic (UserService, DoctorService, TicketService, …)
    │   └── config/
    │       └── JdbcUtils.java      # Database connection configuration
    └── resources/com/example/oucare/
        ├── login.fxml              # Login screen
        ├── register.fxml           # Registration screen
        ├── hello-view.fxml         # Admin dashboard (5 tabs)
        ├── doctor.fxml             # Doctor view
        └── customer.fxml           # Patient booking view
```

---

## Getting Started

### Prerequisites
- Java 17 or later
- Maven 3.6 or later
- MySQL 8.0 running locally

### Database Setup
1. Create a MySQL database named `oucare`.
2. Update the connection settings in `src/main/java/com/example/oucare/config/JdbcUtils.java` if your MySQL credentials differ from the defaults.

### Run the Application
```bash
mvn clean javafx:run
```

---

## User Roles

| Role | Description |
|------|-------------|
| `ADMIN` | Full access – manage departments, doctors, patients, and appointments |
| `DOCTOR` | View personal appointment schedule |
| `CUSTOMER` | Book appointments and view appointment history |
