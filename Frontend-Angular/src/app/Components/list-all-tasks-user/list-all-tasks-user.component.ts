import {AfterViewInit, Component, OnInit, TemplateRef} from '@angular/core';
import {Task} from "../../Models/task";
import {Subject, Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {User} from "../../Models/user";
import {TaskService} from "../../Services/task.service";
import {MemberService} from "../../Services/member.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {TokenStorageService} from "../../Services/Security/token-storage.service";

class DataTablesResponse {
  data?: any[];
  draw?: number;
  recordsFiltered?: number;
  recordsTotal?: number;
}
@Component({
  selector: 'app-list-all-tasks-user',
  templateUrl: './list-all-tasks-user.component.html',
  styleUrls: ['./list-all-tasks-user.component.css']
})
export class ListAllTasksUserComponent implements OnInit {
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
  constructor(private http:HttpClient,
              private taskservice:TaskService,
              private modalService: BsModalService,
              private memberservice:MemberService,
              private tokenStorage:TokenStorageService) { }

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
            'http://localhost:8085/api/tasks/member/'+this.tokenStorage.getId(),
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
    this.taskservice.getAllTasks().subscribe(
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



  saveEditedTask() {
    this.taskservice.updateTask(this.selectedTask).subscribe(
      response => {
        console.log("Task updated successfully");
        this.loadAll();
        this.modalRef?.hide();
        window.location.reload();
      },
      error => {
        console.log("Error updating task: ", error);
      }
    );
  }


// Check if a member is selected
  /*isMemberSelected(member: User): boolean {
    return this.selectedTask.members.some((selectedMember:any) => selectedMember.id === member.id);
  }


  toggleMemberSelection(member: User): void {
    const index = this.selectedTask.members.findIndex((selectedMember:any) => selectedMember.id === member.id);
    if (index > -1) {
      this.selectedTask.members.splice(index, 1);
    } else {
      this.selectedTask.members.push(member);
    }
  }*/


}
