import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {SourceComponent} from "./source/source.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent},
  { path: 'sourceFile', component: SourceComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
