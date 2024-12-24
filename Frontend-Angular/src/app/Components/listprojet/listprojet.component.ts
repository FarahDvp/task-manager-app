import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {FormControlerService} from "../../Services/form-controles.service";
import {FormControl, FormGroup} from "@angular/forms";
import {Projet} from "../../Models/projet";
import {ProjectService} from "../../Services/project.service";

@Component({
  selector: 'app-listprojet',
  templateUrl: './listprojet.component.html',
  styleUrls: ['./listprojet.component.css']
})
export class ListprojetComponent implements OnInit {

  project:Projet=new Projet();
  listProject:Projet[]=[];
  editProject:Projet=new Projet();

  constructor(private router :Router,
               public formService :FormControlerService,
              private projectService :ProjectService,
              ) { }

  ngOnInit(): void {
  this.getProjects();
  }

  getProjects() {
    this.projectService.getProjectList().subscribe(data => {

        this.listProject = data as Projet[];
      }
    );
  }
  seeProject(id: number) {
    console.log('id : ', id);
    this.router.navigate(['homeAdmin/detailsProject/',id]);
  }

  onSubmit() {
    if (this.formService.formGroupAddProject.valid) {
      console.log("Valid form");
      this.project.description = this.formService.formGroupAddProject.value.descriptionP;
      this.project.name = this.formService.formGroupAddProject.value.nameP;
      console.log('project : ', this.project);
      this.projectService.addProject(this.project).subscribe(data => {
          console.log('data : ', data);
          this.formService.formGroupAddProject.reset();
          this.project = new Projet();
          window.location.reload();
        }, error => {
          console.log('error : ', error);

        }
      );
    } else {
      console.log("Invalid form");
      console.log(this.formService.formGroupAddProject.controls.validate);
      this.validateAllFormFields(this.formService.formGroupAddProject);
    }

  }
  validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }
  onCancel() {
    this.formService.formGroupAddProject.reset();
    this.editProject = new Projet();
    this.project = new Projet();

  }

  deleteProject(id: number) {
    this.projectService.deleteProject(id).subscribe(()=> {
      console.log('success delete !');
      window.location.reload();
    }, error => {
      console.log('error : ', error);
    }
    );
  }
getProjectById(id: number) {
    this.projectService.getProjectById(id).subscribe(data => {
        console.log('data : ', data);
        this.editProject = data as Projet;
        console.log('editProject : ', this.editProject);
      this.formService.formGroupAddProject.patchValue({
        nameP: this.editProject.name,
        descriptionP: this.editProject.description,
      });
      }, error => {
        console.log('error : ', error);

      }
    );
}
  updateProject(id: number) {
    if (this.formService.formGroupAddProject.valid) {
      console.log("Valid form");
      console.log('form group value : ',this.formService.formGroupAddProject.value);
      this.editProject.description = this.formService.formGroupAddProject.value.descriptionP;
      this.editProject.name = this.formService.formGroupAddProject.value.nameP;
      console.log('project to update : ', this.editProject);
      this.projectService.updateProject(this.editProject).subscribe(data => {
          console.log('data api: ', data);
          this.formService.formGroupAddProject.reset();
          window.location.reload();


        }, error => {
          console.log('error : ', error);

        }
      );
    } else {
      console.log("Invalid form");
      this.validateAllFormFields(this.formService.formGroupAddProject);
    }
  }
}
