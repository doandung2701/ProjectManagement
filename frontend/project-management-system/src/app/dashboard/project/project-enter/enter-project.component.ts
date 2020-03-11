import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import { MessageType } from 'src/app/model/typeMessage';


@Component({
  selector: 'app-enter-project',
  templateUrl: './enter-project.component.html',
  styleUrls: ['./enter-project.component.css'],
})
export class EnterProjectComponent implements OnInit {

  passcode: string='';
  constructor(private _notificaionService:NotificationService) { }

  ngOnInit() {
  }
  send() {
    if(this.passcode.trim()==''){
      this._notificaionService.showNotification(MessageType.ERROR,"You need enter passcode");
      return;
    }
  }
}
