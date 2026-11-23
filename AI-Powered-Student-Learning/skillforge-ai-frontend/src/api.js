const base=(import.meta.env.VITE_API_URL||'').replace(/\/$/,'');
export async function api(path,{token,method='GET',body}={}){
 const response=await fetch(`${base}/api${path}`,{
  method,
  headers:{...(body!==undefined?{'Content-Type':'application/json'}:{}),...(token?{Authorization:`Bearer ${token}`}:{})},
  ...(body!==undefined?{body:JSON.stringify(body)}:{})
 });
 if(!response.ok){
  let detail='';
  try{
   const data=await response.json();
   // Spring ProblemDetail often places the useful reason in detail rather than error.
   const generic=new Set(['Bad Request','Forbidden','Internal Server Error','Not Found','Bad Gateway']);
   detail=[data.detail,data.message,data.error_description,data.error].find(v=>typeof v==='string' && v.trim() && !generic.has(v))||'';
  }catch{}
  if(!detail && response.status===400 && path.includes('/interviews/'))
   detail='Interview request was rejected (400). Select an enrolled course and try again. Check the Spring Boot console for request parsing errors.';
  if(!detail && response.status===403)
   detail='Access denied. Verify the account has permission to use this endpoint and sign in again if needed.';
  if(!detail && response.status===409)detail='This record already exists or conflicts with another record.';
  if(!detail)detail=`Request failed (${response.status} ${response.statusText})`;
  throw new Error(detail);
 }
 if(response.status===204)return null;
 return response.json();
}
