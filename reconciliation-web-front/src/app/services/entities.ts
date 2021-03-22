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
