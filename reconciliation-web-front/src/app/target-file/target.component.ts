import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { Location } from '@angular/common';
import {Router} from "@angular/router";
import {FileHandleService} from "../services/file-handle.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-target',
  templateUrl: './target-file.component.html',
  styleUrls: ['./target-file.component.css']
})
export class TargetComponent implements OnInit {
  form!: FormGroup;
  fileTypesList : string[] = ['Select File Type', 'JSON' , 'CSV'];

  selectedFile: any

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private fileHandleService: FileHandleService,
              private location: Location
  ) { }
  ngOnInit(): void {
    this.form = this.formBuilder.group( {
      'displayName': this.formBuilder.control('', Validators.required),
      'fileType': this.formBuilder.control('', Validators.required),
      'file': this.formBuilder.control('')

    });

  }

  async submitForm(event: any) {
    console.log("File upload form submitted")
    console.log(this.selectedFile);
    if(this.form.valid) {
      const value = this.form.value;
      const formData = new FormData();
      formData.append('displayName', value.displayName);
      formData.append('isSource', 'false');
      formData.append('fileType', value.fileType);
      formData.append('file', this.selectedFile);
      const response = await this.fileHandleService.fileUpload(formData).toPromise();
      console.log('File Upload Success');
      localStorage.setItem('targetFileName', value.displayName);
      localStorage.setItem('targetFileType', value.fileType);
      this.router.navigate(['/compare']);
    }

  }

  upload(event:any){
    const file = event.target.files[0];
    console.log(file);
    this.selectedFile = file;
  }

  goBack(): void {
      this.location.back();
  }
}
