import { Router } from '@angular/router';
import { NotiService } from './../../services/noti.service';
import { NotificationDataSource } from './../../services/NotificationDataSource';
import { Component, OnInit, Output, EventEmitter, HostListener } from '@angular/core';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.css']
})
export class NotificationListComponent implements OnInit {
  dataSource: NotificationDataSource;
  currentPageIndex = 0;
  currentPagesize = 5;
  @Output()
  itemClick = new EventEmitter();
  constructor(private notifyService: NotiService, private router: Router) { }
  ngOnInit() {
    this.dataSource = this.notifyService.dataSource;
    this.loadData();
  }
  loadData() {
    this.dataSource.loadNotifications(JSON.parse(localStorage.getItem('currentUser'))["uid"], 0, 5);
  }
  loadMore(event) {
    event.preventDefault();
    debugger;
    this.currentPageIndex++;
    this.dataSource.loadMoreNotifications(JSON.parse(localStorage.getItem('currentUser'))["uid"], this.currentPageIndex, 5);

  }
  hideList() {
    this.itemClick.emit('close');
  }
}
