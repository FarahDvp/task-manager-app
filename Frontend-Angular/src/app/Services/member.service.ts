import { Injectable } from '@angular/core';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {User} from "../Models/user";

const url = 'http://localhost:8085/api/members';
@Injectable({
  providedIn: 'root'
})
export class MemberService {

  constructor(private http:HttpClient) { }

public getAllMembers(){
  return this.http.get(url);
}
public getMemberById(id:number) {
  return this.http.get(url+'/'+id);
}
  public addMember(member:User) {
    return this.http.post(url, member);
  }
public updateMember(member:User) {
  return this.http.put(url, member);
}
public deleteMember(id:number) {
  return this.http.delete(url+'/'+id);
}
public affecterMemberToProject(memberId :number,projectId :number) {
  return this.http.put(url+'/'+memberId +'/assignProject/'+projectId ,null);
}
public getTaskMemberByUsername(username :string) {
    return this.http.get(url+'/tasks/'+username);
}

}
