import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

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
    return !!localStorage.getItem('authToken');

  }
}
