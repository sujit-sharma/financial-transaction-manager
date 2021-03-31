import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    if(this.needSkip(req.url)){
      console.log('skipped by Interceptor for ' + req.url);
      return next.handle(req);
    }
    let reqWithToken = req.clone({
      setHeaders: {
        'Authorization': `Bearer ${localStorage.getItem('authToken')}`,

      }
    })
    console.log('Authorization token added in request by Interceptor');
    return next.handle(reqWithToken);
  }

  private needSkip(reqUrl: string): boolean {
    console.log('Checking Url to intercept request');
    let positionOfSkipUrl = reqUrl.indexOf('/login');
    return positionOfSkipUrl> 0;
  }
}
