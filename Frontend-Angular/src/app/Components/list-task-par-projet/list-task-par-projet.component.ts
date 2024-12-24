import { Component, OnInit } from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from "@angular/cdk/drag-drop";
import {TaskService} from "../../Services/task.service";
import{Task} from "../../Models/task";
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../../Services/Security/token-storage.service";

@Component({
  selector: 'app-list-task-par-projet',
  templateUrl: './list-task-par-projet.component.html',
  styleUrls: ['./list-task-par-projet.component.css']
})
export class ListTaskParProjetComponent implements OnInit {
  listTasks :Task[]=[];
  listTaskToDO :Task[]=[];
  listTaskDone :Task[]=[];
  listTaskInProgress :Task[]=[];
  selectedTask :Task=new Task();
  idProject :any;

  constructor(private taskService:TaskService,
              private route: ActivatedRoute,
              private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.idProject = params['id'];
    });
    this.taskService.getTasksByProjectAndMemberId(this.idProject,this.tokenStorage.getId()).subscribe(data =>{
      this.listTasks=data as Task[];
      this.listTaskToDO=this.listTasks.filter(task => task.status === 'TODO' || task.status === 'PENDING');
      this.listTaskDone=this.listTasks.filter(task => task.status === 'DONE');
      this.listTaskInProgress=this.listTasks.filter(task => task.status === 'IN_PROGRESS' || task.status === 'DOING');

    })

  }
  drop(taskList: Task[], event: CdkDragDrop<Task[]>): void {
    console.log('Début de la fonction drop');

    if (event.previousContainer === event.container) {
      console.log('Condition 1');
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      console.log('Condition 2');
      console.log("task previous index", taskList[event.previousIndex]);

      const selectedTask = event.previousContainer.data[event.previousIndex];

      if (selectedTask && selectedTask.id !== undefined) {
        console.log('Tâche', selectedTask.id, 'déplacée vers la liste appropriée !');

        // Update the status of the selected task based on the container
        if (event.container.id === "todoList") {
          console.log('todoList1111');
          selectedTask.status = "TODO";
        } else if (event.container.id === "inProgressList") {
          console.log('inProgressList1111');
          selectedTask.status = "IN_PROGRESS";
        } else if (event.container.id === "doneList") {
          console.log('doneList1111');
          selectedTask.status = "DONE";
        }

        this.taskService.updateTask(selectedTask).subscribe(data => {
          console.log('data : ', data);
         // window.location.reload();
        }, error => {
          console.log('error : ', error);
        })
      } else {
        console.error('probléme rencontré lors de déplacement de la tâche.');
      }

      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    }
    console.log('Fin de la fonction drop');
    this.sortLists();
  }

/*  drop(taskList: Task[], event: CdkDragDrop<Task[]>): void {
    console.log('Début de la fonction drop');

    if (event.previousContainer === event.container) {
      console.log('Condition 1');
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      console.log('Condition 2');
      console.log("task previous index", taskList[event.previousIndex]);

      // Assign the selected task correctly
      const selectedTask = event.previousContainer.data[event.previousIndex];

      if (selectedTask && selectedTask.id !== undefined) {
        console.log('Tâche', selectedTask.id, 'déplacée vers la liste appropriée !');
        this.selectedTask = selectedTask;
      } else {
        console.error('La tâche déplacée ne contient pas une propriété "id" définie correctement.');
      }

      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    }
    console.log('Fin de la fonction drop');
    this.sortLists();
  }*/
  sortLists(): void {
    this.listTaskInProgress.sort();
    this.listTaskToDO.sort();
    this.listTaskDone.sort();
  }

  onClear() {
    this.selectedTask=new Task();
  }


  getDetailsTask(id: any) {
    this.taskService.taskById(id).subscribe(data=>{
      this.selectedTask=data as Task;
      console.log('selected task :',this.selectedTask);
    },error => {
      console.log(error);
    })

  }
}
