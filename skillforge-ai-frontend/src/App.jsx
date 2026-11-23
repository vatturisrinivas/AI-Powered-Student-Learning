import { BrowserRouter, Navigate, Route, Routes, useLocation } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import AuthPage from './features/AuthPage';
import AppLayout from './layouts/AppLayout';
import { LearningDashboard, CourseEnrollment, MyLearning, TrainerStudio } from './features/learning';
import { AdminPortal, TrainerAssessments, RoadmapPortal, StudentMcqPortal, PerformancePortal, InterviewPortal } from './features/portal';
import { useApiData } from './hooks/useApiData';
import { EmptyState, ErrorNotice, LoadingState, PageHeader } from './components/UI';

function Guard({ roles, children }) {
 const {session}=useAuth();
 if(!session?.token) return <Navigate to="/login" replace/>;
 if(roles && !roles.includes(session.role)) return <Navigate to={session.role==='ADMIN'?'/admin-portal':session.role==='TRAINER'?'/trainer':'/dashboard'} replace/>;
 return <AppLayout>{children}</AppLayout>;
}
function EnrollmentsPage(){
 const {session}=useAuth();
 const {data,loading,error}=useApiData('/enrollments',session.token);
 return <><PageHeader title="Enrollments" description="Review enrollment records across the platform."/><ErrorNotice message={error}/><div className="panel overflow-hidden">{loading?<LoadingState/>:!data?.length?<EmptyState title="No enrollments yet" description="Students will appear here when they join courses."/>:<div className="overflow-x-auto"><table className="min-w-[640px] w-full text-left text-sm"><thead><tr><th>ID</th><th>Student ID</th><th>Course ID</th><th>Status</th><th>Enrolled</th></tr></thead><tbody>{data.map(x=><tr key={x.id}><td>#{x.id}</td><td>{x.student?.id??'—'}</td><td>{x.course?.id??'—'}</td><td>{x.status}</td><td>{x.enrolledAt?new Date(x.enrolledAt).toLocaleDateString():'—'}</td></tr>)}</tbody></table></div>}</div></>;
}
function AppRoutes(){const {session}=useAuth();return <Routes>
 <Route path="/" element={<Navigate to={session?.role==='ADMIN'?'/admin-portal':session?.role==='TRAINER'?'/trainer':'/dashboard'} replace/>}/>
 <Route path="/login" element={<AuthPage/>}/><Route path="/register" element={<AuthPage register/>}/>
 <Route path="/dashboard" element={<Guard><LearningDashboard session={session}/></Guard>}/>
 <Route path="/enroll" element={<Guard roles={['STUDENT']}><CourseEnrollment session={session}/></Guard>}/>
 <Route path="/my-learning" element={<Guard roles={['STUDENT']}><MyLearning session={session}/></Guard>}/>
 <Route path="/student-assessments" element={<Guard roles={['STUDENT']}><StudentMcqPortal session={session}/></Guard>}/>
 <Route path="/roadmap" element={<Guard><RoadmapPortal session={session}/></Guard>}/>
 <Route path="/performance" element={<Guard roles={['STUDENT']}><PerformancePortal session={session}/></Guard>}/>
 <Route path="/interview" element={<Guard roles={['STUDENT']}><InterviewPortal session={session}/></Guard>}/>
 <Route path="/trainer" element={<Guard roles={['TRAINER','ADMIN']}><TrainerStudio session={session}/></Guard>}/>
 <Route path="/trainer-assessments" element={<Guard roles={['TRAINER','ADMIN']}><TrainerAssessments session={session}/></Guard>}/>
 <Route path="/admin-portal" element={<Guard roles={['ADMIN']}><AdminPortal session={session}/></Guard>}/>
 <Route path="/manage/enrollments" element={<Guard roles={['ADMIN']}><EnrollmentsPage/></Guard>}/>
 <Route path="*" element={<Navigate to="/dashboard" replace/>}/>
 </Routes>}
export default function App(){return <AuthProvider><BrowserRouter><AppRoutes/></BrowserRouter></AuthProvider>}
