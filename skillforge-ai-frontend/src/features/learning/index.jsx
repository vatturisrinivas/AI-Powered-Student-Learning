import React,{useEffect,useState} from 'react';
import {Link} from 'react-router-dom';
import {BookOpen,ClipboardCheck,TrendingUp,Plus,ArrowRight,GraduationCap} from 'lucide-react';
import {api} from '../../api';

const Notice=({error})=>error?<div role="alert" className="rounded-xl border border-red-200 bg-red-50 p-3 text-sm text-red-700">{error}</div>:null;
const Card=({children})=><div className="panel p-5 md:p-6">{children}</div>;
const Btn=({children,...rest})=><button className="btn-primary" {...rest}>{children}</button>;
function useLoad(path,token,enabled=true){const [data,setData]=useState(null),[error,setError]=useState(''),[busy,setBusy]=useState(true),[refresh,setRefresh]=useState(0);useEffect(()=>{if(!enabled){setBusy(false);return}let active=true;setBusy(true);setError('');api(path,{token}).then(x=>active&&setData(x)).catch(e=>active&&setError(e.message)).finally(()=>active&&setBusy(false));return()=>{active=false}},[path,token,enabled,refresh]);return {data,error,busy,reload:()=>setRefresh(x=>x+1)}}
const Title=({title,subtitle})=><div className="mb-6"><p className="eyebrow">YOUR LEARNING WORKSPACE</p><h1 className="mt-2 font-display text-3xl font-extrabold">{title}</h1><p className="mt-2 text-sm text-slate-500">{subtitle}</p></div>;

