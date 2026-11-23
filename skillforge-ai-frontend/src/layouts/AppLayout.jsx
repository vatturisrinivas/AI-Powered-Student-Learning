import { useEffect, useState } from 'react';
import { NavLink, useLocation, useNavigate } from 'react-router-dom';
import { ArrowUpRight, BookOpen, BrainCircuit, ChevronRight, ClipboardCheck, GraduationCap, LayoutDashboard, LogOut, Menu, PanelLeftClose, ShieldCheck, Sparkles, Target, TrendingUp, Users, X } from 'lucide-react';
import Brand from '../components/Brand';
import { useAuth } from '../context/AuthContext';

const studentLinks = [
 { to:'/dashboard', label:'Overview', icon:LayoutDashboard },
 { to:'/enroll', label:'Explore courses', icon:BookOpen },
 { to:'/my-learning', label:'My learning', icon:GraduationCap },
 { to:'/student-assessments', label:'Assessments', icon:ClipboardCheck },
 { to:'/roadmap', label:'Learning roadmap', icon:Target },
 { to:'/performance', label:'Performance', icon:TrendingUp },
 { to:'/interview', label:'AI Mock Interview', icon:BrainCircuit, featured:true },
];
const trainerLinks = [
 { to:'/dashboard', label:'Overview', icon:LayoutDashboard },
 { to:'/trainer', label:'My courses', icon:GraduationCap },
 { to:'/trainer-assessments', label:'MCQ assessments', icon:ClipboardCheck },
 { to:'/roadmap', label:'Course roadmaps', icon:Target },
];
const adminLinks = [
 { to:'/admin-portal', label:'Admin overview', icon:LayoutDashboard },
 { to:'/trainer', label:'Course management', icon:GraduationCap },
 { to:'/trainer-assessments', label:'MCQ assessments', icon:ClipboardCheck },
 { to:'/roadmap', label:'Course roadmaps', icon:Target },
 { to:'/manage/enrollments', label:'Enrollments', icon:Users },
];
function SidebarLink({ entry, onNavigate }) {
 const Icon = entry.icon;
 return <NavLink to={entry.to} onClick={onNavigate} className={({isActive}) => `nav-item ${isActive?'nav-item-active':''} ${entry.featured?'nav-item-featured':''}`}>
   <Icon size={19} strokeWidth={1.9}/><span className="min-w-0 flex-1 truncate">{entry.label}</span>{entry.featured&&<Sparkles size={14} className="opacity-70"/>}
 </NavLink>;
}
export default function AppLayout({children}) {
 const { session, logout } = useAuth();
 const [open, setOpen] = useState(false);
 const navigate = useNavigate();
 const location = useLocation();
 useEffect(() => { setOpen(false); }, [location.pathname]);
 const role = session?.role || 'STUDENT';
 const links = role==='ADMIN' ? adminLinks : role==='TRAINER' ? trainerLinks : studentLinks;
 const page = links.find(x => x.to===location.pathname)?.label || 'Workspace';
 const signOut = () => { logout(); navigate('/login', {replace:true}); };
 return <div className="app-shell">
   {open&&<button aria-label="Close navigation overlay" className="fixed inset-0 z-40 bg-slate-950/45 backdrop-blur-[2px] lg:hidden" onClick={()=>setOpen(false)}/>}
   <aside className={`app-sidebar ${open?'sidebar-open':''}`}>
      <div className="mb-10 flex items-center justify-between px-2"><Brand/><button aria-label="Close menu" className="rounded-xl p-2 text-slate-500 hover:bg-slate-100 lg:hidden" onClick={()=>setOpen(false)}><X size={20}/></button></div>
      <div className="px-3 pb-3 text-[10px] font-bold uppercase tracking-[.22em] text-slate-400">{role==='STUDENT'?'Learning space':role==='TRAINER'?'Trainer workspace':'Administration'}</div>
      <nav aria-label="Main navigation" className="flex-1 space-y-1">{links.map(entry=><SidebarLink key={entry.to} entry={entry} onNavigate={()=>setOpen(false)}/>)}</nav>
      <div className="mt-7 rounded-[22px] bg-gradient-to-br from-indigo-600 to-violet-700 p-5 text-white shadow-lg shadow-indigo-200/40">
        <span className="inline-flex items-center gap-1.5 rounded-full bg-white/15 px-2.5 py-1 text-[10px] font-bold tracking-wide"><Sparkles size={12}/> SKILLFORGE AI</span>
        <p className="mt-3 text-base font-extrabold leading-snug">Every step makes you stronger.</p>
        <p className="mt-2 text-xs leading-relaxed text-indigo-100">Practice, improve, and build confidence at your pace.</p>
        {role==='STUDENT'&&<NavLink className="mt-4 inline-flex items-center gap-1.5 text-xs font-extrabold text-white" to="/interview">Practice with AI <ArrowUpRight size={14}/></NavLink>}
      </div>
      <button onClick={signOut} className="mt-5 flex w-full items-center gap-3 rounded-xl px-4 py-3 text-left text-sm font-semibold text-slate-500 transition hover:bg-rose-50 hover:text-rose-600"><LogOut size={18}/> Sign out</button>
   </aside>
   <div className="min-w-0 flex-1">
     <header className="app-topbar">
       <div className="flex min-w-0 items-center gap-3"><button onClick={()=>setOpen(true)} aria-label="Open navigation" className="rounded-xl border border-slate-200 p-2.5 text-slate-700 lg:hidden"><Menu size={20}/></button><span className="hidden text-sm text-slate-400 sm:block">Workspace</span><ChevronRight size={15} className="hidden text-slate-300 sm:block"/><strong className="truncate text-sm text-slate-800">{page}</strong></div>
       <div className="flex items-center gap-3"><span className="hidden rounded-full bg-indigo-50 px-3 py-1.5 text-[11px] font-extrabold tracking-wide text-indigo-700 sm:block">{role}</span><span className="flex size-10 shrink-0 items-center justify-center rounded-2xl bg-gradient-to-br from-violet-200 to-indigo-100 text-sm font-extrabold text-violet-700">{session?.name?.[0]?.toUpperCase()||'U'}</span><div className="hidden min-w-0 sm:block"><p className="max-w-40 truncate text-xs font-bold text-slate-900">{session?.name||'Account'}</p><p className="text-[11px] text-slate-400">{role.toLowerCase()}</p></div></div>
     </header>
     <main id="main-content" className="mx-auto max-w-[1440px] px-4 pb-12 pt-8 sm:px-7 lg:px-10 lg:pt-10">{children}</main>
   </div>
 </div>;
}
