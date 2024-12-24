import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/Services/Security/token-storage.service';

@Component({
  selector: 'app-home-user',
  templateUrl: './home-user.component.html',
  styleUrls: ['./home-user.component.css']
})
export class HomeUserComponent implements OnInit {

  constructor(private tokenService: TokenStorageService,
    private router:Router) { }

ngOnInit(): void {
}

logOut(){
this.tokenService.signOut();
this.tokenService.remove();
this.router.navigate(['/']);
}

}
