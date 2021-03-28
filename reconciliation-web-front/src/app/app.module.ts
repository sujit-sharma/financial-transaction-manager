import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {ReactiveFormsModule} from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { SourceComponent } from './source/source.component';
import { TargetComponent } from './target-file/target.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { CompareComponent } from './compare/compare.component';
import { ResultComponent } from './result/result.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSortModule} from "@angular/material/sort";
import { Test1Component } from './test1/test1.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SourceComponent,
    TargetComponent,
    NotFoundComponent,
    CompareComponent,
    ResultComponent,
    Test1Component,


  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        MatTabsModule,
        MatTableModule,
        MatSortModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
