import { useEffect, useState } from 'react';
import { api } from '../api';
export function useApiData(path, token, enabled = true) {
 const [data, setData] = useState(null);
 const [loading, setLoading] = useState(true);
 const [error, setError] = useState('');
 const [revision, setRevision] = useState(0);
 useEffect(() => {
   if (!enabled || !path) { setLoading(false); return; }
   const controller = new AbortController();
   let alive = true;
   setLoading(true); setError('');
   api(path, { token }).then(result => { if (alive) setData(result); }).catch(err => { if (alive) setError(err.message); }).finally(() => { if (alive) setLoading(false); });
   return () => { alive = false; controller.abort(); };
 }, [path,token,enabled,revision]);
 return { data, loading, error, reload: () => setRevision(n => n+1) };
}
