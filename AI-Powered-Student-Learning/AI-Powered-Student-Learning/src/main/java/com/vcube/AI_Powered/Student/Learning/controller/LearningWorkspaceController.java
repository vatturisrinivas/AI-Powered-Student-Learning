package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.*;
import com.vcube.AI_Powered.Student.Learning.repository.*;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestClient;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@Transactional
@RequestMapping("/api/workspace")
public class LearningWorkspaceController {
 private final UserRepository users;
 private final CourseRepository courses;
 private final EnrollmentRepository enrollments;
 private final AssignmentRepository assignments;
 private final AssignmentSubmissionRepository submissions;
 private final SkillQuizSessionRepository quizzes;
 private final DomainRepository domains;
 private final CourseTopicRepository topics;
 private final JsonMapper mapper;
 private final String aiKey;
 private final String aiModel;
 public LearningWorkspaceController(UserRepository users,CourseRepository courses,EnrollmentRepository enrollments,
 AssignmentRepository assignments,AssignmentSubmissionRepository submissions,SkillQuizSessionRepository quizzes,
 DomainRepository domains,CourseTopicRepository topics,JsonMapper mapper,@Value("${app.ai.api-key:}") String aiKey,
 @Value("${app.ai.model:gemini-2.5-flash}") String aiModel) {
 this.users=users;this.courses=courses;this.enrollments=enrollments;this.assignments=assignments;
 this.submissions=submissions;this.quizzes=quizzes;this.domains=domains;this.topics=topics;this.mapper=mapper;this.aiKey=aiKey;this.aiModel=aiModel;
 }
 private User me(Authentication auth){return users.findByEmail(auth.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));}
 private Course course(Long id){return courses.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found"));}
 private Assignment assignment(Long id){return assignments.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Assignment not found"));}
 private void trainerOwns(User user,Course c){if(user.getRole()!=UserRole.ADMIN&&!Objects.equals(c.getTrainer().getId(),user.getId()))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Not your course");}
 private void enrolled(User user,Long courseId){if(!enrollments.existsByStudentIdAndCourseId(user.getId(),courseId))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Enroll first");}
 private Map<String,Object> courseDto(Course c){return Map.of("id",c.getId(),"title",c.getTitle(),"description",c.getDescription()==null?"":c.getDescription(),"level",c.getLevel()==null?"":c.getLevel(),"trainerId",c.getTrainer().getId(),"trainerName",c.getTrainer().getName(),"domainId",c.getDomain().getId());}
 private Map<String,Object> assignmentDto(Assignment a){Map<String,Object> d=new LinkedHashMap<>();d.put("id",a.getId());d.put("courseId",a.getCourse().getId());d.put("title",a.getTitle());d.put("description",a.getDescription());d.put("maxScore",a.getMaxScore());d.put("dueAt",a.getDueAt());return d;}
 private Map<String,Object> submissionDto(AssignmentSubmission a){Map<String,Object> d=new LinkedHashMap<>();d.put("id",a.getId());d.put("assignmentId",a.getAssignment().getId());d.put("studentId",a.getStudent().getId());d.put("studentName",a.getStudent().getName());d.put("answerText",a.getAnswerText());d.put("status",a.getStatus());d.put("score",a.getScore());d.put("feedback",a.getFeedback());d.put("submittedAt",a.getSubmittedAt());return d;}
 @GetMapping("/catalog") public List<Map<String,Object>> catalog(){return courses.findAll().stream().map(this::courseDto).toList();}
 @GetMapping("/my-courses") @PreAuthorize("hasRole('STUDENT')")
 public List<Map<String,Object>> myCourses(Authentication auth){return enrollments.findByStudentId(me(auth).getId()).stream().map(e->courseDto(e.getCourse())).toList();}
 @PostMapping("/courses/{id}/enroll") @PreAuthorize("hasRole('STUDENT')")
 public Map<String,Object> enroll(@PathVariable Long id,Authentication auth){User user=me(auth);Course c=course(id);
 if(enrollments.existsByStudentIdAndCourseId(user.getId(),id))throw new ResponseStatusException(HttpStatus.CONFLICT,"Already enrolled");
 Enrollment e=new Enrollment();e.setStudent(user);e.setCourse(c);e.setStatus("ENROLLED");enrollments.save(e);return Map.of("enrolled",true,"courseId",id);}
 @PostMapping("/trainer/courses") @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
 public Map<String,Object> trainerCreate(@RequestBody CourseInput body,Authentication auth){User u=me(auth);if(body.title()==null||body.title().isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Title required");
 Domain d;
  if(body.domainId()!=null){d=domains.findById(body.domainId()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Domain not found"));}
  else{d=domains.findByName("General").orElseGet(()->{Domain created=new Domain();created.setName("General");created.setDescription("Internal compatibility category; courses are the primary unit.");return domains.save(created);});}
 Course c=new Course();c.setDomain(d);c.setTrainer(u);c.setTitle(body.title().trim());c.setDescription(body.description());c.setLevel(body.level());return courseDto(courses.save(c));}
 @GetMapping("/trainer/courses") @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
 public List<Map<String,Object>> trainerCourses(Authentication auth){User u=me(auth);return courses.findAll().stream().filter(c->u.getRole()==UserRole.ADMIN||Objects.equals(c.getTrainer().getId(),u.getId())).map(this::courseDto).toList();}
 @PostMapping("/trainer/assignments") @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
 public Map<String,Object> createAssignment(@RequestBody AssignmentInput input,Authentication auth){User u=me(auth);Course c=course(input.courseId());trainerOwns(u,c);
 if(input.title()==null||input.title().isBlank()||input.maxScore()==null||input.maxScore()<1)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Valid title and max score required");
 Assignment a=new Assignment();a.setCourse(c);a.setTitle(input.title());a.setDescription(input.description());a.setDueAt(input.dueAt());a.setMaxScore(input.maxScore());return assignmentDto(assignments.save(a));}
 @GetMapping("/courses/{id}/assignments")
 public List<Map<String,Object>> assignments(@PathVariable Long id,Authentication auth){User u=me(auth);Course c=course(id);
 if(u.getRole()==UserRole.STUDENT)enrolled(u,id);else trainerOwns(u,c);
 return assignments.findByCourseId(id).stream().map(this::assignmentDto).toList();}
 @PostMapping("/assignments/{id}/submit") @PreAuthorize("hasRole('STUDENT')")
 public Map<String,Object> submit(@PathVariable Long id,@RequestBody SubmissionInput input,Authentication auth){User u=me(auth);Assignment a=assignment(id);enrolled(u,a.getCourse().getId());
 if(input.answerText()==null||input.answerText().isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Submission cannot be empty");
 if(submissions.findByAssignmentIdAndStudentId(id,u.getId()).isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT,"Already submitted");
 AssignmentSubmission result=new AssignmentSubmission();result.setAssignment(a);result.setStudent(u);result.setAnswerText(input.answerText());return submissionDto(submissions.save(result));}
 @GetMapping("/my-submissions") @PreAuthorize("hasRole('STUDENT')")
 public List<Map<String,Object>> mySubmissions(Authentication auth){return submissions.findByStudentId(me(auth).getId()).stream().map(this::submissionDto).toList();}
 @GetMapping("/trainer/assignments/{id}/submissions") @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
 public List<Map<String,Object>> trainerSubmissions(@PathVariable Long id,Authentication auth){Assignment a=assignment(id);trainerOwns(me(auth),a.getCourse());return submissions.findByAssignmentId(id).stream().map(this::submissionDto).toList();}
 @PutMapping("/trainer/submissions/{id}/grade") @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
 public Map<String,Object> grade(@PathVariable Long id,@RequestBody GradeInput input,Authentication auth){AssignmentSubmission s=submissions.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));trainerOwns(me(auth),s.getAssignment().getCourse());
 if(input.score()==null||input.score()<0||input.score()>s.getAssignment().getMaxScore())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid score");s.setScore(input.score());s.setFeedback(input.feedback());s.setStatus("GRADED");return submissionDto(submissions.save(s));}
 @GetMapping("/dashboard") @PreAuthorize("hasRole('STUDENT')")
 public Map<String,Object> dashboard(Authentication auth){User u=me(auth);var en=enrollments.findByStudentId(u.getId());var ss=submissions.findByStudentId(u.getId());var qs=quizzes.findByStudentId(u.getId()).stream().filter(SkillQuizSession::isCompleted).toList();
 var graded=ss.stream().filter(s->s.getScore()!=null).toList();double assignmentAverage=graded.stream().mapToDouble(s->100.0*s.getScore()/s.getAssignment().getMaxScore()).average().orElse(0);
 double quizAverage=qs.stream().mapToDouble(q->q.getTotal()!=null&&q.getTotal()>0?100.0*q.getScore()/q.getTotal():0).average().orElse(0);
 int totalTasks=en.stream().mapToInt(e->assignments.findByCourseId(e.getCourse().getId()).size()).sum();
 List<Map<String,Object>> history=qs.stream().map(q->{Map<String,Object> m=new LinkedHashMap<>();m.put("course",q.getCourse().getTitle());m.put("score",q.getScore());m.put("total",q.getTotal());m.put("date",q.getCreatedAt());return m;}).toList();
 Map<String,Object> result=new LinkedHashMap<>();result.put("enrolledCourses",en.size());result.put("totalAssignments",totalTasks);result.put("submittedAssignments",ss.size());result.put("gradedAssignments",graded.size());result.put("assignmentAverage",Math.round(assignmentAverage*10)/10.0);result.put("quizAverage",Math.round(quizAverage*10)/10.0);result.put("quizAttempts",qs.size());result.put("completionRate",totalTasks==0?0:Math.round(1000.0*ss.size()/totalTasks)/10.0);result.put("overallPerformance",graded.isEmpty()&&qs.isEmpty()?0:Math.round(((graded.isEmpty()?0:assignmentAverage)+(qs.isEmpty()?0:quizAverage))/(graded.isEmpty()||qs.isEmpty()?1:2)*10)/10.0);result.put("quizHistory",history);return result;}
 @PostMapping("/courses/{id}/skill-quiz") @PreAuthorize("hasRole('STUDENT')")
 public Map<String,Object> skillQuiz(@PathVariable Long id,Authentication auth){User u=me(auth);Course c=course(id);enrolled(u,id);
 if(aiKey==null||aiKey.isBlank())throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"AI provider not configured. Set GEMINI_API_KEY.");
 String topicContext=topics.findByCourseIdOrderByPositionAsc(id).stream().limit(15).map(t->t.getTitle()+": "+(t.getContent()==null?"":t.getContent().substring(0,Math.min(800,t.getContent().length())))).reduce("",(a,v)->a+"\n"+v);
 String prompt="Create exactly 5 multiple-choice questions testing this course, each with four options A,B,C,D, a correct answer, and a concise explanation. Return ONLY JSON as {\"questions\":[{\"question\":\"...\",\"options\":{\"A\":\"...\",\"B\":\"...\",\"C\":\"...\",\"D\":\"...\"},\"correct\":\"A\",\"explanation\":\"...\"}]}. Course title: "+c.getTitle()+". Description: "+c.getDescription()+". Level: "+c.getLevel()+". Course topics and materials: "+topicContext;
 try{
 Map<String,Object> payload=Map.of(
   "contents",List.of(Map.of("role","user","parts",List.of(Map.of("text",prompt)))),
   "generationConfig",Map.of("responseMimeType","application/json"));
 String raw=RestClient.create().post()
   .uri("https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent",aiModel)
   .header("x-goog-api-key",aiKey).contentType(MediaType.APPLICATION_JSON)
   .body(payload).retrieve().body(String.class);
 JsonNode root=mapper.readTree(raw);
 String content=root.path("candidates").path(0).path("content").path("parts").path(0).path("text").asText();
 JsonNode qs=mapper.readTree(content).path("questions");
 if(!qs.isArray()||qs.size()!=5)throw new IllegalStateException("Invalid AI question count");
 for(JsonNode q:qs){if(q.path("question").asText().isBlank()||!Set.of("A","B","C","D").contains(q.path("correct").asText())||!q.path("options").isObject())throw new IllegalStateException("Invalid AI question format");for(String key:List.of("A","B","C","D"))if(q.path("options").path(key).asText().isBlank())throw new IllegalStateException("Missing answer option");}
 SkillQuizSession session=new SkillQuizSession();session.setCourse(c);session.setStudent(u);session.setQuestionsJson(mapper.writeValueAsString(qs));session=quizzes.save(session);
 return Map.of("quizId",session.getId(),"course",c.getTitle(),"questions",publicQuestions(qs));
 }catch(Exception e){throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"Could not generate quiz; check provider configuration and try again");}}
 private List<Map<String,Object>> publicQuestions(JsonNode qs){List<Map<String,Object>> list=new ArrayList<>();for(int i=0;i<qs.size();i++){JsonNode q=qs.get(i);list.add(Map.of("index",i,"question",q.path("question").asText(),"options",mapper.convertValue(q.path("options"),Map.class)));}return list;}
 @PostMapping("/skill-quizzes/{id}/submit") @PreAuthorize("hasRole('STUDENT')")
 public Map<String,Object> gradeQuiz(@PathVariable Long id,@RequestBody QuizAnswers body,Authentication auth){User u=me(auth);SkillQuizSession quiz=quizzes.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
 if(!Objects.equals(quiz.getStudent().getId(),u.getId()))throw new ResponseStatusException(HttpStatus.FORBIDDEN);if(quiz.isCompleted())throw new ResponseStatusException(HttpStatus.CONFLICT,"Quiz already submitted");
 try{JsonNode questions=mapper.readTree(quiz.getQuestionsJson());int score=0;List<Map<String,Object>> detail=new ArrayList<>();
 for(int i=0;i<questions.size();i++){JsonNode q=questions.get(i);String answer=body.answers()==null?"":body.answers().getOrDefault(String.valueOf(i),"");String expected=q.path("correct").asText();boolean correct=expected.equals(answer);if(correct)score++;
 detail.add(Map.of("index",i,"correct",correct,"correctOption",expected,"explanation",q.path("explanation").asText()));}
 quiz.setCompleted(true);quiz.setScore(score);quiz.setTotal(questions.size());quizzes.save(quiz);return Map.of("score",score,"total",questions.size(),"review",detail);
 }catch(Exception e){throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to grade quiz");}}
 public record CourseInput(Long domainId,String title,String description,String level){}
 public record AssignmentInput(Long courseId,String title,String description,Integer maxScore,LocalDateTime dueAt){}
 public record SubmissionInput(String answerText){}
 public record GradeInput(Integer score,String feedback){}
 public record QuizAnswers(Map<String,String> answers){}
}
