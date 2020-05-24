import { ProjectService } from './../../services/project.service';
import { GlobalService } from './../../services/global.service';
import { NotiService } from './../../services/noti.service';
import { Notification } from './../../model/response/Notification.model';
import { Component, OnInit, Input, OnChanges, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-notification-item',
  templateUrl: './notification-item.component.html',
  styleUrls: ['./notification-item.component.css']
})
export class NotificationItemComponent implements OnInit {
  @Input()
  item: Notification;
  constructor(private notify: NotiService, private globalService: GlobalService, private router: Router, private projectService: ProjectService) { }
  @Output()
  callBack = new EventEmitter();


  ngOnInit() {
  }
  goToDetail() {
    debugger;
    this.notify.updateNotification(this.item).subscribe((response: Notification) => {
      this.item = response;
      this.globalService.setCurrentprojectId(this.item.projectId);
      switch (this.item.type) {
        case "project":
          if (this.item.url == 'joinProject') {
            this.router.navigate(['/dashboard/project/joinProject']);
          } else {
            this.projectService.GetDetailProjectById(this.item.projectId).subscribe(response => {
              this.globalService.setCurrentProjectDetail(response);
              this.router.navigate(['/dashboard/task']);
            });
          }
          break;
        case "task":
        case "comment":
          this.projectService.GetDetailProjectById(this.item.projectId).subscribe(response => {
            this.globalService.setCurrentProjectDetail(response);
            this.router.navigate([`/dashboard/task/detail/${this.item.taskId}`]);
          });
          break;
        default:
          break;
      }
      this.callBack.emit('close');
    });
  }
}
