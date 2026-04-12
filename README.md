# 🚀 Drive Backend (Google Drive Clone)

A Spring Boot backend application that provides secure file storage functionality similar to Google Drive.

## ✨ Features
- 🔐 Session-based Authentication
- 📁 File Upload & Download
- 🛡️ Spring Security Integration
- 📦 RESTful APIs

## 🛠️ Tech Stack
- Java
- Spring Boot
- Spring Security
- Maven

## 📂 Project Structure
controller → API endpoints  
service → business logic  
repo → database access  
entity → database models  

com.cfs.drive_backend
│
├── config
│ └── SecurityConfig
│
├── controller
│ ├── AuthController
│ └── FileController
│
├── service
│ ├── UserService
│ └── FileService
│
├── repository
│ ├── UserRepository
│ └── FileRepository
│
├── entity
│ ├── User
│ └── FileEntity
│
├── security
│ └── PasswordConfig
│
└── dto
 ├── LoginRequest
 └── RegisterRequest

## 📬 API Endpoints

### Auth
- POST `/api/user/register`
- POST `/api/user/login`

### Files
- POST `/api/files/upload`
- GET `/api/files/download`
- GET `/api/files/download/{id}`
- GET `/api/files/list`
- DELETE `/api/files/delete/{id}`


## ▶️ Run Locally
```bash
mvn spring-boot:run


⚡ How It Works...............

User uploads file via API
File is stored in local storage using Java NIO
File metadata is saved in MySQL database
Each file is linked to a user (ownership)
Access control ensures only the owner can view/delete files
🧪 How to Run
# Clone repository
git clone https://github.com/your-username/drive-backend.git

# Navigate to project
cd drive-backend

# Run application
mvn spring-boot:run

Server runs at:..................
👉 http://localhost:8080

🔐 Key Highlights
✔ Secure file access with ownership validation
✔ Folder-based file organization
✔ Efficient file handling using Java NIO
✔ Scalable backend design using Spring Boot
🚀 Future Improvements
🔐 Session Based Authentication (login/signup)
☁️ Integrate AWS S3 for cloud storage
🔗 File sharing via public/private links
📊 Dashboard for file management
👨‍💻 Author

Abhiraj Kumar

📧 Email: mr10abhiraj@gmail.com
🔗 LinkedIn: https://www.linkedin.com/in/abhiraj17
⭐ Support

If you like this project, give it a ⭐ on GitHub!


