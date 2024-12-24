import { Component, OnInit } from '@angular/core';
import {Label} from "../../Models/label";
import {FormControlerService} from "../../Services/form-controles.service";
import {FormControl, FormGroup} from "@angular/forms";
import {LabelService} from "../../Services/label.service";

@Component({
  selector: 'app-labels',
  templateUrl: './labels.component.html',
  styleUrls: ['./labels.component.css']
})
export class LabelsComponent implements OnInit {

  label:Label=new Label();
  listLabels:Label[]=[];

  constructor(public formService:FormControlerService,
              private labelService:LabelService) { }

  ngOnInit(): void {
    this.labelService.getAllLabels().subscribe(data =>{
      console.log('data : ',data);
      this.listLabels=data as Label[];
      console.log('list labels :',this.listLabels);
    })
  }
  onCancel() {
    this.label=new Label();
    this.formService.formGroupAddEmp.reset();
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
  onSubmit() {
    console.log('hello 1');
    if(this.formService.formGroupLabel.valid){
      console.log('hello 2 : all valide');
      this.label.name=this.formService.formGroupLabel.value.nameLabel;
      console.log('label',this.label);
      console.log('hello 3 :',this.label);
      this.labelService.addLabel(this.label).subscribe(data=>{
        console.log('data :',data);
        console.log('hello 4 : api done ');
        setTimeout(()=>{
          this.onCancel();
          window.location.reload();
        },1000)
      },error => {
        console.log('error :',error);
        console.log('hello 4 : failed api ');
      })
    }else{
      this.validateAllFormFields(this.formService.formGroupLabel);
      console.log('hello 2 : failed form ');
    }

  }

  deleteLabel(id: any) {
    this.labelService.deleteLabel(id).subscribe(()=>{
      console.log('deleting succesfull !');
      window.location.reload();
    })

  }

  getLabelById(id: any) {
    this.labelService.getLabelById(id).subscribe(data =>{
      this.label=data as Label;
      console.log('label selected :',this.label);
    })

  }
}
