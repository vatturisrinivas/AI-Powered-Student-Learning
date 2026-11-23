import { createContext, useContext, useEffect, useState } from 'react';
import { api } from '../api';

const AuthContext = createContext(null);
const STORAGE_KEY = 'sf_session';

export function AuthProvider({ children }) {
  const [session, setSession] = useState(() => {
    try { return JSON.parse(localStorage.getItem(STORAGE_KEY) || localStorage.getItem('ss_session') || 'null'); }
    catch { return null; }
  });
  const save = (value) => {
    setSession(value);
    if (value) localStorage.setItem(STORAGE_KEY, JSON.stringify(value));
    else { localStorage.removeItem(STORAGE_KEY); localStorage.removeItem('ss_session'); }
  };
  useEffect(() => {
    if (!session?.token) return;
    let cancelled = false;
    api('/auth/me', { token: session.token })
      .then(user => {
        if (cancelled) return;
        setSession(old => {
          if (!old || old.token !== session.token) return old;
          const next = { ...old, ...user };
          localStorage.setItem(STORAGE_KEY, JSON.stringify(next));
          return next;
        });
      })
      .catch(() => { if (!cancelled) save(null); });
    return () => { cancelled = true; };
  }, [session?.token]);
  return <AuthContext.Provider value={{ session, save, logout: () => save(null) }}>{children}</AuthContext.Provider>;
}
export function useAuth() { const value = useContext(AuthContext); if (!value) throw Error('AuthProvider missing'); return value; }
