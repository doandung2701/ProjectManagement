import { PageEvent } from '@angular/material';
import { NotiService } from './../../services/noti.service';
import { Component, OnInit, HostListener } from '@angular/core';

@Component({
  selector: 'app-notificaion-sticky',
  templateUrl: './notificaion-sticky.component.html',
  styleUrls: ['./notificaion-sticky.component.css']
})
export class NotificaionStickyComponent implements OnInit {
  isShow: boolean = false;
  numOfNotRead = 0;
  constructor(public notify: NotiService) { }
  @HostListener('window:click', ['$event'])
  onClick(targetElement: any) {
    if (targetElement.target.nodeName != 'MAT-ICON')
      this.isShow = false;
  }
  ngOnInit() {
    this.notify.dataSource.values.forEach(x => {
      if (x.read == false) {
        this.numOfNotRead++;
      }
    })
  }
  toggleNotificationList() {
    this.isShow = !this.isShow;
  }
  onFocusout() {
    this.isShow = false;
  }
  hideList() {
    this.isShow = false;
  }
}
