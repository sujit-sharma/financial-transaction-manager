import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {FileHandleService} from "../services/file-handle.service";

@Component({
  selector: 'app-file-upload',
  templateUrl: './source.component.html',
  styleUrls: ['./source.component.css']
})
export class SourceComponent implements OnInit {
  // @ts-ignore
  form: FormGroup;
  fileTypesList : string[] = ['Select File Type', 'JSON' , 'CSV'];
  fileName = '';
  fileToUpload: File | null | undefined;
  public formData = new FormData();
  ReqJson: any = {};

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private fileHandleService: FileHandleService
              ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group( {
      'displayName': this.formBuilder.control('', Validators.required),
      'fileType': this.formBuilder.control('', Validators.required),

    });

  }
  async submitForm() {

  }


  uploadFiles( file: string | any[] ) {
    console.log( 'file', file )
    for ( let i = 0; i < file.length; i++ ) {
      this.formData.append( "file", file[i], file[i]['name'] );
    }
  }

  RequestUpload() {
    this.ReqJson["patientId"] = "12"
    this.ReqJson["requesterName"] = "test1"
    this.ReqJson["requestDate"] = "1/1/2019"
    this.ReqJson["location"] = "INDIA"
    this.formData.append( 'Info', JSON.stringify( this.ReqJson ) )

  }

}
