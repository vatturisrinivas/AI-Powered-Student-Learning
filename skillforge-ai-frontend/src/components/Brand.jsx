import { Sparkles } from 'lucide-react';
import { Link } from 'react-router-dom';
export default function Brand({ inverse = false }) {
  return <Link to="/dashboard" className="inline-flex min-w-0 items-center gap-3" aria-label="SkillForge AI home">
    <span className="flex h-11 w-11 shrink-0 items-center justify-center rounded-2xl bg-gradient-to-br from-violet-500 to-indigo-700 text-white shadow-lg shadow-indigo-500/20"><Sparkles size={22} strokeWidth={2.3}/></span>
    <span className="leading-tight"><span className={`block text-[20px] font-extrabold tracking-[-.055em] ${inverse?'text-white':'text-slate-900'}`}>SkillForge <span className="text-violet-500">AI</span></span><span className={`block text-[9px] font-bold uppercase tracking-[.25em] ${inverse?'text-indigo-200':'text-slate-400'}`}>LEARN · PRACTICE · GROW</span></span>
  </Link>;
}
