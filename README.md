# ✈️ Flight Finder

A full-stack application that calculates how many times the word "flight" can be formed from a given string.

## 🎯 Features

- **Calculate** - Count how many times "flight" can be formed from input string
- **History** - View all search history with timestamps
- **Validation** - Input validation (lowercase letters only, max 100 characters)
- **Auto-refresh** - Search history automatically refreshes after calculation
- **API Documentation** - Interactive API docs with Swagger UI

## 🛠️ Tech Stack

### Backend
- **Java 21** - Programming language
- **Spring Boot 3.5.7** - Application framework
- **Spring Data JPA** - Data persistence
- **Hibernate** - ORM framework
- **PostgreSQL** - Database
- **Swagger/OpenAPI** - API documentation

### Frontend
- **React 18** - UI library
- **TypeScript** - Type safety
- **Tailwind CSS** - Styling
- **Axios** - HTTP client

### DevOps
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Nginx** - Frontend web server

## 🚀 Quick Start

### Prerequisites

- Docker and Docker Compose installed
- Git

### Option 1: Run with Docker (Recommended)
```bash
# 1. Clone the repository
git clone https://github.com/YOUR_USERNAME/flight-finder.git
cd flight-finder

# 2. Build and start all services
docker-compose up --build
```

Access the application
- **Frontend**: http://localhost
- **Backend API**: http://localhost:8080/api/flight-finder
- **Swagger UI**: http://localhost:8080/swagger-ui.html 


### Option 2: Run Locally

#### Backend
```bash
cd backend

# Start PostgreSQL (using Docker)
docker-compose -f docker-compose.dev.yml up -d

# Run Spring Boot application
./mvnw spring-boot:run

# Backend will be available at http://localhost:8080
```

#### Frontend
```bash
cd frontend

# Install dependencies
npm install

# Start development server
npm start

# Frontend will be available at http://localhost:3000
```

## 📖 API Documentation

After starting the backend, visit:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs


## 🏗️ Project Structure
```
flight-finder/
├── backend/                 # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/flightfinder/
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── repository/
│   │   │   │       ├── entity/
│   │   │   │       ├── dto/
│   │   │   │       └── config/
│   │   │   └── resources/
│   │   └── test/
│   ├── pom.xml
│   └── Dockerfile
├── frontend/                # React frontend
│   ├── src/
│   │   ├── components/
│   │   ├── services/
│   │   ├── types/
│   │   ├── context/
│   │   └── App.tsx
│   ├── package.json
│   ├── Dockerfile
│   └── nginx.conf
├── docker-compose.yml
└── README.md
```

## 📄 License

This project is licensed under the MIT License.


## 🙏 Acknowledgments

- Spring Boot
- React
- PostgreSQL
- Docker