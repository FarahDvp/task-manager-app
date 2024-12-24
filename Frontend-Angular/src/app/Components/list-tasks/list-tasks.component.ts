import {AfterViewInit, Component, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import {BsModalRef, BsModalService, ModalDirective} from 'ngx-bootstrap/modal';

import { Task } from '../../Models/task';
import { TaskService } from '../../Services/task.service';
import {Subject, Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {User} from "../../Models/user";
import {MemberService} from "../../Services/member.service";
class DataTablesResponse {
  data?: any[];
  draw?: number;  // Last Selected Page
  recordsFiltered?: number;
  recordsTotal?: number;
}
@Component({
  selector: 'app-list-tasks',
  templateUrl: './list-tasks.component.html',
  styleUrls: ['./list-tasks.component.css']
})
export class ListTasksComponent implements OnInit, OnDestroy, AfterViewInit {
  modalRef?: BsModalRef;
  tasks?: Task[];
  dtOptions: any = {};
  dtTrigger: Subject<Task> = new Subject();
  loadingData: boolean = false;
  request? : Subscription
  selectedTask: any = {};
  listMembers: User[] = [];
  listStatus: any = [{
    id: "TODO",
    name: 'Todo'
  }, {
    id: "DOING",
    name: 'Doing'
  }, {
    id: "DONE",
    name: 'Done'
  },
    {
      id: "ARCHIVED",
      name: 'Archived'
    },
    {
      id:"PENDING",
      name:'Pending'
    },
    {
      id:"IN_PROGRESS",
      name:'In Progress'
    }
  ];

  constructor(private router: Router,
              private taskService: TaskService,
              private http: HttpClient,
              private modalService: BsModalService,
              private memberService: MemberService
              ) {

  }
  ngAfterViewInit(): void {
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {

    this.memberService.getAllMembers().subscribe(
      response => {
        this.listMembers = response as User[];
      },
      error => {
        console.log(error);
      });
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
            'http://localhost:8085/api/tasks/search',
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






  loadAll() {
    this.taskService.getAllTasks().subscribe(
      response => {
        this.tasks = response as Task[];
        this.dtTrigger.next();
      },
      error => {
        console.log(error);
      });

  }

  openSeeTaskModal(task: Task) {
    this.selectedTask = task;
  }

  openEditTaskModal(template: TemplateRef<any>,task: Task) {

    this.selectedTask = task;
    console.log(this.selectedTask);
    this.modalRef = this.modalService.show(template);
  }

  openDeleteTaskModal(template: TemplateRef<any>,task: Task) {
    this.selectedTask = task;
    this.modalRef = this.modalService.show(template);
  }

  saveEditedTask() {
    this.taskService.updateTask(this.selectedTask).subscribe(
      response => {
        console.log("Task updated successfully");
        this.loadAll();
        this.modalRef?.hide();
      },
      error => {
        console.log("Error updating task: ", error);
      }
    );
  }

  MoveToTrash(id: any) {
    this.taskService.hideTask(id).subscribe(()=>{
      console.log('task hide succesfully !');
      setTimeout(() => {
        window.location.reload();
      }, 1000);
    },error => {
      console.log(error);

    })

  }


// Check if a member is selected
  isMemberSelected(member: User): boolean {
    return this.selectedTask.members.some((selectedMember:any) => selectedMember.id === member.id);
  }


  toggleMemberSelection(member: User): void {
    const index = this.selectedTask.members.findIndex((selectedMember:any) => selectedMember.id === member.id);
    if (index > -1) {
      this.selectedTask.members.splice(index, 1);
    } else {
      this.selectedTask.members.push(member);
    }
  }
}
