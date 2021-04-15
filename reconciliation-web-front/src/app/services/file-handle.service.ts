import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {FileUploadEntity} from "./entities";

@Injectable({
  providedIn: 'root'
})
export class FileHandleService {
  token: any = localStorage.getItem('authToken');

  constructor(private http: HttpClient

  ) { }
  comparisonResult: any;

  httpOptions = {
    headers: new HttpHeaders( {
      Authorization: this.token
    })
  };

  public fileUpload(formData: FormData) {

    console.log('Processing for post request to backend with token' + this.token)
    return this.http.post<FileUploadEntity>(`${environment.baseURL}/api/fileUpload`, formData)
  }

   doCompare() {
    return this.http.get(`${environment.baseURL}/api/compare`);
  }
}