export function LearningDashboard({session}) {
 const trainer=session.role==='TRAINER'||session.role==='ADMIN';
 const d=useLoad(trainer?'/workspace/trainer/courses':'/portal/student/performance',session.token);
 const firstName=session.name?.split(' ')[0]||'Learner';
 const stats=trainer?[
   {label:'Courses managed',value:d.data?.length||0,icon:BookOpen,accent:'violet'},
   {label:'Learning content',value:'MCQ + Roadmap',icon:ClipboardCheck,accent:'blue'},
   {label:'Your workspace',value:'Trainer',icon:GraduationCap,accent:'amber'},
 ]:[
   {label:'MCQ average',value:`${d.data?.mcqAverage||0}%`,icon:TrendingUp,accent:'violet'},
   {label:'Assessments taken',value:d.data?.mcqAttempts||0,icon:ClipboardCheck,accent:'blue'},
   {label:'Topics completed',value:`${d.data?.roadmapCompleted||0}/${d.data?.roadmapTotal||0}`,icon:BookOpen,accent:'amber'},
   {label:'Roadmap progress',value:`${d.data?.roadmapPercent||0}%`,icon:TrendingUp,accent:'green'},
 ];
 return <>
   <section className="relative mb-8 overflow-hidden rounded-[28px] bg-gradient-to-br from-[#3b2a93] via-[#6245d7] to-[#865bde] px-6 py-9 text-white shadow-xl shadow-violet-200/60 sm:px-9 sm:py-11 lg:px-12">
     <div aria-hidden="true" className="pointer-events-none absolute -right-20 -top-36 h-96 w-96 rounded-full border-[55px] border-white/10"/>
     <div aria-hidden="true" className="pointer-events-none absolute -bottom-44 right-32 h-80 w-80 rounded-full border-[45px] border-white/5"/>
     <div className="relative z-10 max-w-2xl"><span className="mb-5 inline-flex rounded-full border border-white/15 bg-white/15 px-3 py-1.5 text-[11px] font-extrabold tracking-wide">✦ YOUR SKILLFORGE WORKSPACE</span>
       <h1 className="font-display text-3xl font-extrabold tracking-[-.05em] sm:text-4xl lg:text-[44px]">Welcome back, {firstName}! <span aria-hidden="true">👋</span></h1>
       <p className="mt-4 max-w-xl text-sm leading-7 text-violet-100 sm:text-base">{trainer?'Shape the next generation of talent. Create courses, publish assessments, and guide every learning milestone.':'Build momentum every day. Learn new concepts, track your progress, and get ready for your next big opportunity.'}</p>
       <div className="mt-7 flex flex-wrap gap-3"><Link to={trainer?'/trainer':'/my-learning'} className="inline-flex items-center gap-2 rounded-xl bg-white px-5 py-3 text-sm font-extrabold text-violet-700 shadow hover:bg-violet-50">{trainer?'Manage courses':'Continue learning'} <ArrowRight size={17}/></Link><Link to={trainer?'/trainer-assessments':'/interview'} className="inline-flex items-center gap-2 rounded-xl border border-white/35 bg-white/10 px-5 py-3 text-sm font-bold text-white hover:bg-white/20">{trainer?'Create assessment':'AI Mock Interview'} <ArrowRight size={16}/></Link></div>
     </div>
   </section>
   <div className="mb-8"><h2 className="mb-4 font-display text-lg font-extrabold tracking-tight text-slate-900">At a glance</h2><Notice error={d.error}/>{d.busy?<p className="py-6 text-sm text-slate-500">Loading your progress…</p>:<div className="grid gap-4 sm:grid-cols-2 xl:grid-cols-4">{stats.map(({label,value,icon:Icon,accent})=><article key={label} className="panel flex min-h-[146px] flex-col justify-between p-5 sm:p-6"><div className={`flex size-11 items-center justify-center rounded-2xl ${accent==='blue'?'bg-sky-50 text-sky-600':accent==='amber'?'bg-amber-50 text-amber-600':accent==='green'?'bg-emerald-50 text-emerald-600':'bg-violet-50 text-violet-600'}`}><Icon size={22}/></div><div><p className="mt-4 font-display text-2xl font-extrabold tracking-tight text-slate-900">{value}</p><p className="mt-1 text-xs font-semibold text-slate-500">{label}</p></div></article>)}</div>}</div>
   <div className="grid gap-5 lg:grid-cols-2"><section className="panel p-6 sm:p-7"><h2 className="font-display text-lg font-extrabold">Quick actions</h2><p className="mt-1 text-sm text-slate-500">Pick up right where you left off.</p><div className="mt-5 space-y-3">{(trainer?[["My courses","/trainer"],["Create MCQ assessments","/trainer-assessments"],["Publish roadmap","/roadmap"]]:[["Browse available courses","/enroll"],["Take course assessments","/student-assessments"],["Track your roadmap","/roadmap"]]).map(([text,path])=><Link key={path} to={path} className="group flex items-center justify-between gap-3 rounded-xl border border-violet-100 bg-violet-50/50 px-4 py-3.5 text-sm font-bold text-slate-700 transition hover:border-violet-200 hover:bg-violet-50 hover:text-violet-700">{text}<ArrowRight size={17} className="text-violet-500 transition group-hover:translate-x-1"/></Link>)}</div></section><section className="panel flex flex-col justify-between p-6 sm:p-7"><div><div className="mb-4 inline-flex size-12 items-center justify-center rounded-2xl bg-indigo-50 text-indigo-600"><GraduationCap size={24}/></div><h2 className="font-display text-xl font-extrabold">{trainer?'Teach with impact':'Interview-ready starts here'}</h2><p className="mt-3 text-sm leading-7 text-slate-500">{trainer?'Publish structured MCQ assessments and course roadmaps to help your students learn with clarity.':'Practice real, course-specific interview questions in a conversation with your AI interview coach.'}</p></div><Link to={trainer?'/roadmap':'/interview'} className="mt-7 inline-flex w-fit items-center gap-2 text-sm font-extrabold text-violet-600 hover:text-violet-800">{trainer?'Manage course roadmaps':'Start mock interview'} <ArrowRight size={17}/></Link></section></div>
 </>;
}

export function CourseEnrollment({session}){
 const catalog=useLoad('/workspace/catalog',session.token),my=useLoad('/workspace/my-courses',session.token),[error,setError]=useState(''),[busy,setBusy]=useState(null);
 const enrolled=new Set((my.data||[]).map(c=>c.id));async function enroll(id){setBusy(id);setError('');try{await api(`/workspace/courses/${id}/enroll`,{token:session.token,method:'POST'});my.reload()}catch(e){setError(e.message)}finally{setBusy(null)}}
 return <><Title title="Browse and enroll" subtitle="Enroll to access assessments, course roadmaps and AI mock interviews."/><Notice error={error||catalog.error||my.error}/><div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">{(catalog.data||[]).map(c=><Card key={c.id}><div className="flex justify-between"><BookOpen className="text-brand"/><span className="tag">{c.level||'Course'}</span></div><h3 className="mt-4 text-lg font-extrabold">{c.title}</h3><p className="mt-2 line-clamp-3 min-h-[58px] text-sm text-slate-500">{c.description}</p><p className="mt-3 text-xs text-slate-500">Trainer: {c.trainerName}</p><button className={`${enrolled.has(c.id)?'btn-outline':'btn-primary'} mt-5 w-full`} disabled={enrolled.has(c.id)||busy===c.id} onClick={()=>enroll(c.id)}>{enrolled.has(c.id)?'Enrolled ✓':busy===c.id?'Enrolling…':'Enroll in course'}</button></Card>)}</div>{!catalog.busy&&!catalog.data?.length&&<p>No courses have been created yet.</p>}</>;
}

