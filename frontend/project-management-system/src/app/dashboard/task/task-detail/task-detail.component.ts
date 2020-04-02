import { Component, OnInit, ViewChildren } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskResponse } from 'src/app/model/response/taskResponse.model';
import { TaskService } from 'src/app/services/task.service';
import { Category } from '../../../model/category.model';
import { Priority } from '../../../model/priority.enum';
import { Status } from '../../../model/status.enum';
import { MatDialog, MatSelectionList } from '@angular/material';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import { MessageType } from 'src/app/model/typeMessage';
import { ConfirmationDialogComponent } from 'src/app/common/confirmation-dialog/confirmation-dialog.component';
import { Comment } from 'src/app/model/comment.model';
import { ProjectService } from 'src/app/services/project.service';
import { UserResponse } from 'src/app/model/response/userResponse.model';
import UpdateCommonTaskRequest from 'src/app/model/request/updateCommonTask.request';
import { DatePipe } from '@angular/common';
import { CheckListFormComponent } from '../check-list-form/checklist-form.component';
import { CheckListUpdateComponent } from '../checklist-update/checklist-update.component';
import { CheckListDto } from 'src/app/model/checklistDto.model';
import { pipe } from 'rxjs';

@Component({
    selector: 'task-task-detail',
    templateUrl: './task-detail.component.html',
    styleUrls: ['./task-detail.component.css'],
    providers: [DatePipe]
})
export class TaskDetailComponent implements OnInit {
    taskDetail: TaskResponse = new TaskResponse();
    categories = Category;
    categoriesOptions = [];
    priorities = Priority;
    prioritesOptions = [];
    statuses = Status;
    statusesOptions = [];
    isUpdateCommon = false;
    listComment: Comment[];
    sourceUser: UserResponse[];
    isChanged = false;
    @ViewChildren(MatSelectionList) userListSearched;
    constructor(private route: ActivatedRoute, private taskService: TaskService,
        public dialog: MatDialog,
        private notificationService: NotificationService, private projectService: ProjectService,
        private datePipe: DatePipe) { }
    onSelection(e, v) {
        this.isChanged = true;
    }
    ngOnInit(): void {
        var that = this;
        this.categoriesOptions = Object.keys(this.categories);
        this.prioritesOptions = Object.keys(this.priorities);
        this.statusesOptions = Object.keys(this.statuses);
        this.taskService.getById(this.route.snapshot.paramMap.get("taskId")).subscribe(data => {
            this.taskDetail = data;
            let dateInstring=data.createdTime[0]+"-"+data.createdTime[1]+"-"+data.createdTime[2]+" "+data.createdTime[3]+":"+data.createdTime[4];
            this.taskDetail.createdTime=new Date(dateInstring);
             dateInstring=data.deadline[0]+"-"+data.deadline[1]+"-"+data.deadline[2]+" "+data.deadline[3]+":"+data.deadline[4];
            this.taskDetail.deadline=new Date(dateInstring);
             dateInstring=data.startTime[0]+"-"+data.startTime[1]+"-"+data.startTime[2]+" "+data.startTime[3]+":"+data.startTime[4];
            this.taskDetail.startTime=new Date(dateInstring);

            that.projectService.getUserJoinedProject().subscribe(response => {
                that.sourceUser = response;
            });
        });
        this.taskService.getCommentById(Number.parseInt(this.route.snapshot.paramMap.get("taskId"))).subscribe(data => {
            this.listComment = data;
        })
    }
    isUserIntask(user: UserResponse) {
        if (this.taskDetail && this.taskDetail.users) {
            var indexSelectedUser = this.taskDetail.users.findIndex(x => x.id == user.id);
            if (indexSelectedUser != -1) {
                return true;
            }
        }
        return false;
    }
    hanlderUserComment($event) {

        var comment = new Comment();
        comment.content = $event.content;
        comment.taskId = Number.parseInt(this.route.snapshot.paramMap.get("taskId"));
        comment.userId = Number.parseInt(JSON.parse(localStorage.getItem('currentUser'))["uid"]);
        comment.username = (JSON.parse(localStorage.getItem('currentUser'))["username"]);
        this.taskService.createComment(comment.taskId, comment).subscribe(data => {
            if (data) {
                console.debug(data);
                this.listComment.push(data);
                this.notificationService.showNotification(MessageType.SUCCESS, "Add comment success");
            }
        }, err => {
            this.notificationService.showNotification(MessageType.ERROR, "Add comment error");
        });
    }
    statusChange($event) {
        console.log($event);
    }
    updateCommon() {
        console.log(this.taskDetail);
        if (this.taskDetail.name && this.taskDetail.name.trim() == '') {
            this.notificationService.showNotification(MessageType.ERROR, "Task name can not be null");
            return;
        }
        if (this.taskDetail.description && this.taskDetail.description.trim() == '') {
            this.notificationService.showNotification(MessageType.ERROR, "Task description can not be null");
            return;
        }
        if (this.taskDetail.startTime && this.taskDetail.startTime == null) {
            this.notificationService.showNotification(MessageType.ERROR, "Task startTime can not be null");
            return;
        }
        if (this.taskDetail.deadline && this.taskDetail.deadline == null) {
            this.notificationService.showNotification(MessageType.ERROR, "Task name can not be null");
            return;
        }
        if (this.taskDetail.deadline < this.taskDetail.startTime) {
            this.notificationService.showNotification(MessageType.ERROR, "Task deadline can not less than start time");
            return;
        }
        if (this.isChanged)
            if (this.userListSearched._results[0]._value.length == 0) {
                this.notificationService.showNotification(MessageType.ERROR, "You need add people to task");
                return;
            }
        let requestDto = new UpdateCommonTaskRequest();
        requestDto.taskId = this.taskDetail.id;
        requestDto.taskName = this.taskDetail.name;
        requestDto.taskDescription = this.taskDetail.description;
        requestDto.deadline = this.datePipe.transform(this.taskDetail.deadline, 'yyyy-MM-dd HH:mm');
        requestDto.startTime = this.datePipe.transform(this.taskDetail.startTime, 'yyyy-MM-dd HH:mm');
        requestDto.status = this.taskDetail.status;
        requestDto.priority = this.taskDetail.priority;
        requestDto.category = this.taskDetail.category;
        if (this.isChanged) 
            requestDto.users = this.userListSearched._results[0]._value.map(x => x.id);
         else{
            requestDto.users=this.sourceUser.map(x=>x.id);
        }   
        this.taskService.updateTask(requestDto).subscribe(data => {
            if (data && data['status'] == 200)
                this.taskDetail = data['data'];
                var response= data['data'];
                let dateInstring=response.createdTime[0]+"-"+response.createdTime[1]+"-"+response.createdTime[2]+" "+response.createdTime[3]+":"+response.createdTime[4];
                this.taskDetail.createdTime=new Date(dateInstring);
                 dateInstring=response.deadline[0]+"-"+response.deadline[1]+"-"+response.deadline[2]+" "+response.deadline[3]+":"+response.deadline[4];
                this.taskDetail.deadline=new Date(dateInstring);
                 dateInstring=response.startTime[0]+"-"+response.startTime[1]+"-"+response.startTime[2]+" "+response.startTime[3]+":"+response.startTime[4];
                this.taskDetail.startTime=new Date(dateInstring);
            this.notificationService.showNotification(MessageType.SUCCESS, "Update task success");
        }, err => {
            this.notificationService.showNotification(MessageType.ERROR, "Update task error");
            return;
        })
    }
    remove(user) {
        var that = this;
        if (this.taskDetail.createdBy != JSON.parse(localStorage.getItem('currentUser'))["uid"]) {
            this.notificationService.showNotification(MessageType.ERROR, "You do no have permission to remove user because you not created this task");
            return;
        }
        const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
            width: '350px',
            data: "Do you confirm the remove of this data?"
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                var indexSelectedUser = that.taskDetail.users.indexOf(user);
                if (indexSelectedUser != -1) {
                    that.taskDetail.users.splice(indexSelectedUser, 1);
                }
                that.notificationService.showNotification(MessageType.SUCCESS, "Remove user successs");
            } else {

            }
        })

    }
    addCheckList(result: CheckListDto) {
        this.taskService.addCheckList(this.taskDetail.id, result).subscribe(response => {
            this.taskDetail = response;
            let data=response as TaskResponse;
            let dateInstring=data.createdTime[0]+"-"+data.createdTime[1]+"-"+data.createdTime[2]+" "+data.createdTime[3]+":"+data.createdTime[4];
            this.taskDetail.createdTime=new Date(dateInstring);
             dateInstring=data.deadline[0]+"-"+data.deadline[1]+"-"+data.deadline[2]+" "+data.deadline[3]+":"+data.deadline[4];
            this.taskDetail.deadline=new Date(dateInstring);
             dateInstring=data.startTime[0]+"-"+data.startTime[1]+"-"+data.startTime[2]+" "+data.startTime[3]+":"+data.startTime[4];
            this.taskDetail.startTime=new Date(dateInstring);
        })
    }
    saveCheckList(result: CheckListDto) {
        this.taskService.updateCheckList(this.taskDetail.id, result).subscribe(response => {
            this.taskDetail = response;
            let data=response as TaskResponse;
            let dateInstring=data.createdTime[0]+"-"+data.createdTime[1]+"-"+data.createdTime[2]+" "+data.createdTime[3]+":"+data.createdTime[4];
            this.taskDetail.createdTime=new Date(dateInstring);
             dateInstring=data.deadline[0]+"-"+data.deadline[1]+"-"+data.deadline[2]+" "+data.deadline[3]+":"+data.deadline[4];
            this.taskDetail.deadline=new Date(dateInstring);
             dateInstring=data.startTime[0]+"-"+data.startTime[1]+"-"+data.startTime[2]+" "+data.startTime[3]+":"+data.startTime[4];
            this.taskDetail.startTime=new Date(dateInstring);
        })
    }
    deleteCheckList(result: CheckListDto) {
        this.taskService.removeCheckList(this.taskDetail.id, result.id).subscribe(response => {
            this.taskDetail = response;
            let data=response as TaskResponse;
            let dateInstring=data.createdTime[0]+"-"+data.createdTime[1]+"-"+data.createdTime[2]+" "+data.createdTime[3]+":"+data.createdTime[4];
            this.taskDetail.createdTime=new Date(dateInstring);
             dateInstring=data.deadline[0]+"-"+data.deadline[1]+"-"+data.deadline[2]+" "+data.deadline[3]+":"+data.deadline[4];
            this.taskDetail.deadline=new Date(dateInstring);
             dateInstring=data.startTime[0]+"-"+data.startTime[1]+"-"+data.startTime[2]+" "+data.startTime[3]+":"+data.startTime[4];
            this.taskDetail.startTime=new Date(dateInstring);
        })
    }
    createNewCheckList() {
        const dialogRef = this.dialog.open(CheckListFormComponent, {
            width: '400px',
            data: {
                taskId: this.taskDetail.id,
                checkListId: null
            },
            disableClose: true
        }
        );
        dialogRef.afterClosed().subscribe(result => {;
            this.addCheckList(result);
        });
    }
    editCheckList(checkList: CheckListDto) {
        const dialogRef = this.dialog.open(CheckListUpdateComponent, {
            width: '400px',
            data: checkList,
            disableClose: true
        }
        );
        dialogRef.afterClosed().subscribe(result => {
            this.saveCheckList(result);
        });
    }

}
