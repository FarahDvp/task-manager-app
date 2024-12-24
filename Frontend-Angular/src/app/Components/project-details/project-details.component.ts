import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {FormControlerService} from "../../Services/form-controles.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectService} from "../../Services/project.service";
import {Projet} from "../../Models/projet";
import {Task} from "../../Models/task";
import {TaskService} from "../../Services/task.service";
import {MemberService} from "../../Services/member.service";
import {User} from "../../Models/user";
import {LabelService} from "../../Services/label.service";
import {Label} from "../../Models/label";
import _default from "chart.js/dist/plugins/plugin.legend";
import labels = _default.defaults.labels;

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css']
})
export class ProjectDetailsComponent implements OnInit {
  projetId: any;
  project: Projet = new Projet();
  listTask: Task[] = new Array<Task>();
  task: Task = new Task();
  editTask: Task = new Task();
  listMembers: User[] = [];
  currentOpenCollapseEdit: string | null = null;
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
  listLabels: Label[] = [];
    labelSelected: any;
  constructor(private projectService: ProjectService,
              private taskService: TaskService,
              private memberService: MemberService,
              private route: ActivatedRoute,
              public formService: FormControlerService,
              public labelService: LabelService,
  ) {

  }

  ngOnInit(): void {
    this.memberService.getAllMembers().subscribe(data => {
      this.listMembers = data as User[];
      console.log('list members : ', this.listMembers);
    });
    this.labelService.getAllLabels().subscribe(data => {
      this.listLabels = data as Label[];
      console.log('list labels : ', this.listLabels);
    });
    this.project = new Projet();
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.projetId = id;
      this.projectService.getProjectById(this.projetId).subscribe(data => {
        this.project = data as Projet;
        console.log('project  :', this.project);
        this.listTask = this.project.tasks.filter((task : Task) => task.status != 'ARCHIVED');


      });
    });
  }

  validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({onlySelf: true});
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }

  onSubmitTask() {
      this.task.description = this.formService.descriptionT?.value;
      this.task.title = this.formService.nameT?.value;
      this.task.projectId = this.projetId;
      this.task.priority = this.formService.periortyT?.value;
      this.task.startDate = this.formService.dateStartT?.value;
      this.task.dueDate = this.formService.dueDateT?.value;
      this.task.status = this.formService.statusT?.value;
     /* this.task.labels = this.formService.nameLabel?.value;
      this.task.members = this.formService.nameEmp?.value;*/
      alert('task : ' + this.task);
      console.log('task : ', this.task);
      this.taskService.addTask(this.task).subscribe(data => {
        this.listTask.push(data as Task);
        this.formService.formGroupAddTask.reset();
       // window.location.reload();
      }, error => {
        console.log(error);

      });
  }

  onClear() {
    this.formService.formGroupAddTask.reset();
  }

  getTaskById(id: any) {
    this.taskService.taskById(id).subscribe(data => {
        this.task = data as Task;
        console.log('task : ', this.task);
      this.formService.formGroupAddTask.patchValue({
        nameT: this.task.title,
        descriptionT: this.task.description,
        dateStartT:this.task.startDate,
        periortyT:this.task.priority,
        dueDateT:this.task.dueDate,
        statusT:this.task.status,

      });
      });

  }

  updateTask() {
      this.editTask.description = this.formService.descriptionT?.value;
      this.editTask.title = this.formService.nameT?.value;
      this.editTask.projectId = this.projetId;
      this.editTask.id = this.task.id;
      this.editTask.priority = this.formService.periortyT?.value;
      this.editTask.startDate = this.formService.dateStartT?.value;
      this.editTask.dueDate = this.formService.dueDateT?.value;
      this.editTask.status = this.formService.statusT?.value;
      this.editTask.members = this.task.members;
      this.editTask.labels = this.task.labels;


      this.taskService.updateTask(this.editTask).subscribe(data => {
        console.log('task updated');
          this.onClear();
        setTimeout(() => {
         window.location.reload();
        }, 150);
      });

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
  isMemberSelected(member: User): boolean {
    return this.task?.members?.some((selectedMember: User) => selectedMember.id === member.id) || false;
  }

  toggleMemberSelection(member: User): void {
    const isSelected = this.isMemberSelected(member);
    if (isSelected) {
      this.task!.members = this.task!.members!.filter((selectedMember: User) => selectedMember.id !== member.id);
    } else {
      this.task!.members!.push(member);
    }
  }
  setCurrentOpenCollapseEdit(collapseEditId: string | null): void {
    this.currentOpenCollapseEdit = collapseEditId;
  }
  isLabelSelected(label: Label): boolean {
    return this.task?.labels?.some((selectedLabel: Label) => selectedLabel.id === label.id) || false;
  }

  toggleLabelSelection(label: Label): void {
    const isSelected = this.isLabelSelected(label);
    if (isSelected) {
      this.task!.labels = this.task!.labels!.filter((selectedLabel: Label) => selectedLabel.id !== label.id);
    } else {
      this.task!.labels!.push(label);
    }
  }
  assignLabelToTask(idTask:number,idLabel:any){
    console.log('id task : ',idTask);
    console.log('id label : ',idLabel);
    this.taskService.assignLabelToTask(idTask,idLabel).subscribe(data=>{
      console.log('label assigned to task');
    },error => {
      console.log(error);
    })
  }

  updateSelectedLabel(label:any) {
    console.log('label selected : ',label);
   this.labelSelected = label;
  }

}
