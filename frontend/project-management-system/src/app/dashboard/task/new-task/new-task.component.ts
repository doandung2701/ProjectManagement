import { Component, OnInit, Input } from '@angular/core';
import { DatePipe } from '@angular/common';
import { UserDto } from 'src/app/model/userDto.model';
import { Task } from 'src/app/model/task.model';
import { NewTask } from 'src/app/model/newTask.model';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import { TaskService } from 'src/app/services/task.service';
import { UserService } from 'src/app/services/user.service';
import { GlobalService } from 'src/app/services/global.service';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.css'],
})
export class TaskCreatorComponent implements OnInit {

  showReview = false;
  sourceUser: UserDto[];
  assignedUser: UserDto[];
  taskModel = new Task();
  newTaskModel = new NewTask();
  lastStepIndex: number;
  selectedIndex: number;
  stepSize: number;
  reviewIndex = 3;
  assignIndex = 2;
  isLinear = true;

  constructor(private msg: NotificationService, private service: TaskService, private userService: UserService,
              private datePipe: DatePipe, private global: GlobalService) {
  }

  ngOnInit() {
   // this.userService.getAllUserByProgram().subscribe( res => this.sourceUser = res.userList);
    this.assignedUser = [];
    this.lastStepIndex = 4;
    this.stepSize = 5;
  }

  prepareForReview() {
    // this.newTaskModel.user = [];
    // this.assignedUser.forEach(t => { this.newTaskModel.user.push(t.id); });
    // this.taskModel.createdBy = this.global.getUid();  // The same user
    // this.taskModel.program = this.global.getCurrentProgramId();  // The program of this dashboard
    // this.taskModel.modifiedBy = this.global.getUid(); // The same user who created
    // this.taskModel.status = 'created';

    // this.taskModel.modifiedTime = this.datePipe.transform(Date.now(), 'yyyy-MM-dd HH:mm');
    // this.taskModel.createdTime = this.datePipe.transform(Date.now(), 'yyyy-MM-dd HH:mm');
    // try {
    //   this.taskModel.startTime = this.taskModel.startTime.replace('T', ' ');
    //   this.taskModel.deadline = this.taskModel.deadline.replace('T', ' ') ;
    //   this.taskModel.createdTime = this.taskModel.createdTime.replace('T', ' ') ;
    //   this.taskModel.modifiedTime = this.taskModel.modifiedTime.replace('T', ' ');
    // } catch (error) {
    //   console.log('Wrong format datetime-local');
    // }
    // this.newTaskModel.task = this.taskModel;
  }

  initialize() {
    this.prepareForReview();
  }

  onPublish() {
    // this.prepareForReview();
    // let valid = false;
    // if (this.newTaskModel.task.name.length > 0 && this.newTaskModel.task.description.length > 0
    //     && this.newTaskModel.task.startTime.length > 0 && this.newTaskModel.task.deadline.length > 0) {
    //       valid = true;
    // }

    // if (valid) {
    //   this.service.create(this.newTaskModel).subscribe( data => {
    //       this.msg.add({severity: 'success', summary: 'Task Created',
    //       detail: 'New task has been created successfully'});
    //     },
    //     error => {
    //       this.msg.add({severity: 'error', summary: 'Incomplete Form',
    //       detail: 'New Task will be created after all neccessary fields are provided.'});
    //     });
    // } else {
    //   this.msg.add({severity: 'error', summary: 'Incomplete Form',
    //   detail: 'New Task will be created after all neccessary fields are provided.'});
    // }
  }

  stepChange(ev) {
    // if ( ev.selectedIndex === this.reviewIndex) {
    //   this.showReview = true;
    //   const child = document.createElement('div');
    //   child.innerHTML = this.taskModel.description;
    //   document.getElementById('decriptionBox').innerHTML = '';  // empty earlier values
    //   document.getElementById('decriptionBox').appendChild(child);
    // } else {
    //   this.showReview = false;
    // }
    // if ( ev.selectedIndex === this.assignIndex) {
    //   this.assignedUser = [];
    //   this.newTaskModel.user = [];
    //   this.userService.getAllUserByProgram().subscribe( res => this.sourceUser = res.userList);
    // }
  }
}
