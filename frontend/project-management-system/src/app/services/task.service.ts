import { SearchTaskListModel } from './../model/request/searchTaskListModel';
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
import { environment } from 'src/environments/environment';
import { DashboardDto } from '../model/response/DashboardDto.model';
import { CountTaskByProjectViewModel } from '../model/response/CountTaskByProjectViewModel';
import { APIResponse } from '../model/APIResponse';

@Injectable({
    providedIn:'root'
})
export class TaskService {
    removeTask(id: number) {
        return this.http.delete<APIResponse>(this.url+`/task/deleteTask/${id}/${JSON.parse(localStorage.getItem('currentUser'))["uid"]}`);
    }
    getTop5TaskOrderByDeadline() {
        return this.http.get<TaskResponse[]>(this.url+`/task/getTop5TaskOrderByDeadline/${JSON.parse(localStorage.getItem('currentUser'))["uid"]}`);

    }
    countTaskByProjectIdOfUser() {
        return this.http.get<CountTaskByProjectViewModel[]>(this.url+`/task/countTaskByProject/${JSON.parse(localStorage.getItem('currentUser'))["uid"]}`);

    }
    updateCheckList(id: number, result: import("../model/checklistDto.model").CheckListDto) {
        return this.http.post(this.url+`/task/${id}/updateCheckList`,result);   
    }
    addCheckList(id: number, result: import("../model/checklistDto.model").CheckListDto) {
        return this.http.post(this.url+`/task/${id}/addCheckList`,result);   
    }
    removeCheckList(id: number, checkListId:number) {
        return this.http.get(this.url+`/task/${id}/removeCheckList/${checkListId}`);   
    }
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
    private url=environment.apiUrl;
    // private url="http://localhost:8020";
    constructor(private http:HttpClient,private globalService:GlobalService){}
    getTaskOfUser(pageNumber:number,pageSize:number,filter:SearchTaskListModel):Observable<APIPaginationResponse<Task>>{
       return this.http.post<APIPaginationResponse<Task>>(this.url+'/task/getProjectTasks/'+this.globalService.getCurrentprojectId()+`?page=${pageNumber}&&size=${pageSize}`,filter);
    }
    createTask(creatTaskRequest:CreateTaskRequest){
        return this.http.post(this.url+'/task/createTask',creatTaskRequest);
    }
    getAllTaskCalendar(){
        return this.http.get<TaskCalendarList>(this.url+`/task/getTaskCalendar/${this.globalService.getCurrentprojectId()}/${JSON.parse(localStorage.getItem('currentUser'))["uid"]}`)
    }
    getCountTaskByUserid(userId:number){
        return this.http.get<DashboardDto>(this.url+`/task/countTaskByUser/${userId}`);
    }
    getAllTaskOfUser(){
        return this.http.get<TaskCalendarList>(this.url+`/task/getAllTaskByUserId/${JSON.parse(localStorage.getItem('currentUser'))["uid"]}`);
    }
}