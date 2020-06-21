import { NotificationService } from './../common/service/notification.sevice';
import { NotificationDataSource } from './NotificationDataSource';
import { APIPaginationResponse } from 'src/app/model/APIPaginationResponse.model';
import { Notification } from '../model/response/Notification.model';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { MessageType } from '../model/typeMessage';
declare var SockJS;
declare var Stomp;
@Injectable({
  providedIn: 'root'
})
export class NotiService {
  private url=environment.apiUrl;
  _dataSource:NotificationDataSource;
  public notification: Subject<Notification>;
  public stompClient;
  public msg = [];
  public isHasNewMessage=new BehaviorSubject<boolean>(false);
  constructor(private httpClient:HttpClient,private notifiCation:NotificationService) {
    this._dataSource=new NotificationDataSource(this);
    console.log('constructors');
    this.initializeWebSocketConnection();
   }
   initializeWebSocketConnection() {
    const serverUrl = `http://34.68.195.249:9090/notification/ws`;
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    // tslint:disable-next-line:only-arrow-functions
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe('/topic/notification', (message) => {
        if (message.body) {
          that.updateDataSource(message.body);
        }
      });
    });
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
   updateDataSource(message){
     this._dataSource.addNewNotification(JSON.parse(message));
     this.isHasNewMessage.next(true);
     this.notifiCation.showNotification(MessageType.INFO,'You have new notification');

   }
}
