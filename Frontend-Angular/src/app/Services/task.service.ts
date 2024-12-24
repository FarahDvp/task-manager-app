import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {TaskPagination} from "../Models/TaskPagination";

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl='http://localhost:8085/api/tasks';
  constructor(private http: HttpClient) { }
  getAllTasks(){
    return this.http.get(this.apiUrl);
  }
  updateTask(task:any){
    return this.http.put(this.apiUrl,task);
  }
  deleteTask(id:any){
    return this.http.delete(this.apiUrl+'/'+id);
  }
  addTask(task:any){
    return this.http.post(this.apiUrl,task);
  }

  hideTask(id:any){
    return this.http.put(this.apiUrl+'/'+id+'/to-trash',null);
  }
  fromTrashToListTask(id:any){
    return this.http.put(this.apiUrl+'/'+id+'/to-list-task',null);
  }

  taskById(id:any){
    return this.http.get(this.apiUrl+'/'+id);
  }

  getLabels(){
    return this.http.get(this.apiUrl+'/labels');
  }

  getTaskByStatusAndMemberId(status :string,memberId :number){
    return this.http.get(this.apiUrl+'/getByStatusAndMemberId?status='+status+'&memberId='+memberId);
  }
  getTaskByLabelId(labelId:any){
    return this.http.get(this.apiUrl+'/label/'+labelId+'/tasks');
  }



  assignLabelToTask(taskId: number, labelId: number) {
    return this.http.put(this.apiUrl + '/' + taskId + '/assignLabel/' + labelId, null);
  }

  getTasksByProjectAndMemberId(projectId: number, memberId: number | null) {
    return this.http.get(this.apiUrl + '/project/' + projectId + '/member/' + memberId);
  }

  getTasksBymemberId(memberId: number | null) {
    return this.http.get(this.apiUrl + '/member/' + memberId);
  }
}
