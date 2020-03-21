import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { UserDto } from 'src/app/model/userDto.model';
import { Task } from 'src/app/model/task.model';
import { NewTask } from 'src/app/model/newTask.model';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import { TaskService } from 'src/app/services/task.service';
import { UserService } from 'src/app/services/user.service';
import { GlobalService } from 'src/app/services/global.service';
import { CreateTaskRequest } from 'src/app/model/request/createTaskRequest.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Priority } from 'src/app/model/priority.enum';
import { UserResponse } from 'src/app/model/response/userResponse.model';
import { ProjectService } from 'src/app/services/project.service';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.css'],
  providers:[DatePipe]
})
export class TaskCreatorComponent implements OnInit {

  showReview = false;
   sourceUser: UserResponse[];
   assignedUser: UserResponse[];
  // taskModel = new Task();
  // newTaskModel = new NewTask();
  lastStepIndex: number;
  // selectedIndex: number;
  stepSize: number;
  // reviewIndex = 3;
  // assignIndex = 2;
  isLinear = true;
  createTaskRequest:CreateTaskRequest;
  createForm:FormGroup;
  constructor(private msg: NotificationService, private service: TaskService, private userService: UserService,
              private datePipe: DatePipe, private global: GlobalService,private fb:FormBuilder,
              private projectService:ProjectService) {
  }
  dateTimeValid(){
    if(this.createTaskRequest.startTime&&this.createTaskRequest.deadline){
      if(this.createTaskRequest.deadline>=this.createTaskRequest.startTime){
        return true;
      }
    }
    return false;
  }
  public set startTime(v: string) {
    let actualParsedDate = v ? new Date(v) : new Date();
    let normalizedParsedDate = new Date(actualParsedDate.getTime() + (actualParsedDate.getTimezoneOffset() * 60000));
    this.createTaskRequest.startTime = normalizedParsedDate;
  }


  public get startTime(): string {
    return this.parseDateToStringWithFormat(this.createTaskRequest.startTime);
  }
  public set deadLine(v: string) {
    let actualParsedDate = v ? new Date(v) : new Date();
    let normalizedParsedDate = new Date(actualParsedDate.getTime() + (actualParsedDate.getTimezoneOffset() * 60000));
    this.createTaskRequest.deadline = normalizedParsedDate;
  }


  public get deadLine(): string {
    return this.parseDateToStringWithFormat(this.createTaskRequest.deadline);
  }
  private parseDateToStringWithFormat(date: Date): string {
    let result: string;
    let dd = date.getDate().toString();
    let mm = (date.getMonth() + 1).toString();
    let hh = date.getHours().toString();
    let min = date.getMinutes().toString();
    dd = dd.length === 2 ? dd : "0" + dd;
    mm = mm.length === 2 ? mm : "0" + mm;
    hh = hh.length === 2 ? hh : "0" + hh;
    min = min.length === 2 ? min : "0" + min;
    result = [date.getFullYear(), '-', mm, '-', dd, 'T', hh, ':', min].join('');

    return result;
  }
  ngOnInit() {
    this.createTaskRequest=new CreateTaskRequest();
    this.createTaskRequest.startTime=new Date();
    this.createTaskRequest.deadline=new Date();
    this.createTaskRequest.priority=Priority.NONE;
   this.projectService.getUserJoinedProject().subscribe(response=>{
    this.sourceUser=response;
   });
    // this.assignedUser = [];
    this.lastStepIndex = 4;
    this.stepSize = 5;
    this.createForm=this.fb.group({
      name:['',[Validators.compose([Validators.required,Validators.minLength(3),Validators.maxLength(20)])]],
      description:['',Validators.compose([Validators.required,Validators.minLength(3),Validators.maxLength(300)])],
      startTime:[Date.now(),Validators.required],
      deadline:[Date.now(),Validators.compose([Validators.required])]      
    })
  }
  get createTaskFormControl() {
    return this.createForm.controls;
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
    
  }

  onPublish() {
    
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
