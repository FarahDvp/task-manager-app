import { Injectable } from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormControlerService {

  constructor(private fb: FormBuilder,
              private fbAddProject: FormBuilder,
              private fbAddTask: FormBuilder,
              private fbAddEmp: FormBuilder,
              private fbLabel: FormBuilder,

  ) { }

  formGroupLogin = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });
  formGroupAddProject = this.fbAddProject.group({
    nameP: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20),Validators.pattern('[a-zA-Z ]*')]],
    descriptionP: ['', [Validators.required]],
  });
  formGroupAddTask = this.fbAddTask.group({
    nameT: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30),Validators.pattern('[a-zA-Z ]*')]],
    descriptionT: ['', [Validators.required]],
    dateStartT: [null, Validators.required],
    dueDateT: [null],
    statusT: ['', Validators.required],
    periortyT: ['',Validators.required],


  });
  formGroupAddEmp = this.fbAddEmp.group({
    nameEmp: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20),Validators.pattern('[a-zA-Z ]*')]],
    emailEmp: ['', [Validators.required, Validators.email]],
    usernameEmp: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    phoneEmp: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8),Validators.pattern('[0-9]*')]],
    passwordEmp: ['', [Validators.required,Validators.minLength(3), Validators.maxLength(20)]],

  });
  formGroupLabel = this.fbLabel.group({
    nameLabel: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20),Validators.pattern('[a-zA-Z ]*')]],
  });
  get nameLabel(){
    return this.formGroupLabel.get('nameLabel')
  }
  /* add employee controle */
  get nameEmp() {
    return this.formGroupAddEmp.get('nameEmp');
  }
  get usernameEmp() {
    return this.formGroupAddEmp.get('usernameEmp');
  }
  get emailEmp() {
    return this.formGroupAddEmp.get('emailEmp');
  }
  get phoneEmp() {
    return this.formGroupAddEmp.get('phoneEmp');
  }
  get passwordEmp() {
    return this.formGroupAddEmp.get('passwordEmp');
  }
  /* fin add employee controle */


  /* add project controle */
  get nameP() {
    return this.formGroupAddProject.get('nameP');
  }

  get descriptionP() {
    return this.formGroupAddProject.get('descriptionP');
  }
   get ownerP() {
    return this.formGroupAddProject.get('ownerP');
   }
/* fin add project controle */
  /* login controle */
  get username() {
    return this.formGroupLogin.get('username');
  }
  get password() {
    return this.formGroupLogin.get('password');
  }
  /* fin login controle */
  /* add task */
  get nameT() {
    return this.formGroupAddTask.get('nameT');
  }
  get descriptionT() {
    return this.formGroupAddTask.get('descriptionT');
  }
  get dateStartT() {
    return this.formGroupAddTask.get('dateStartT');
  }
  get periortyT() {
    return this.formGroupAddTask.get('periortyT');
  }


  get dueDateT() {
    return this.formGroupAddTask.get('dueDateT');
  }
  get statusT() {
    return this.formGroupAddTask.get('statusT');
  }


}
