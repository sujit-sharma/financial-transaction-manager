import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";

import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public userActivityResponse: any;
  constructor(private http: HttpClient,
              private router: Router) { }

  login(value: any) {
    return this.http.get<any>(`${environment.baseURL}/api/user/login?username=${value.username}&password=${value.password}`)
  }

  logoutUser() {
    localStorage.removeItem('authToken');
    this.router.navigate(['/login']);
  }
  isLoggedIn(): boolean {
    // return !!localStorage.getItem('authToken');
    const val = this.isTokenValid();
    return val;

  }

  private jwtDecode(token: string): object {
    return jwt_decode(token);
  }


  private isTokenValid(): boolean {
    let token = localStorage.getItem('authToken');
    if(token){
      const decodedToken:object = this.jwtDecode(token);
      const expiryTime: number = this.getTokenExpireTime(decodedToken);
      return ((1000 * expiryTime) - (new Date()).getTime()) > 5000;
    }
    console.log('cannot find token at local storage ');
    return false;
  }

  private getTokenExpireTime(decodedToken: object): number {
    // @ts-ignore
    return decodedToken.exp;
  }

  findUsersActivity() {
    this.http.get(`${environment.baseURL}/api/user/activity`)
      .subscribe((data) => {
        this.userActivityResponse = data;
        console.log("Request for User Activity" + JSON.stringify(this.userActivityResponse));
        this.router.navigate(['/usersActivity']);
      })

  }
}
