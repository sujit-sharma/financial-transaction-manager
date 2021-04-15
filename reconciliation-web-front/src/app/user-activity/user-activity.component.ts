import {Component, Injectable, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {UserActivity} from "../services/entities";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-user-activity',
  templateUrl: './user-activity.component.html',
  styleUrls: ['./user-activity.component.css']
})
export class UserActivityComponent implements OnInit {


  userActivitySource: any;
  userActivity: UserActivity[] = [];
  displayColumns: string[] = ['activity', 'requestIpAddr', 'date'];

  constructor(private authService: AuthService) { }

  ngOnInit(): void {

  }

 async getUsersActivity(){
   this.authService.findUsersActivity()
     .subscribe(data => {
       this.userActivity = data;
       //  // @ts-ignore
       // this.userActivitySource = this.userActivity.pop();
       // @ts-ignore
       this.userActivity.forEach(value =>  {
         this.userActivitySource.push(value);
       });

       console.log("Users activity retrieved from backend");
       // console.log("User info is" + this.userActivitySource);
       // @ts-ignore
       // console.log('@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ' + this.userActivitySource.forEach(val => {
       //   console.log(val.hitInformation);
       // }));
     })


  }

}
