import { Component, OnInit } from '@angular/core';
import { GlobalService } from 'src/app/services/global.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import { MessageType } from 'src/app/model/typeMessage';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent {

  constructor(private globalService:GlobalService,private router:Router,private notificationService:NotificationService) { 
    // if(!this.globalService.getCurrentprojectId()){
    //   this.notificationService.showNotification(MessageType.ERROR,"You need select project before go to task dashboard");
    //   this.router.navigate(['/dashboard/project/joinedProject']);
    // }
  }

  // ngOnInit() {
  //   if(!this.globalService.getCurrentprojectId()){
  //     this.notificationService.showNotification(MessageType.ERROR,"You need select project before go to task dashboard");
  //     this.router.navigate(['/dashboard/project/joinedProject']);
  //   }
  // }

}
