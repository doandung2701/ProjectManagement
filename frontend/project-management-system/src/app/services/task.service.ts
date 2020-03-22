import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { APIPaginationResponse } from '../model/APIPaginationResponse.model';
import { Task } from '../model/task.model';
import { HttpClient } from '@angular/common/http';
import { CreateTaskRequest } from '../model/request/createTaskRequest.model';
import { TaskResponse } from '../model/response/taskResponse.model';
import { GlobalService } from './global.service';
import { TaskCalendarList } from '../model/TaskCalendarList';
import { Comment } from '../model/comment.model';
import UpdateCommonTaskRequest from '../model/request/updateCommonTask.request';

@Injectable({
    providedIn:'root'
})
export class TaskService {
    updateTask(requestDto: UpdateCommonTaskRequest) {
        return this.http.put(this.url+`/task/updateTask`,requestDto);
    }
    createComment(taskId: number, comment: Comment) {
       return this.http.post<Comment>(this.url+`/task/addComment/${taskId}`,comment);
    }
    getCommentById(taskId:number) {
       return this.http.get<Comment[]>(this.url+`/task/getComment/${taskId}`);
    }
    getById(taskId: string) {
       return this.http.get<TaskResponse>(this.url+`/task/getDetail/${taskId}`);
    }
      // url=environment.apiUrl;
    url="http://localhost:8020";
    constructor(private http:HttpClient,private globalService:GlobalService){}
    getTaskOfUser(pageNumber:number,pageSize:number,filter:string):Observable<APIPaginationResponse<Task>>{
       return this.http.get<APIPaginationResponse<Task>>(this.url+'/task/getUserTasks/'+JSON.parse(localStorage.getItem('currentUser'))["uid"]+`?page=${pageNumber}&&size=${pageSize}&&filter=${filter}`);
    }
    createTask(creatTaskRequest:CreateTaskRequest){
        return this.http.post(this.url+'/task/createTask',creatTaskRequest);
    }
    getAllTaskCalendar(){
        return this.http.get<TaskCalendarList>(this.url+`/task/getTaskCalendar/${this.globalService.getCurrentprojectId()}/${JSON.parse(localStorage.getItem('currentUser'))["uid"]}`)
    }
}