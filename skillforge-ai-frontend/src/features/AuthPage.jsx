import { useState } from 'react';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { ArrowRight, BookOpen, BrainCircuit, CheckCircle2, Eye, EyeOff, ShieldCheck, Sparkles } from 'lucide-react';
import Brand from '../components/Brand';
import { ErrorNotice } from '../components/UI';
import { useAuth } from '../context/AuthContext';
import { api } from '../api';
export default function AuthPage({register=false}) {
 const {session, save} = useAuth(); const navigate=useNavigate();
 const [form,setForm]=useState({name:'',email:'',password:''});
 const [busy,setBusy]=useState(false); const [error,setError]=useState(''); const [showPassword,setShowPassword]=useState(false);
 if(session?.token) return <Navigate to={session.role==='ADMIN'?'/admin-portal':session.role==='TRAINER'?'/trainer':'/dashboard'} replace/>;
 async function submit(e){e.preventDefault();setBusy(true);setError('');try {const user=await api(`/auth/${register?'register':'login'}`,{method:'POST',body:register?form:{email:form.email,password:form.password}});save(user);navigate(user.role==='ADMIN'?'/admin-portal':user.role==='TRAINER'?'/trainer':'/dashboard',{replace:true});} catch(e){setError(e.message);}finally{setBusy(false);}}
 return <div className="auth-shell">
   <div className="auth-form-side"><div className="mx-auto flex min-h-screen w-full max-w-[490px] flex-col px-5 py-7 sm:px-10 sm:py-10"><Brand/>
      <div className="my-auto py-12"><div className="mb-5 inline-flex items-center gap-2 rounded-full border border-violet-100 bg-violet-50 px-3 py-1.5 text-xs font-bold text-violet-700"><Sparkles size={15}/> Your next chapter starts here</div>
        <h1 className="font-display text-[clamp(2rem,4vw,3.25rem)] font-extrabold leading-[1.12] tracking-[-.055em] text-slate-950">{register?'Build your future.':'Welcome back to'} <span className="text-violet-600">{register?'Start learning.':'SkillForge AI.'}</span></h1>
        <p className="mt-4 max-w-sm text-sm leading-7 text-slate-500">{register?'Create a student account to explore courses, take trainer-made assessments, and practice interviews.':'Sign in to continue learning, teaching, or managing your platform.'}</p>
        <form onSubmit={submit} className="mt-9 space-y-5">
          {register&&<label className="block"><span className="form-label">Full name</span><input className="field" required autoComplete="name" value={form.name} placeholder="Enter your full name" onChange={e=>setForm({...form,name:e.target.value})}/></label>}
          <label className="block"><span className="form-label">Email address</span><input className="field" type="email" autoComplete="email" required placeholder="you@example.com" value={form.email} onChange={e=>setForm({...form,email:e.target.value})}/></label>
          <label className="block"><span className="form-label">Password</span><span className="relative block"><input className="field pr-12" type={showPassword?'text':'password'} autoComplete={register?'new-password':'current-password'} required minLength={register?8:undefined} placeholder="Enter your password" value={form.password} onChange={e=>setForm({...form,password:e.target.value})}/><button type="button" aria-label={showPassword?'Hide password':'Show password'} onClick={()=>setShowPassword(x=>!x)} className="absolute right-3 top-1/2 -translate-y-1/2 rounded-lg p-2 text-slate-400 hover:text-slate-600">{showPassword?<EyeOff size={18}/>:<Eye size={18}/>}</button></span>{register&&<span className="mt-1.5 block text-xs text-slate-400">Use at least 8 characters.</span>}</label>
          <ErrorNotice message={error}/>
          <button disabled={busy} className="btn-primary min-h-12 w-full">{busy?'Please wait…':register?'Create student account':'Sign in to SkillForge'}<ArrowRight size={17}/></button>
        </form>
        <p className="mt-7 text-center text-sm text-slate-500">{register?'Already have an account?':'New here?'} <Link className="font-extrabold text-violet-600 hover:underline" to={register?'/login':'/register'}>{register?'Sign in':'Create a student account'}</Link></p>
        <p className="mt-7 text-center text-xs leading-5 text-slate-400">Admin and trainer accounts sign in here too.<br/>Only students can create accounts through registration.</p>
      </div><p className="text-xs text-slate-400">© {new Date().getFullYear()} SkillForge AI. Built for curious minds.</p>
   </div></div>
   <div className="auth-showcase"><div className="auth-showcase-ambient auth-orb-one"/><div className="auth-showcase-ambient auth-orb-two"/><div className="relative z-10 flex h-full max-w-xl flex-col justify-center"><span className="mb-8 inline-flex w-fit items-center gap-2 rounded-full border border-white/20 bg-white/10 px-4 py-2 text-xs font-bold text-white/90"><Sparkles size={14}/> YOUR LEARNING, REIMAGINED</span><h2 className="font-display text-[clamp(2.8rem,4.1vw,4.5rem)] font-extrabold leading-[1.08] tracking-[-.06em]">More than learning.<br/><span className="text-[#c9baff]">A future in the making.</span></h2><p className="mt-6 max-w-md text-base leading-8 text-indigo-100/90">Learn from expert trainers, track every milestone, and sharpen your interview skills with AI-powered conversations.</p>
   <div className="mt-12 grid grid-cols-2 gap-4"><div className="auth-benefit"><BookOpen size={22}/><strong>Learn with purpose</strong><span>Structured courses and roadmaps</span></div><div className="auth-benefit"><BrainCircuit size={22}/><strong>Practice with AI</strong><span>Real interview-style conversations</span></div><div className="auth-benefit"><CheckCircle2 size={22}/><strong>Prove your progress</strong><span>Instantly graded MCQ assessments</span></div><div className="auth-benefit"><ShieldCheck size={22}/><strong>Grow together</strong><span>Trainer-guided learning experiences</span></div></div>
   </div></div>
 </div>;
}
