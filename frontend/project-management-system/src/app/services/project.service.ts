import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { CodeDTO } from '../model/codeDto.model';
import { HttpClient } from '@angular/common/http';
import { ProjectRoutingModule } from '../dashboard/project/project-routing.module';
import { InviteUserDTO } from '../model/InviteUserDto.model';
import { ProjectList } from '../model/projectList.model';
@Injectable({
    providedIn:'root'
})
export class ProjectService {
    url=environment.apiUrl;
    constructor(private http:HttpClient){}
    joinProjectByCode(code:string){
        const dto=new CodeDTO(code,JSON.parse(localStorage.getItem('currentUser'))["uid"]);
        this.http.post<any>(this.url+"/joinProjectByCode",dto);
    }
    inviteUserToProject(projectId:number,emails:string[]){
        const dto=new InviteUserDTO(emails);
        this.http.post<any>(this.url+`/inviteUserToProject/${projectId}`,dto);
    }
    getAllProjectUserJoin(){
        this.http.get<ProjectList>(this.url+'/getProjectUserJoined/'+JSON.parse(localStorage.getItem('currentUser'))["uid"]);
    }
    getProjectByAdmin(){
        this.http.get<ProjectList>(this.url+'/getProjectByAdmin/'+JSON.parse(localStorage.getItem('currentUser'))["uid"]);
    }
}