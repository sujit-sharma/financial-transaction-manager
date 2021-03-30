import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let reqWithToken = req.clone({
      setHeaders: {
        'Authorization': `Bearer ${localStorage.getItem('authToken')}`,

      }
    })
    return next.handle(reqWithToken);
  }
}
