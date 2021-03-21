import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {SourceComponent} from "./source/source.component";
import {AuthGuard} from "./services/auth.guard";
import {TargetComponent} from "./target/target.component";
import {NotFoundComponent} from "./not-found/not-found.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent},
  { path: 'sourceFile', component: SourceComponent, canActivate: [AuthGuard]},
  { path: 'targetFile', component: TargetComponent, canActivate: [AuthGuard]},
  { path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
