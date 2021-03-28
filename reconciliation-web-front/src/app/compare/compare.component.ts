import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { Location } from '@angular/common';
import {Router} from "@angular/router";
import {FileHandleService} from "../services/file-handle.service";

@Component({
  selector: 'app-compare',
  templateUrl: './compare.component.html',
  styleUrls: ['./compare.component.css']
})
export class CompareComponent implements OnInit {
  form!: FormGroup;
  fileTypesList : string[] = ['Select File Type', 'JSON' , 'CSV'];

  sourceFileName = localStorage.getItem('sourceFileName');
  sourceFileType = localStorage.getItem('sourceFileType');
  targetFileName = localStorage.getItem('targetFileName');
  targetFileType = localStorage.getItem('targetFileType');




  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private fileHandleService: FileHandleService,
              private location: Location
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'fileType': this.formBuilder.control('', Validators.required),
    });
  }

  async submitForm() {
    console.log("request submitted for file comparison");
    if(this.form.valid) {
      this.fileHandleService.comparisonResult = await this.fileHandleService.doCompare().toPromise();
      console.log('Comparison Success Success');
      console.log('Result of comparison is '+JSON.stringify(this.fileHandleService.comparisonResult.MATCHING));
      this.router.navigate(['/result']);
    }

  }
  goBack() {
    this.location.back();
  }
}
