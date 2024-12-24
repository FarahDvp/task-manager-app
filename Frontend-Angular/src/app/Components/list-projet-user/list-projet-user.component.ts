import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Projet} from "../../Models/projet";
import {ProjectService} from "../../Services/project.service";
import {TokenStorageService} from "../../Services/Security/token-storage.service";

@Component({
  selector: 'app-list-projet-user',
  templateUrl: './list-projet-user.component.html',
  styleUrls: ['./list-projet-user.component.css']
})
export class ListProjetUserComponent implements OnInit {

  listProject : Projet []=[];
  constructor(private router :Router,
              private projectService: ProjectService,
              private token_storage:TokenStorageService) { }

  ngOnInit(): void {
    this.getProjects();
  }
  getProjects() {
    this.projectService.getProjectByUser(this.token_storage.getId()).subscribe(data => {

        this.listProject = data as Projet[];
      }
    );
  }
  seeTaskProjet(id: number) {
    this.router.navigate(['homeUser/listTasksParProject/'+id]);

  }

}
