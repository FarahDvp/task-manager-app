import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {FormControlerService} from "../../Services/form-controles.service";
import {FormControl, FormGroup} from "@angular/forms";
import {User} from "../../Models/user";
import {MemberService} from "../../Services/member.service";
import {AuthServerService} from "../../Services/Security/auth-server.service";
import {Register} from "../../Models/register";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  member:Register=new Register();
  constructor(public formService:FormControlerService,
              private router:Router,
              private authService :AuthServerService,
  ) { }
  ngOnInit(): void {
  }
  goToLogin() {
    this.formService.formGroupAddEmp.reset();
    this.router.navigate(['']);

  }
  validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    } );
  }

  onClear() {
    this.member=new Register();
    this.formService.formGroupAddEmp.reset();
  }
  onSubmit() {
    if(this.formService.formGroupAddEmp.valid){
      console.log('form submitted');
      this.member.email=this.formService.emailEmp?.value;
      this.member.fullname=this.formService.nameEmp?.value;
      this.member.password=this.formService.passwordEmp?.value;
      this.member.userName=this.formService.usernameEmp?.value;
      this.member.phone=this.formService.phoneEmp?.value;
      this.member.role="USER";
      console.log('user to add :',this.member);
      this.authService.register(this.member).subscribe(data=> {
        console.log('data : ', data);
        this.member = new Register();
        setTimeout(() => {
          this.goToLogin();
        }, 1000);
      },error => {
        console.log(error);
      })
    }else{
      console.log('invalid form');
      this.validateAllFormFields(this.formService.formGroupAddEmp);
    }
  }
}
