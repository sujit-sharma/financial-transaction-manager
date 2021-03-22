import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource} from "@angular/material/table";
import { MatSort} from "@angular/material/sort";
import {MatchEntity} from "../services/entities";

@Component({
  selector: 'app-test1',
  templateUrl: './test1.component.html',
  styleUrls: ['./test1.component.css']
})
export class Test1Component implements OnInit {

  dataSource: any;
  displayedColumns = [];
  @ViewChild(MatSort) sort?: MatSort;

  columnNames = [{
    id: 'sn',
    value: 'No.',

    },
    {
    id: 'transactionId',
    value: 'Transaction ID',
    },
    {
      id: 'amount',
      value: 'Amount',
    },
    {
      id: 'currency',
      value: 'Currency',
    },
    {
      id: 'valueDate',
      value: 'Value Date'
    }
    ];

  ngOnInit() {
    // @ts-ignore
    this.displayedColumns = this.columnNames.map(x => x.id);
    this.createTable();
  }

  createTable() {
    let tableArr: MatchEntity[] = [
      { sn: 1, transactionId:'TR-47884222201',amount: 140.0,currency:'USD',valueDate:'2020-01-20' },
      { sn: 2, transactionId:'TR-47884222203',amount: 5000.0,currency:'JOD',valueDate: '2020-01-25' }
    ];
    this.dataSource = new MatTableDataSource(tableArr);
    this.dataSource.sort = this.sort;
  }
}


