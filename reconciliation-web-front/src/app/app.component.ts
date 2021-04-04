import {Component, Injectable, Injector, OnInit} from '@angular/core';
import {AuthService} from "./services/auth.service";
import { UserActivityComponent } from "./user-activity/user-activity.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
@Injectable()
export class AppComponent {
  title = 'reconciliation-web-front';

  constructor(public authService: AuthService,
              ) {
  }

  logout() {
    this.authService.logoutUser();
  }
}
