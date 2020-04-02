import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import { MessageType } from 'src/app/model/typeMessage';
import { ProjectService } from 'src/app/services/project.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-enter-project',
  templateUrl: './enter-project.component.html',
  styleUrls: ['./enter-project.component.css'],
})
export class EnterProjectComponent implements OnInit {

  passcode: string='';
  constructor(private _notificaionService:NotificationService,private router:Router,private projectService:ProjectService) { }

  ngOnInit() {
  }
  send() {
    if(this.passcode.trim()==''){
      this._notificaionService.showNotification(MessageType.ERROR,"You need enter passcode");
      return;
    }else{
      this.projectService.joinProjectByCode(this.passcode).subscribe(response=>{
        if(response){
        this._notificaionService.showNotification(MessageType.SUCCESS,"You have been joined into project");
        this.router.navigate(['/dashboard/project/joinedProject']);
        }else{
          this._notificaionService.showNotification(MessageType.ERROR,"Your passcode error,please enter again");

        }
      },err=>{
        this._notificaionService.showNotification(MessageType.ERROR,"Your passcode error,please enter again");

      })
    }
  }
}
