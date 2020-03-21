import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { APIPaginationResponse } from '../model/APIPaginationResponse.model';
import { Task } from '../model/task.model';
import { HttpClient } from '@angular/common/http';
import { CreateTaskRequest } from '../model/request/createTaskRequest.model';
import { TaskResponse } from '../model/response/taskResponse.model';
import { GlobalService } from './global.service';
import { TaskCalendarList } from '../model/TaskCalendarList';

@Injectable({
    providedIn:'root'
})
export class TaskService {
    getById(taskId: string) {
       return this.http.get<TaskResponse>(this.url+`/task/getDetail/${taskId}`);
    }
      // url=environment.apiUrl;
    url="http://localhost:8020";
    constructor(private http:HttpClient,private globalService:GlobalService){}
    getTaskOfUser(pageNumber:number,pageSize:number,filter:string):Observable<APIPaginationResponse<Task>>{
       return this.http.get<APIPaginationResponse<Task>>(this.url+'/task/getUserTasks/'+JSON.parse(localStorage.getItem('currentUser'))["uid"]+`?page=${pageNumber}&&size=${pageSize}&&filter=${filter}`);
    }
    createTask(creatTaskRequest:CreateTaskRequest):Observable<TaskResponse>{
        return this.http.post(this.url+'/task/createTask',creatTaskRequest);
    }
    getAllTaskCalendar(){
        return this.http.get<TaskCalendarList>(this.url+`/task/getTaskCalendar/${this.globalService.getCurrentprojectId}/${JSON.parse(localStorage.getItem('currentUser'))["uid"]}`)
    }
}