import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {SourceComponent} from "./source/source.component";
import {AuthGuard} from "./services/auth.guard";
import {TargetComponent} from "./target-file/target.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {CompareComponent} from "./compare/compare.component";
import {ResultComponent} from "./result/result.component";
import { UserActivityComponent } from "./user-activity/user-activity.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent},
  { path: 'sourceFile', component: SourceComponent, canActivate: [AuthGuard]},
  { path: 'targetFile', component: TargetComponent, canActivate: [AuthGuard]},
  { path: 'compare', component: CompareComponent, canActivate: [AuthGuard] },
  { path: 'result', component: ResultComponent , canActivate: [AuthGuard]},
  { path: 'usersActivity', component: UserActivityComponent, canActivate: [AuthGuard]},

  { path: '**', pathMatch: 'full', component: NotFoundComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
