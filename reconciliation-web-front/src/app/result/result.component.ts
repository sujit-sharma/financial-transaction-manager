import { Component, OnInit } from '@angular/core';
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

  activeTab:any;

  downloadFile(event: any, transactionType: string) {
    const fileType = event.target.value.toString();
    console.log('Download request accepted for ' +fileType + ' file');
    const  dataForDownload = transactionType === 'match'? this.matchDataSource: transactionType === 'mismatch'? this.mismatchingDataSource: this.missingDataSource;
    const fileName = transactionType;
    console.log('Data downloading are...........  ' +JSON.stringify(dataForDownload));
    if(fileType == 'JSON') {
      this.downloadJsonFile(dataForDownload, fileName);
    }
    else {
      this.downloadCsvFile(dataForDownload, fileName);
    }
  }
  downloadJsonFile(data: any, fileName: string) {
    console.log('Downloading JSON file');
    fileName += '.json';
    const blob = new Blob([JSON.stringify(data)], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    // Create a new anchor element
    const a = document.createElement('a');
    a.href = url;
    a.download = fileName || 'comparison-result.json';
    a.click();
    a.remove();
  }

  private downloadCsvFile(data: any, fileName: string) {
    fileName += '.csv'
    data = this.convertToCSV(data);
    const blob = new Blob([data], { type: 'text/csv' });
    const url= window.URL.createObjectURL(blob);
    // window.open(url);
    const a = document.createElement('a');
    a.href = url;
    a.download = fileName || 'comparison-result.csv';
    a.click();
    a.remove();
    console.log('Download CSV file');
  }

  convertToCSV(objArray:any) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    var str = '';

    for (var i = 0; i < array.length; i++) {
      var line = '';
      for (var index in array[i]) {
        if (line != '') line += ','

        line += array[i][index];
      }

      str += line + '\r\n';
    }

    return str;
  }
}
