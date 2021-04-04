import {Component, Injectable, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-user-activity',
  templateUrl: './user-activity.component.html',
  styleUrls: ['./user-activity.component.css']
})
export class UserActivityComponent implements OnInit {
  userActivitySource: any;
  displayColumns: string[] = ['activity', 'requestIpAddr', 'date'];

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.getUsersActivity();
  }

 public getUsersActivity(){
    this.userActivitySource = this.authService.userActivityResponse;
    console.log("Users activity retrieved from backend");
    console.log("User info is" + JSON.stringify(this.userActivitySource));
  }

}
