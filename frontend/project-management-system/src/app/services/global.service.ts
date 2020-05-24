import { Injectable } from '@angular/core';
import { ProjectDto } from '../model/response/ProjectDto';
@Injectable({
  providedIn: 'root'
})
export class GlobalService {
  empty() {
      this.currentProjectId=null;
      this.projectDetail=null;
  }
  constructor() { }
  private currentProjectId: number;
  private projectDetail:ProjectDto;
  getCurrentprojectId(): number {
    return this.currentProjectId;
  }
  setCurrentprojectId(projectId: number) {
    this.currentProjectId = projectId;
  }
  getCurrentProjectDetail():ProjectDto{
    return this.projectDetail;
  }
  setCurrentProjectDetail(projectDetail:ProjectDto){
    this.projectDetail=projectDetail;
  }
}
