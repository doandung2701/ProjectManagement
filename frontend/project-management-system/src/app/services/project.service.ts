import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { CodeDTO } from '../model/codeDto.model';
import { HttpClient } from '@angular/common/http';
import { InviteUserDTO } from '../model/InviteUserDto.model';
import { ProjectList } from '../model/projectList.model';
import { NewProjectDto } from '../model/newProjectDTO.model';
import { Observable } from 'rxjs';
import { APIPaginationResponse } from '../model/APIPaginationResponse.model';
import { Project } from '../model/project.model';
import { UserDto } from '../model/userDto.model';
import { GlobalService } from './global.service';
import { UserResponse } from '../model/response/userResponse.model';
import { ProjectDto } from '../model/response/ProjectDto';
@Injectable({
    providedIn:'root'
})
export class ProjectService {
    // url=environment.apiUrl;
    private url=environment.apiUrl;
    // private url="http://localhost:8090";
    constructor(private http:HttpClient,private globalServcie:GlobalService){}
    joinProjectByCode(code:string){
        const dto=new CodeDTO(code,JSON.parse(localStorage.getItem('currentUser'))["uid"]);
        return this.http.post<any>(this.url+"/project/joinProjectByCode",dto);
    }
    inviteUserToProject(projectId:number,emails:string[]){
        const dto=new InviteUserDTO(emails);
        return this.http.post<any>(this.url+`/project/inviteUserToProject/${projectId}`,dto);
    }
    getAllProjectUserJoin(pageNumber:number,pageSize:number,filterText:string):Observable<APIPaginationResponse<Project>>{
        return this.http.get<APIPaginationResponse<Project>>(this.url+'/project/getProjectUserJoined/'+JSON.parse(localStorage.getItem('currentUser'))["uid"]+`?page=${pageNumber}&&size=${pageSize}&&filter=${filterText}`);
    }
  
    getProjectByAdmin(){
        return this.http.get<ProjectList>(this.url+'/project/getProjectByAdmin/'+JSON.parse(localStorage.getItem('currentUser'))["uid"]);
    }
    createProject(project:NewProjectDto){
        return this.http.post(this.url+'/project/createProject',project);

    }
    getUserJoinedProject(){
            return this.http.get<UserResponse[]>(this.url+`/project/${this.globalServcie.getCurrentprojectId()}/getUser`);   
    }
    GetDetailProjectById(id: number) {
        return this.http.get<ProjectDto>(this.url+`/project/detail/${id}`);
      }
}