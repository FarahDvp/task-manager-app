import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AuthInspecterService, authInterceptorProviders} from "./Services/Security/Helper/auth-inspecter.service";

import {MatDialogModule} from "@angular/material/dialog";
import {DragDropModule} from "@angular/cdk/drag-drop";
import { HomeComponent } from './Components/home/home.component';
import { ListprojetComponent } from './Components/listprojet/listprojet.component';
import { ListTasksComponent } from './Components/list-tasks/list-tasks.component';
import { HomeUserComponent } from './Components/home-user/home-user.component';
import { ListProjetUserComponent } from './Components/list-projet-user/list-projet-user.component';
import { ListAllTasksUserComponent } from './Components/list-all-tasks-user/list-all-tasks-user.component';
import { ListTaskParProjetComponent } from './Components/list-task-par-projet/list-task-par-projet.component';
import { DashboardAdminComponent } from './Components/dashboard-admin/dashboard-admin.component';
import { DashboardUserComponent } from './Components/dashboard-user/dashboard-user.component';
import { ProjectDetailsComponent } from './Components/project-details/project-details.component';
import { LoginComponent } from './Components/login/login.component';
import { HidenTasksAdminComponent } from './Components/hiden-tasks-admin/hiden-tasks-admin.component';
import { EmployeesAdminComponent } from './Components/employees-admin/employees-admin.component';
import {DataTablesModule} from "angular-datatables";
import {BsModalService} from "ngx-bootstrap/modal";
import { LabelsComponent } from './Components/labels/labels.component';
import { SignUpComponent } from './Components/sign-up/sign-up.component';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ListprojetComponent,
    ListTasksComponent,
    HomeUserComponent,
    ListProjetUserComponent,
    ListAllTasksUserComponent,
    ListTaskParProjetComponent,
    DashboardAdminComponent,
    DashboardUserComponent,
    ProjectDetailsComponent,
    LoginComponent,
    HidenTasksAdminComponent,
    EmployeesAdminComponent,
    LabelsComponent,
    SignUpComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatDialogModule,
    DragDropModule,
    DataTablesModule,



  ],
  providers: [authInterceptorProviders,BsModalService],
  bootstrap: [AppComponent]
})
export class AppModule { }
