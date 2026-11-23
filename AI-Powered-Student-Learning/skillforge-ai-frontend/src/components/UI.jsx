import { AlertCircle, LoaderCircle, BookOpen } from 'lucide-react';
export function PageHeader({ eyebrow = 'SKILLFORGE AI', title, description, action }) {
 return <div className="page-heading"><div><p className="eyebrow">{eyebrow}</p><h1 className="page-title">{title}</h1>{description && <p className="mt-2 max-w-2xl text-sm leading-6 text-slate-500 sm:text-[15px]">{description}</p>}</div>{action}</div>;
}
export function ErrorNotice({ message }) { return message ? <div role="alert" className="mb-5 flex items-start gap-3 rounded-2xl border border-rose-200 bg-rose-50 p-4 text-sm text-rose-700"><AlertCircle size={18} className="mt-0.5 shrink-0"/>{message}</div> : null; }
export function LoadingState() { return <div className="flex min-h-48 items-center justify-center text-violet-600"><LoaderCircle className="animate-spin" size={28}/><span className="ml-3 text-sm">Loading your workspace…</span></div>; }
export function EmptyState({ title = 'Nothing here yet', description='Content will appear here once available.' }) { return <div className="flex flex-col items-center py-14 text-center"><div className="mb-4 rounded-2xl bg-violet-50 p-4 text-violet-600"><BookOpen/></div><h3 className="font-bold text-slate-900">{title}</h3><p className="mt-2 max-w-md text-sm text-slate-500">{description}</p></div>; }
