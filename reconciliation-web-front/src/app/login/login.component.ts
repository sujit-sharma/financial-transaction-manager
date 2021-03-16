import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public title = 'login';
  // @ts-ignore
  loginForm: FormGroup;
  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      'username': this.formBuilder.control('', [Validators.required]),
      'password': this.formBuilder.control('', Validators.required)
    });
  }
  async submitForm() {
    if (this.loginForm.valid ) {
      const response = await this.authService.login(this.loginForm.value).toPromise();
      localStorage.setItem('authToken', response.token);
      this.router.navigate(['/fileUpload']).then();
    }
  }

}
