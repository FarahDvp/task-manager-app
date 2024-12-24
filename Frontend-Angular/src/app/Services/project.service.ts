import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Projet} from "../Models/projet";
@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private apiUrl='http://localhost:8085/api/projects';

  constructor(private http: HttpClient) { }
  getProjectList() {
    return this.http.get(this.apiUrl);
  }
  updateProject(project :Projet) {
    return this.http.put(this.apiUrl,project);
  }

  deleteProject(id: number) {
    return this.http.delete(this.apiUrl+'/'+id);
  }
  getProjectById(id: number) {
    return this.http.get(this.apiUrl+'/'+id);
  }
  addProject(project :Projet) {
    return this.http.post(this.apiUrl,project);
  }

  getProjectByUser(id?: number | null) {
    return this.http.get(this.apiUrl+'/member/'+id);
  }
}
