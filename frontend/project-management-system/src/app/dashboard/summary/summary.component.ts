import { Component, OnInit } from '@angular/core';
import { TaskService } from 'src/app/services/task.service';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import { DashboardDto } from 'src/app/model/response/DashboardDto.model';
import { Status } from 'src/app/model/status.enum';
import { Category } from 'src/app/model/category.model';
import { MessageType } from 'src/app/model/typeMessage';
export interface Stat{
    property:string;
    value:number;
}
@Component({
    selector: 'dashboard-summary',
    templateUrl: './summary.component.html',
    styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {
    constructor(private taskService:TaskService,private notificationService:NotificationService) { }

    dashboardDto:DashboardDto;
    statusOptions=Object.keys(Status);
    categoriesOptions=Object.keys(Category);
    arrayStatus=new Array<Stat>();
    arrayCategory=new Array<Stat>();
    totalStatus:number=0;
    totalCategory:number=0;
    ngOnInit(): void { 
        //fix Id
        this.taskService.getCountTaskByUserid(JSON.parse(localStorage.getItem('currentUser'))["uid"]).subscribe((data:DashboardDto)=>{
           
           this.dashboardDto=data;
           //for status
            this.statusOptions.forEach(element=>{
            var a:boolean=false;  
            this.dashboardDto.taskByStatus.forEach(stat=>{
                if(stat.status.toString().localeCompare(element)==0){
                    a=true;
                    this.arrayStatus.push({
                        property:element,
                        value:stat.numOfTask
                    });
                    this.totalStatus+=stat.numOfTask;
                }
            });
            if(a==false){
                this.arrayStatus.push({
                    property:element,
                    value:0
                });
            }
            });
            

            this.categoriesOptions.forEach(element=>{
                var a:boolean=false;  
                this.dashboardDto.taskByCategory.forEach(stat=>{
                    if(stat.category.toString().localeCompare(element)==0){
                        a=true;
                        this.arrayCategory.push({
                            property:element,
                            value:stat.numOfTask
                        });
                        this.totalCategory+=stat.numOfTask;
                    }
                });
                if(a==false){
                    this.arrayCategory.push({
                        property:element,
                        value:0
                    });
                }
                });
        },err=>{
            this.notificationService.showNotification(MessageType.ERROR,"Error occur!!");
        })
    }
}
