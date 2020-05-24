import { map } from 'rxjs/operators';
import { WebsocketService } from './../websocket.service';
import { NotificationDataSource } from './NotificationDataSource';
import { APIPaginationResponse } from 'src/app/model/APIPaginationResponse.model';
import { Notification } from '../model/response/Notification.model';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotiService {
  private url=environment.apiUrl;
  _dataSource:NotificationDataSource;
  public notification: Subject<Notification>;
  constructor(private httpClient:HttpClient,wsService:WebsocketService) {
    this._dataSource=new NotificationDataSource(this);
    //  wsService.connect(this.notifySocketURL).subscribe((data)=>{
    //    console.log(data);
    //   this.notification.next(data.data);
    // })
   }
    getNotifyOfUser(pageIndex:number,pageSize:number,userId:number):Observable<APIPaginationResponse<Notification>>{
      return this.httpClient.get<APIPaginationResponse<Notification>>(this.url+`/notification?userId=${userId}&page=${pageIndex}&&size=${pageSize}`);

   }
   updateNotification(notify:Notification){
     return this.httpClient.get(this.url+`/notification/${notify.id}/read`);
   }
   
   public get dataSource()  {
    return this._dataSource
   }
   
}
