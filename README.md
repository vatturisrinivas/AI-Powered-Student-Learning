# SkillForge AI

**AI-Powered Student Learning & Placement Preparation Platform**  
**VCUBE Hack Quest 1.0 · Problem 4 (EdTech) · 21 November 2026**

SkillForge AI is a full-stack learning and placement-preparation project built with **Java, Spring Boot, React, Tailwind CSS, and MySQL**. Its goal is to help students explore career roles, access courses and assessments, track their performance, and eventually receive AI-assisted learning plans and mock-interview feedback.

> **Project status:** Spring Boot entities, repositories, service/controller scaffolding, authentication endpoints, and a React UI have been prepared. End-to-end integration and some student-facing workflows are **still being tested/implemented**. AI-related dashboard screens may show previews rather than real evaluated AI results. Do not present them as completed features until verified.

## Tech stack

| Layer | Technology |
| --- | --- |
| Frontend | React 18, Vite 6, Tailwind CSS 3, React Router, Axios |
| Backend | Java, Spring Boot, Spring Web, Spring Security, Spring Data JPA |
| Database | MySQL |
| Authentication | BCrypt password hashing, JWT |
| Planned AI integration | Spring AI or compatible LLM API, invoked by Spring Boot |
| Development | Eclipse IDE, VS Code, Postman, Git, GitHub |

**Version note:** Development logs showed Java 25 and Spring Boot 4.2.0 in one local run, while earlier examples used Java 17/Spring Boot 3. Confirm the *actual* versions in the backend `pom.xml` and your installed JDK. Use compatible versions consistently.

## Main modules

- **Authentication:** Student registration, login, JWT-protected profile endpoint.
- **Learning catalogue:** Domains, career roles, courses, and course topics.
- **Assessments:** Assessments, reusable questions, assessment-question associations, attempts, and responses.
- **Performance tracking:** Assessment results and topic/skill analysis data structures.
- **Personalized learning:** Learning paths, path items, and AI roadmap data structures.
- **Interview preparation:** Mock-interview sessions, question/answer records, and evaluation fields.
- **Placement readiness:** Readiness-score and recommendation data structures.

**Current limitation:** The uploaded controller scaffolding restricts enrollment, assessment attempts, learning paths, mock interviews, and readiness management to **ADMIN**. Student-specific flows require dedicated ownership-checked endpoints. AI generation/evaluation is not confirmed end-to-end.

## Architecture

```text
React + Tailwind (http://localhost:5173)
           |
           | Axios /api/* via Vite dev proxy
           v
Spring Boot REST API (http://localhost:8080)
       |                |
       v                v
Spring Security      Controllers
JWT + roles              |
                         v
                      Services
                         |
                         v
                 Spring Data JPA
                         |
                         v
                       MySQL

Planned: Services -> Spring AI / LLM API
```

## Database design

The expanded design contains **18 entity-backed tables** and one `UserRole` Java enum:

| Table | Java entity |
| --- | --- |
| `users` | `User` |
| `domains` | `Domain` |
| `career_roles` | `CareerRole` |
| `courses` | `Course` |
| `course_topics` | `CourseTopic` |
| `enrollments` | `Enrollment` |
| `assessments` | `Assessment` |
| `questions` | `Question` |
| `assessment_questions` | `AssessmentQuestion` |
| `assessment_attempts` | `AssessmentAttempt` |
| `student_responses` | `StudentResponse` |
| `student_performance` | `StudentPerformance` |
| `learning_paths` | `LearningPath` |
| `learning_path_items` | `LearningPathItem` |
| `learning_roadmaps` | `LearningRoadmap` |
| `mock_interviews` | `MockInterview` |
| `mock_interview_answers` | `MockInterviewAnswer` |
| `placement_readiness` | `PlacementReadiness` |

Important relationships include `Course -> Domain`, `Course -> User (trainer)`, `Enrollment -> Course/User (student)`, `Assessment -> Course`, `AssessmentQuestion -> Assessment/Question`, `AssessmentAttempt -> Assessment/User`, `StudentResponse -> AssessmentAttempt/Question`, and `LearningPath -> User/CareerRole`.

> **ER diagram:** Add your exported diagram to `docs/er-diagram.png` and link it here before submission.

## Repository structure

This is a suggested arrangement if frontend and backend are stored in one repository:

```text
skillforge-ai/
├── backend/                         # Spring Boot Maven project
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/vcube/AI_Powered/Student/Learning/
│       │   ├── controller/
│       │   ├── config/
│       │   ├── dto/
│       │   ├── entity/
│       │   ├── repository/
│       │   ├── security/
│       │   └── service/
│       └── resources/application.properties
├── frontend/                        # React + Vite project
│   ├── package.json
│   ├── src/
│   ├── vite.config.js
│   └── .env.example
├── docs/
│   ├── er-diagram.png                # add before submitting
│   └── postman-collection.json       # add before submitting
└── README.md
```

If your folders are named differently, adjust the commands below accordingly.

## Prerequisites

- JDK compatible with the backend Spring Boot version; verify using `java -version`.
- Eclipse IDE (or another Java IDE), Maven / Maven Wrapper.
- Node.js and npm (compatible with your Vite version).
- MySQL Server and a client such as MySQL Workbench.
- Postman for API testing.

## Local setup

### 1. Create the database

