import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LabelService {
  private apiUrl='http://localhost:8085/api/labels';
  constructor(private http: HttpClient) { }

  addLabel(l:any){
    return this.http.post(this.apiUrl,l);
  }
  getLabelById(id :any){
    return this.http.get(this.apiUrl+'/'+id);
  }
  getAllLabels(){
    return this.http.get(this.apiUrl);
  }
  deleteLabel(id:number){
    return this.http.delete(this.apiUrl+'/'+id);
  }
}
