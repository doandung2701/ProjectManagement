import { MessageType } from './model/typeMessage';
import { NotificationService } from './common/service/notification.sevice';
import { GlobalService } from './services/global.service';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskListActiveGuard implements CanActivate {
  constructor(private globalService:GlobalService,private notificationService:NotificationService,private router: Router){}
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.globalService.getCurrentprojectId()){
      return true;
    }else{
      this.notificationService.showNotification(MessageType.ERROR,"You need select project before go to task dashboard");
      this.router.navigate(['/dashboard/project/joinedProject']);
      return false;
    }
  }
  
}
