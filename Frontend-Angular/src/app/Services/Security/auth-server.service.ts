import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Login} from "../../Models/login";
import {User} from "../../Models/user";
import {Register} from "../../Models/register";

@Injectable({
  providedIn: 'root'
})
export class AuthServerService {

  constructor(private http: HttpClient) { }


  login(l:Login){
    return this.http.post('http://localhost:8085/api/auth/authenticate',l);
  }
  register(u:Register){
    return this.http.post('http://localhost:8085/api/auth/register',u);
  }
}
