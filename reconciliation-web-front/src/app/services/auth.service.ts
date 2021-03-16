import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient ) { }

  login(value: any) {
    return this.http.get<any>(`${environment.baseURL}/api/user/login?username=${value.username}&password=${value.password}`)
  }
}
