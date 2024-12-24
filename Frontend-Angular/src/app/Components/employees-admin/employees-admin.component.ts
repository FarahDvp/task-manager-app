import { Component, OnInit } from '@angular/core';
import {FormControlerService} from "../../Services/form-controles.service";
import {FormControl, FormGroup} from "@angular/forms";
import {MemberService} from "../../Services/member.service";
import {User} from "../../Models/user";

@Component({
  selector: 'app-employees-admin',
  templateUrl: './employees-admin.component.html',
  styleUrls: ['./employees-admin.component.css']
})
export class EmployeesAdminComponent implements OnInit {
listMembers:User []=[];
member:User=new User();
editMember :User=new User();
roleUser:string='';

  constructor(public formService:FormControlerService,
              private memberService :MemberService,
              ) { }

  ngOnInit(): void {
    this.memberService.getAllMembers().subscribe(
      data=>{
        console.log('data : ',data);
        this.listMembers=data as User[];
        console.log('list members : ',this.listMembers);
      });

  }

  onClear() {
    this.member=new User();
    this.editMember=new User();
    this.roleUser=''
    this.formService.formGroupAddEmp.reset();
  }
onSubmit() {
    if(this.formService.formGroupAddEmp.valid){
      console.log('form submitted');
      this.member.email=this.formService.formGroupAddEmp.value.emailEmp;
      this.member.fullname=this.formService.formGroupAddEmp.value.nameEmp;
      this.member.password=this.formService.formGroupAddEmp.value.passwordEmp;
      this.member.username=this.formService.formGroupAddEmp.value.usernameEmp;
      this.member.phone=this.formService.formGroupAddEmp.value.phoneEmp;
      this.member.role=this.roleUser;
      console.log('user to add :',this.member);
      this.memberService.addMember(this.member).subscribe(data=> {
        console.log('data : ', data);
        this.member = new User();
        setTimeout(() => {
          window.location.reload();
        }, 1000);
      },error => {
        console.log(error);
      })
    }else{
      console.log('invalid form');
      this.validateAllFormFields(this.formService.formGroupAddEmp);
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
    } );
  }

  getDetailsUser(id: any) {
    this.memberService.getMemberById(id).subscribe(data=>{
      console.log('data : ',data);
      this.member=data as User;
      this.formService.formGroupAddEmp.patchValue({
        nameEmp: this.member.fullname,
        emailEmp: this.member.email,
        usernameEmp:this.member.username,
        phoneEmp:this.member.phone,
        passwordEmp:this.member.password
      });
    },error => {
      console.log('error :',error);
    });

  }

  editUser() {
    if(this.formService.formGroupAddEmp.valid){
      this.editMember.email=this.formService.formGroupAddEmp.value.emailEmp;
      this.editMember.fullname=this.formService.formGroupAddEmp.value.nameEmp;
      this.editMember.username=this.formService.formGroupAddEmp.value.usernameEmp;
      this.editMember.password=this.formService.formGroupAddEmp.value.passwordEmp;
      this.editMember.phone=this.formService.formGroupAddEmp.value.phoneEmp;
      console.log('edit emp :',this.editMember);
      this.memberService.updateMember(this.editMember).subscribe(data =>{
        console.log('susses updating user :',this.editMember);

        setTimeout(() => {
          window.location.reload();
        }, 1000);

      }, error => {
        console.log(error);
      })
    }
    else {
      console.log('error updating...')
      this.validateAllFormFields(this.formService.formGroupAddEmp)
    }
  }

  deleteUser(id: any) {
    this.memberService.deleteMember(id).subscribe(()=>{
      console.log('user deleted !')

      setTimeout(() => {
        window.location.reload();
      }, 1000);    })

  }

  affecterRole(event: any) {
    this.roleUser = event.target.value;
    console.log('Rôle sélectionné :', this.roleUser);
  }


}