export function MyLearning({session}){
 const courses=useLoad('/workspace/my-courses',session.token);const [active,setActive]=useState(null);return <><Title title="My courses" subtitle="Choose a course, complete trainer MCQ tests, and track topic progress."/><Notice error={courses.error}/><div className="grid gap-4 md:grid-cols-3">{(courses.data||[]).map(c=><button key={c.id} className={`panel p-5 text-left ${active===c.id?'ring-2 ring-indigo-500':''}`} onClick={()=>setActive(c.id)}><BookOpen className="text-brand"/><h3 className="mt-3 font-bold">{c.title}</h3><p className="mt-2 text-xs text-slate-500">View assessments →</p></button>)}</div>{active&&<Card><h3 className="font-bold">Ready to practice?</h3><p className="mt-2 text-sm text-slate-500">Complete trainer-created MCQ assessments, then track your roadmap.</p><div className="mt-4 flex gap-3"><Link className="btn-primary" to="/student-assessments">Take assessment</Link><Link className="btn-outline" to="/roadmap">Learning roadmap</Link></div></Card>} {!courses.busy&&!courses.data?.length&&<p className="text-slate-500">You haven't enrolled in any courses yet. <Link className="text-brand" to="/enroll">Browse courses</Link></p>}</>;
}

export function TrainerStudio({session}){
 const mine=useLoad('/workspace/trainer/courses',session.token);
 const [form,setForm]=useState({title:'',description:'',level:'Beginner'});
 const [error,setError]=useState(''),[message,setMessage]=useState(''),[busy,setBusy]=useState(false);
 async function createCourse(e){e.preventDefault();setBusy(true);setError('');setMessage('');try{
  await api('/workspace/trainer/courses',{token:session.token,method:'POST',body:form});
  setForm({title:'',description:'',level:'Beginner'});mine.reload();setMessage('Course created successfully.');
 }catch(e){setError(e.message)}finally{setBusy(false)}}
 return <><Title title="Trainer studio" subtitle="Create courses, then publish MCQ assessments and roadmap topics."/><Notice error={error||mine.error}/>
 {message&&<p className="mb-4 text-sm text-green-700">{message}</p>}
 <div className="grid gap-5 lg:grid-cols-2"><Card><h2 className="mb-4 text-lg font-extrabold">Create course</h2>
 <form onSubmit={createCourse} className="space-y-3"><input className="field" required placeholder="Course title" value={form.title} onChange={e=>setForm({...form,title:e.target.value})}/>
 <textarea className="field" placeholder="Course description" value={form.description} onChange={e=>setForm({...form,description:e.target.value})}/>
 <select className="field" value={form.level} onChange={e=>setForm({...form,level:e.target.value})}>{['Beginner','Intermediate','Advanced'].map(x=><option key={x}>{x}</option>)}</select>
 <Btn disabled={busy}><Plus size={16}/> Create course</Btn></form></Card>
 <Card><h2 className="text-lg font-extrabold">Your courses</h2>{(mine.data||[]).map(c=><div className="border-b py-3" key={c.id}><strong>{c.title}</strong><p className="text-sm text-slate-500">{c.level||'Course'}</p></div>)}{!mine.data?.length&&<p className="mt-3 text-sm text-slate-500">You have not created a course yet.</p>}</Card></div>
 <div className="mt-5 flex flex-wrap gap-3"><Link className="btn-primary" to="/trainer-assessments">Create MCQ assessments</Link><Link className="btn-outline" to="/roadmap">Publish roadmap topics</Link></div></>;
}


