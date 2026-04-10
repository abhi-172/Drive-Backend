📁 Cloud File Storage System (Drive Backend)

A backend application similar to Google Drive that allows users to upload, store, manage, and retrieve files with secure access control using Spring Boot.

🚀 Features..............

📤 Upload files with user association
📥 Download files securely
🗂️ Organize files using folder structure
🗑️ Delete files from storage and database
🔐 User-based access control (only owner can access files)
📊 Store file metadata (name, size, path, created time)

🛠️ Tech Stack.............

Backend: Java, Spring Boot
Database: MySQL
ORM: Spring Data JPA
File Handling: Java NIO (Files API)
API: REST APIs
📂 Project Structure
Drive_Backend/
│── controller/
│   └── FileController.java
│── services/
│   └── FileServiceStorage.java
│── entity/
│   ├── FileEntity.java
│   └── User.java
│── repo/
│   ├── FileRepository.java
│   └── UserRepository.java

⚙️ API Endpoints.................

📌 1. Upload File

POST /api/files/upload

Request:

Form-data →
file (file to upload)
userId
parentFolderId (optional)

Response:

"File uploaded Successfully"
📌 2. Download File

GET /api/files/download/{id}

Params:

userId

Response:

Returns file as downloadable resource
📌 3. List Files

GET /api/files/list

Params:

userId
parentFolderId (optional)

Response:

[
  {
    "id": 1,
    "name": "file.pdf",
    "size": 12345,
    "type": "file"
  }
]
📌 4. Delete File

DELETE /api/files/delete/{id}

Params:

userId

Response:

"File deleted Successfully"

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
🔐 Add JWT Authentication (login/signup)
☁️ Integrate AWS S3 for cloud storage
🔗 File sharing via public/private links
📊 Dashboard for file management
👨‍💻 Author

Abhiraj Kumar

📧 Email: mr10abhiraj@gmail.com
🔗 LinkedIn: https://www.linkedin.com/in/abhiraj17
⭐ Support

If you like this project, give it a ⭐ on GitHub!
