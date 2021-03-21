import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {FileHandleService} from "../services/file-handle.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-file-upload',
  templateUrl: './source.component.html',
  styleUrls: ['./source.component.css']
})
export class SourceComponent implements OnInit {
  form!: FormGroup;
  fileTypesList : string[] = ['Select File Type', 'JSON' , 'CSV'];

  selectedFile: any


  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private fileHandleService: FileHandleService,
              private http: HttpClient
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
      formData.append('isSource', 'true');
      formData.append('fileType', value.fileType);
      formData.append('file', this.selectedFile);
      const response = await this.fileHandleService.fileUpload(formData).toPromise();
      console.log('File Upload Success');
      localStorage.setItem('sourceFileName', value.displayName);
      localStorage.setItem('sourceFileType', value.fileType);
      this.router.navigate(['/targetFile']);
    }

  }

  upload(event:any){
    const file = event.target.files[0];
    console.log(file);
    this.selectedFile = file;
  }

}
