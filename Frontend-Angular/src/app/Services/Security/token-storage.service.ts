import { Injectable } from '@angular/core';
const TOKEN_KEY = 'access_token';
const USER_KEY = 'auth-user';
const ROLE_KEY = 'role-user';
@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }
  signOut(): void {
    window.localStorage.clear();
  }


  public savedata(token: any): void {
    const decodedToken = this.decode(token.access_token);
    console.log('decodedToken :', decodedToken);
    if (decodedToken) {
    localStorage.setItem(TOKEN_KEY, token.access_token);
    localStorage.setItem(ROLE_KEY, decodedToken.role); // role
    localStorage.setItem(USER_KEY, decodedToken.id); // id

  }

  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  getId(): number | null {
    const userId = localStorage.getItem(USER_KEY);
    return userId ? Number(userId) : null;
  }

  getRole(): string | null {
    return localStorage.getItem(ROLE_KEY);
  }




  remove(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    localStorage.removeItem(ROLE_KEY);
  }

  decode(payload: any): any {
    console.log('payload :', payload);
   let  x= JSON.parse(atob(this.payload(payload)));
    console.log('payload :', x);
    return x;
  }

  payload(token: string): any {
    console.log('token :', token);
   let x =  token.split('.')[1];
    console.log('x :', x);
    return x;
  }

  /*isValid(): boolean {
    const token = this.getToken();
    const id = this.getId();

    if (token && id) {
      const payload = this.payload(token);
      return id === payload.id;
    }

    return false;
  }*/



}
