import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {Source} from "./source/source";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent},
  { path: 'fileUpload', component: Source }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
