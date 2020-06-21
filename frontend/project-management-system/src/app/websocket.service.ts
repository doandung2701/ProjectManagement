import { Injectable } from '@angular/core';
import { NotiService } from './services/noti.service';
declare var SockJS;
declare var Stomp;
// @Injectable({
//   providedIn: 'root'
// })
// export class WebsocketService {

//   constructor(private notiService:NotiService) {
//     this.initializeWebSocketConnection();
//   }
//   public stompClient;
//   public msg = [];
//   initializeWebSocketConnection() {
//     const serverUrl = 'http://notification-service:9090/ws';
//     const ws = new SockJS(serverUrl);
//     this.stompClient = Stomp.over(ws);
//     const that = this;
//     // tslint:disable-next-line:only-arrow-functions
//     this.stompClient.connect({}, function(frame) {
//       that.stompClient.subscribe('/topic/notification', (message) => {
//         if (message.body) {
//           this.notiService.updateDataSource(message.body);
//         }
//       });
//     });
//   }
// }
