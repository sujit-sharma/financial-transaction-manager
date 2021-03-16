import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {FileHandleService} from "../services/file-handle.service";

@Component({
  selector: 'app-file-upload',
  templateUrl: './source.html',
  styleUrls: ['./source.css']
})
export class Source implements OnInit {
  // @ts-ignore
  form: FormGroup;
  fileTypesList : string[] = ['Select FileType', 'JSON' , 'CSV'];

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private fileHandleService: FileHandleService
              ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group( {
      'diaplayName': this.formBuilder.control('', Validators.required),
      'fileType': this.formBuilder.control('', Validators.required),

    });

  }
  async submitForm() {

  }

}
