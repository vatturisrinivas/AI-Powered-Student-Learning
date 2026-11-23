# SkillForge AI — React + Tailwind Frontend

A refreshed, responsive frontend for the existing SkillSpring/SkillForge Spring Boot backend.

## What changed

- Brand renamed throughout the active user interface to **SkillForge AI**.
- Unified email/password sign-in for STUDENT, TRAINER and ADMIN. Public sign-up creates STUDENT accounts only (enforced by backend).
- Responsive layout with drawer navigation on mobile, accessible controls, improved typography, spacing and cards.
- Student: overview, explore and enroll, my courses, trainer-created MCQ assessments, roadmap, performance, **AI Mock Interview**.
- Trainer: overview, create courses, author trainer-created MCQs, course roadmap.
- Admin: admin overview, manage trainer accounts and enrollment list.
- Removed the AI Skill Test page, navigation, dashboard/performance widgets and frontend API requests. **AI Mock Interview remains**.
- API request URLs and JSON shapes reused from the uploaded frontend. This download is **frontend-only**; it does not modify Spring Boot.

## Requirements

- Node.js 20+ recommended.
- Your existing Spring Boot backend running at `http://localhost:8080`.
- Your local MySQL and JWT settings remain in Spring Boot.
- For Gemini interviews, set `GEMINI_API_KEY` and an available `GEMINI_MODEL` (e.g. `gemini-3.5-flash-lite`) **in the backend only**.

## Run on Windows

```powershell
cd student-learning-frontend
npm install
npm run dev
```

Then visit http://localhost:5173.

Vite proxies `/api/*` to `http://localhost:8080` in development. Alternatively set `VITE_API_URL` to your deployed backend origin. Never set Gemini keys in React.

## Code organization

```
src/
  api.js                      # Shared fetch wrapper
  main.jsx                    # React entry
  App.jsx                     # Route definitions and role guards
  context/AuthContext.jsx     # JWT session
  layouts/AppLayout.jsx       # Desktop/mobile navigation and topbar
  components/Brand.jsx       # Shared branding
  components/UI.jsx          # Shared UI states and headings
  features/AuthPage.jsx      # Login + student registration
  features/learning/index.jsx # Student and trainer learning screens
  features/portal/index.jsx  # Trainer MCQs, roadmap, admin, performance, interviews
  hooks/useApiData.js        # Reusable loading/error hook
  index.css                  # Theme and responsive styles
```

## Security and backend requirements

- React's role guards are presentation only. Continue to enforce permissions in Spring Security.
- The existing backend may retain old AI Skill Quiz endpoints/data, but the **frontend no longer calls or displays them**. Removing backend endpoints/tables requires a separate migration.
- The admin enrollment view is read-only to avoid unexpected deletions.
- Existing user data is preserved; old localStorage sessions (`ss_session`) are migrated into `sf_session` when used.

## Checks performed

- All source `.js/.jsx` files parsed successfully with Babel's JSX parser.
- A full Vite production build was **not** completed in the build environment because the uploaded `node_modules` had Windows-only Rollup native dependencies. On Windows, install fresh with `npm install` before `npm run build`.
- End-to-end testing against your local Spring Boot API is still required.
