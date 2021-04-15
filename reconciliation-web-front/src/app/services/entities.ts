export interface FileUploadEntity {
  fileType: string;
  isSource: boolean;
  displayName: string;
  file: any;
}

export interface MatchEntity {
  sn: number;
  transactionId: string;
  amount : number;
  currency: string;
  valueDate: string;
}

export interface UserActivity {
  username: string;
  hitInformation: HitInformation[];
}

export interface HitInformation {
  id: number;
  activity: string;
  requestIpAddr: string;
  date: string;
}
