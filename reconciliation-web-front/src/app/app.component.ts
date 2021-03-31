import { Component, OnInit } from '@angular/core';
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'reconciliation-web-front';

  constructor(public authService: AuthService) {
  }

  logout() {
    this.authService.logoutUser();
  }
}
