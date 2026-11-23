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
   detail=data.detail||data.message||data.error||'';
  }catch{}
  if(response.status===403 && !detail)detail='Access denied. Sign out and log in with an ADMIN account. If the Users list also fails, check the token and role.';
  if(response.status===400 && !detail)detail='Invalid request. Trainer name, valid email, and password with at least 10 characters are required.';
  if(response.status===409 && !detail)detail='This email is already registered.';
  throw new Error(detail||`Request failed (${response.status})`);
 }
 if(response.status===204)return null;
 return response.json();
}
