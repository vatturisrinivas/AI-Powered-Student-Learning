# SkillForge AI — Interview 400 diagnostics update

This bundle pairs the backend from `AI-Powered-Student-Learning(2).zip` with the last supplied SkillForge React frontend.

## What the comparison showed

The Java source code in `AI-Powered-Student-Learning(1).zip` and `(2).zip` is byte-identical. The second archive only adds empty static/templates directories. So the backend replacement alone did not change the interview implementation.

## Changes

- Backend: explicit validation for a missing, zero or negative `courseId` in `POST /api/portal/interviews/start`; the error now explains what's wrong.
- Frontend: validates the selected enrolled course ID before making the request.
- Frontend: removes generic trainer-creation error text for interview errors and prefers Spring's `detail` message.
- Gemini integration and model are unchanged; keep `GEMINI_MODEL=gemini-3.5-flash-lite` and a newly rotated `GEMINI_API_KEY` in Eclipse.

## Run

1. Back up your currently working backend/frontend before replacing files.
2. Import `AI-Powered-Student-Learning` as a Maven project in Eclipse; keep your existing `JWT_SECRET`, `DB_PASSWORD`, `GEMINI_API_KEY`, `GEMINI_MODEL` Run Configuration environment values.
3. Maven Update Project, Project Clean, then restart Spring Boot.
4. In `skillforge-ai-frontend`, run `npm install` and `npm run dev`.
5. Log in as a student, enroll in a course, then choose that course for an AI Mock Interview.

## If it still returns 400

Open browser DevTools > Network > `interviews/start` > Payload and check it contains `{ "courseId": 1 }` with an existing enrolled course ID, not `null` or a string. Check Response for Spring `detail`. For testing in Postman use a STUDENT Bearer token and POST JSON to `http://localhost:8080/api/portal/interviews/start`.

The 400 root cause cannot be confirmed without the actual failed request payload/response or backend console output. This update improves validation and diagnostic messages but does not guarantee every possible 400 is fixed.

Note: the uploaded backend contains legacy APIs and the React frontend still uses `/api/workspace` for selected dashboard data. They are kept to avoid breaking existing functionality.
