import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { TaskService } from '../../Services/task.service';
import { Task } from '../../Models/task';
import { NavigationEnd, Router } from '@angular/router';

import * as $ from 'jquery';
import {Subject, Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";

class DataTablesResponse {
  data?: any[];
  draw?: number;
  recordsFiltered?: number;
  recordsTotal?: number;
}
@Component({
  selector: 'app-hiden-tasks-admin',
  templateUrl: './hiden-tasks-admin.component.html',
  styleUrls: ['./hiden-tasks-admin.component.css'],
})
export class HidenTasksAdminComponent implements OnInit {

  tasks?: Task[];
  dtOptions: any = {};
  dtTrigger: Subject<Task> = new Subject();
  loadingData: boolean = false;
  request? : Subscription
  constructor(private router: Router, private taskService: TaskService,private http:HttpClient) {}


  ngOnInit(): void {
    let lastPage=0;
    let lastSearchText="";

    this.dtOptions = {

      pagingType: 'full_numbers',
      pageLength: 3,
      displayStart:lastPage, // Last Selected Page
      search:{search:lastSearchText}, // Last Searched Text
      serverSide: true,
      processing: true,
      lengthMenu : [5, 10, 25],

      ajax: (dataTablesParameters: any, callback:any) => {
        lastPage=dataTablesParameters.start;  // Note :  dataTablesParameters.start = page count * table length
        lastSearchText=dataTablesParameters.search.value;
        this.loadingData = true;
        this.request= this.http
          .post<DataTablesResponse>(
            'http://localhost:8085/api/tasks/archived',
            dataTablesParameters, {}
          ).subscribe(resp => {
            this.loadingData = false;
            this.tasks = resp.data;

            callback({
              recordsTotal: resp.recordsTotal,
              recordsFiltered: resp.recordsFiltered,
              data: []
            });
          });
      },
      columns: [
        { data: 'id' },
        { data: 'title' },
        { data: 'description' },
        { data: 'startDate' },
        { data: 'dueDate' },
        { data: 'status' },

      ],

    };
  }
  deleteTask(id: number) {
    this.taskService.deleteTask(id).subscribe(data => {
      console.log('task deleted');
      window.location.reload();
    });

  }

 /* loadAll() {
    this.taskService.getArchivedTasks().subscribe(
      response => {
        this.tasks = response as Task[];
        this.dtTrigger.next();
      },
      error => {
        console.log(error);
      });

  }*/
 cancelHidden(id: number): void {
    this.taskService.fromTrashToListTask(id).subscribe(
      (data) => {
      window.location.reload();
        },
      (error) => {
        console.log(error);
      }
    );
  }
}