```sql
CREATE DATABASE IF NOT EXISTS skillforge_db;
```

### 2. Configure Spring Boot

In `backend/src/main/resources/application.properties` (or the corresponding backend resource folder):

```properties
spring.application.name=AI-Powered-Student-Learning
spring.datasource.url=jdbc:mysql://localhost:3306/skillforge_db
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080
app.jwt.secret=${JWT_SECRET}
app.jwt.expiration-ms=86400000
app.frontend-origin=http://localhost:5173
```

Set `DB_PASSWORD` and `JWT_SECRET` as environment variables in **Eclipse → Run Configurations → Environment**. The JWT secret must be **at least 32 UTF-8 bytes** for the supplied HMAC service. Generate a random one, for example:

```bash
python -c "import secrets; print(secrets.token_urlsafe(48))"
```

Do **not** commit `.env` files containing secrets, database passwords, or generated JWT secrets.

### 3. Start the backend

Import the backend into Eclipse as an **Existing Maven Project**, then run your main `AiPoweredStudentLearningApplication` class. Alternatively, from the backend directory on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The backend should listen at `http://localhost:8080` if startup succeeds. Check Eclipse console output for dependency, JWT, MySQL, or repository errors.

### 4. Start the frontend

```bash
cd frontend
npm install
```

Create `frontend/.env` from `.env.example` (or add these values):

```dotenv
VITE_DEMO_MODE=false
VITE_API_BASE_URL=/api
```

Run:

```bash
npm run dev
```

Open `http://localhost:5173`. The Vite dev proxy forwards `/api` requests to `http://localhost:8080`.

**CORS:** With the local Vite proxy, the browser sees same-origin requests. If calling `http://localhost:8080` directly from the browser or hosting frontend and backend on separate domains, configure CORS in Spring Security to allow the actual frontend origin. A property such as `app.frontend-origin` only takes effect if your security configuration reads and uses it.

## Authentication and API testing

The following paths match the controller scaffolding provided for this project. Access depends on the actual `SecurityConfig` and method-level security settings.

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `POST` | `/api/auth/register` | Student signup |
| `POST` | `/api/auth/login` | Login / JWT |
| `GET` | `/api/auth/me` | Logged-in profile |
| `GET` | `/api/domains` | List domains |
| `GET` | `/api/career-roles` | List roles |
| `GET` | `/api/courses` | List courses |
| `GET` | `/api/course-topics` | List course topics |
| `GET` | `/api/assessments` | List assessments |

Create/edit/delete endpoints are in the corresponding controllers, with authorization restrictions. The scaffolding also includes `/api/questions`, `/api/assessment-questions`, `/api/enrollments`, `/api/assessment-attempts`, `/api/student-responses`, `/api/student-performance`, `/api/learning-paths`, `/api/learning-path-items`, `/api/learning-roadmaps`, `/api/mock-interviews`, `/api/mock-interview-answers`, and `/api/placement-readiness`.

### Example student signup

**POST** `http://localhost:8080/api/auth/register`  
Content-Type: `application/json`

```json
{
  "name": "Test Student",
  "email": "student@example.com",
  "password": "Student@123"
}
```

### Example login

**POST** `http://localhost:8080/api/auth/login`

```json
{
  "email": "student@example.com",
  "password": "Student@123"
}
```

For a protected endpoint such as **GET** `/api/auth/me`, send:

```http
Authorization: Bearer <JWT_FROM_LOGIN>
```

> Student signup is intentionally limited to a student role. Trainer/admin accounts should be created by a trusted administrator workflow; do not make public signup an unrestricted admin-registration endpoint.

## Security considerations

- Use **BCrypt** to hash passwords before storage.
- Keep JWT secrets and database credentials outside source control.
- Enable `@EnableMethodSecurity` if using `@PreAuthorize` annotations.
- Validate authorization **and ownership** for student records on the server.
- Do not return raw JPA entities that expose passwords, question answer keys, or problematic lazy relationships. Use request/response DTOs.
- Evaluate quiz answers and calculate marks **on the backend**, not in React.
- For production deployments, use HTTPS and a production-suitable session/token strategy.

## Hackathon deliverables

- [ ] GitHub repository URL: **ADD_LINK**
- [ ] Live frontend URL: **ADD_LINK**
- [ ] Live backend/API URL: **ADD_LINK**
- [ ] ER diagram (`docs/er-diagram.png`)
- [ ] Postman collection (`docs/postman-collection.json`)
- [ ] End-to-end test of student signup, login, and course catalogue
- [ ] At least two meaningful AI features connected through Spring Boot using real application data
- [ ] Final demo and screenshots

## Planned next steps

1. Confirm signup/login and authorized catalogue requests work against local Spring Boot.
2. Implement student-scoped enrollment and quiz submission APIs, with server-side score calculation.
3. Implement **AI Skill Gap Analysis** and **Personalized Learning Path Generation** using saved student results and an LLM called from the backend.
4. Add interview question generation, mock-interview evaluation, and readiness recommendations.
5. Export the ER diagram and Postman collection, deploy frontend/backend/database, and verify production URLs.

## Team and links

- **Team name:** Garbage Collectors
- **Team members:** SRINIVAS, SANTHOSH, BHARGAV, LAXMI LASYA, SIRISHA


---

Built for **VCUBE HACK QUEST 1.0** — *Learn • Build • Innovate*.
