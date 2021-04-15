import {Component, Injectable, Injector, OnInit, NgZone} from '@angular/core';
import {AuthService} from "./services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
@Injectable()
export class AppComponent {
  title = 'reconciliation-web-front';
  readonly AUTO_LOGOUT_TIME_IN_MINUTE: number  = 15;
  readonly TIME_INTERVAL: number = 10000;
  LAST_ACTIVE: number = 5;

  constructor(public authService: AuthService,
              private router: Router,
              private ngZone: NgZone
   ) {
    this.check();
    this.initListener();
    this.initInterval();
  }

initListener() {
    this.ngZone.runOutsideAngular(() => {
      document.body.addEventListener('click', () => this.reset());
    })
}

reset() {
  this.LAST_ACTIVE = Date.now();
}

initInterval() {
  this.ngZone.runOutsideAngular(() => {
      setInterval(() => {
        this.check();
      }, this.TIME_INTERVAL );

    });
}

check() {
  const now = Date.now();
    const timeLeft = this.LAST_ACTIVE + this.AUTO_LOGOUT_TIME_IN_MINUTE * 60 * 1000;
    const diff = timeLeft - now;
    const isTimeout = diff < 0;

    this.ngZone.run(() => {
      if( isTimeout && this.authService.isLoggedIn()) {
        console.log('You are idle so, auto logout');
        this.logout();
        this.router.navigate(['login']);

      }
    })
}

  logout() {
    this.authService.logoutUser();
  }

}
