import { Component, OnInit } from '@angular/core';
import {MatchEntity} from "../services/entities";

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})

export class ResultComponent implements OnInit {
  MATCHING_DATA: MatchEntity[] = [
    { sn: 1, transactionId:'TR-47884222201',amount: 140.0,currency:'USD',valueDate:'2020-01-20' },
    { sn: 2, transactionId:'TR-47884222203',amount: 5000.0,currency:'JOD',valueDate: '2020-01-25' }
  ]
  displayColumns: string[] = ['#', 'Transaction ID', 'Amount', 'Value Date'];
  dataSource = this.MATCHING_DATA;
  constructor() { }

  ngOnInit(): void {
  }

}
