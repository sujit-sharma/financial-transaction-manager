import { Component, OnInit } from '@angular/core';
import {MatchEntity} from "../services/entities";
import {FileHandleService} from "../services/file-handle.service";

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})

export class ResultComponent implements OnInit {
  matchDisplayColumns: string[] = ['transactionId', 'amount', 'currencyCode', 'date'];
  mismatchDisplayColumns: string[] = this.matchDisplayColumns.concat(['foundIn']);


  constructor(private fileHandleService: FileHandleService) { }

  ngOnInit(): void {
  }

  matchDataSource = this.fileHandleService.comparisonResult.MATCHING;
  mismatchingDataSource = this.fileHandleService.comparisonResult.MISMATCHING;
  missingDataSource = this.fileHandleService.comparisonResult.MISSING;
  fileTypesList: string[] = ['Downloads','JSON', 'CSV'];

  downloadFile(event: any) {
    console.log('Value get changed ...........' +event.target.value);
  }
}
