import { Injectable } from '@angular/core';
@Injectable({
  providedIn: 'root'
})
export class GlobalService {
  constructor() { }
  private currentProjectId: number;

  getCurrentprojectId(): number {
    return this.currentProjectId;
  }
  setCurrentprojectId(projectId: number) {
    this.currentProjectId = projectId;
  }
}
