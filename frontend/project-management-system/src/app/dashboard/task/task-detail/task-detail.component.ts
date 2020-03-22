import { Component, OnInit, ViewChildren } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskResponse } from 'src/app/model/response/taskResponse.model';
import { TaskService } from 'src/app/services/task.service';
import {Category} from '../../../model/category.model';
import {Priority} from '../../../model/priority.enum';
import {Status} from '../../../model/status.enum';
import { MatDialog, MatSelectionList } from '@angular/material';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import { MessageType } from 'src/app/model/typeMessage';
import { ConfirmationDialogComponent } from 'src/app/common/confirmation-dialog/confirmation-dialog.component';
import { Comment } from 'src/app/model/comment.model';
import { ProjectService } from 'src/app/services/project.service';
import { UserResponse } from 'src/app/model/response/userResponse.model';
import UpdateCommonTaskRequest from 'src/app/model/request/updateCommonTask.request';
import { DatePipe } from '@angular/common';

@Component({
    selector: 'task-task-detail',
    templateUrl: './task-detail.component.html',
    styleUrls: ['./task-detail.component.css'],
    providers:[DatePipe]
})
export class TaskDetailComponent implements OnInit {
    taskDetail:TaskResponse=new TaskResponse();
    categories=Category;
    categoriesOptions=[];
    priorities=Priority;
    prioritesOptions=[];
    statuses=Status;
    statusesOptions=[];
    isUpdateCommon=false;
    listComment:Comment[];
    sourceUser:UserResponse[];
    @ViewChildren(MatSelectionList) userListSearched;
    constructor(private route:ActivatedRoute,private taskService:TaskService,
        public dialog: MatDialog,
        private notificationService:NotificationService,private projectService:ProjectService,
        private datePipe: DatePipe) { }
    
    ngOnInit(): void { 
        var that=this;
        this.categoriesOptions=Object.keys(this.categories);
        this.prioritesOptions=Object.keys(this.priorities);
        this.statusesOptions=Object.keys(this.statuses);
        this.taskService.getById(this.route.snapshot.paramMap.get("taskId")).subscribe(data=>{
            this.taskDetail=data;
            that.projectService.getUserJoinedProject().subscribe(response=>{
                that.sourceUser=response;
               });
        });
        this.taskService.getCommentById(Number.parseInt(this.route.snapshot.paramMap.get("taskId"))).subscribe(data=>{
            this.listComment=data;
        })
    }
    isUserIntask(user:UserResponse){
        if(this.taskDetail&&this.taskDetail.users){
            var indexSelectedUser=this.taskDetail.users.findIndex(x=>x.id==user.id);
            if(indexSelectedUser!=-1){
               return true;
            }
        }
        return false;
    }
    hanlderUserComment($event){
       
        var comment=new Comment();
        comment.content=$event.content;
        comment.taskId=Number.parseInt(this.route.snapshot.paramMap.get("taskId"));
        comment.userId=Number.parseInt(JSON.parse(localStorage.getItem('currentUser'))["uid"]);
        comment.username=(JSON.parse(localStorage.getItem('currentUser'))["username"]);
        console.log(comment);
        this.taskService.createComment(comment.taskId,comment).subscribe(data=>{
            if(data){
                console.debug(data);
                this.listComment.push(data);
                this.notificationService.showNotification(MessageType.SUCCESS,"Add comment success");
            }
        },err=>{
            this.notificationService.showNotification(MessageType.ERROR,"Add comment error");
        });
    }
    statusChange($event){
        console.log($event);
    }
    updateCommon(){
        console.log(this.taskDetail);
        if(this.taskDetail.name&&this.taskDetail.name.trim()==''){
            this.notificationService.showNotification(MessageType.ERROR,"Task name can not be null");
            return ;
        }
        if(this.taskDetail.description&&this.taskDetail.description.trim()==''){
            this.notificationService.showNotification(MessageType.ERROR,"Task description can not be null");
            return ;
        }
        if(this.taskDetail.startTime&&this.taskDetail.startTime==null){
            this.notificationService.showNotification(MessageType.ERROR,"Task startTime can not be null");
            return ;
        }
        if(this.taskDetail.deadline&&this.taskDetail.deadline==null){
            this.notificationService.showNotification(MessageType.ERROR,"Task name can not be null");
            return ;
        }
        if(this.taskDetail.deadline<this.taskDetail.startTime){
            this.notificationService.showNotification(MessageType.ERROR,"Task deadline can not less than start time");
            return ;
        }
        if(this.userListSearched._results[0]._value.length==0){
            this.notificationService.showNotification(MessageType.ERROR,"You need add people to task");
            return ;
        }
        let requestDto=new UpdateCommonTaskRequest();
        requestDto.taskId=this.taskDetail.id;
        requestDto.taskName=this.taskDetail.name;
        requestDto.taskDescription=this.taskDetail.description;
        requestDto.deadline=this.datePipe.transform(this.taskDetail.deadline,'yyyy-MM-dd HH:mm');
        requestDto.startTime=this.datePipe.transform(this.taskDetail.startTime,'yyyy-MM-dd HH:mm');
        requestDto.status=this.taskDetail.status;
        requestDto.priority=this.taskDetail.priority;
        requestDto.category=this.taskDetail.category;
        requestDto.users=this.userListSearched._results[0]._value.map(x=>x.id);
        this.taskService.updateTask(requestDto).subscribe(data=>{
            if(data&&data['status']==200)
                this.taskDetail=data['data'];
                this.notificationService.showNotification(MessageType.SUCCESS,"Update task success");
        },err=>{
            this.notificationService.showNotification(MessageType.ERROR,"Update task error");
            return ;
        })
    }
    remove(user){
        var that=this;
        if(this.taskDetail.createdBy!=JSON.parse(localStorage.getItem('currentUser'))["uid"]){
            this.notificationService.showNotification(MessageType.ERROR,"You do no have permission to remove user because you not created this task");
            return ;
        }
        const dialogRef=this.dialog.open(ConfirmationDialogComponent,{
            width:'350px',
            data:"Do you confirm the remove of this data?"
        });
        dialogRef.afterClosed().subscribe(result=>{
            if (result) {
                var indexSelectedUser=that.taskDetail.users.indexOf(user);
                if(indexSelectedUser!=-1){
                    that.taskDetail.users.splice(indexSelectedUser,1);
                }
             that.notificationService.showNotification(MessageType.SUCCESS,"Remove user successs");   
            }else{

            }
        })
      
    }
}
