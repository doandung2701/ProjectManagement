import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ProjectService } from 'src/app/services/project.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import {  MatPaginator, PageEvent, MatMenuTrigger } from '@angular/material';
import { ProjectDataSource } from './project.datasource';
import { fromEvent } from 'rxjs';
import {  distinctUntilChanged, debounceTime, tap } from 'rxjs/operators';
import { Project } from 'src/app/model/project.model';
import { GlobalService } from 'src/app/services/global.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit, AfterViewInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild('input', { static: false }) input: ElementRef;
  @ViewChild(MatMenuTrigger,{static:false}) contextMenu: MatMenuTrigger;
  contextMenuPosition = { x: '0px', y: '0px' };
  displayedColumns: string[] = ['name', 'description','admin','actions'];
  dataSource: ProjectDataSource;
  userId:number;
  constructor(private projectService: ProjectService, private autService: AuthenticationService, private notificationService: NotificationService,
    private globalService:GlobalService,private router:Router) {
  }
  ngOnInit() {
    this.userId=this.autService.currentUserValue.uid;
    this.dataSource = new ProjectDataSource(this.projectService);
    this.dataSource.loadProjects('', 0, 5);
  }
  isAdmin(admin){
    return admin==this.userId;
  }
  ngAfterViewInit() {
    fromEvent(this.input.nativeElement, 'keyup')
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.loadProjects();
        })
      ).subscribe();
  }
  loadProjects() {
    this.dataSource.loadProjects(
      this.input.nativeElement.value, this.paginator.pageIndex, this.paginator.pageSize
    )
  }
  changPage(event: PageEvent) {
    this.loadProjects();
  }
  goToProject(item:Project){
    this.globalService.setCurrentprojectId(item.id);
    this.projectService.GetDetailProjectById(item.id).subscribe(response=>{
      console.log(response);
       this.globalService.setCurrentProjectDetail(response );
      this.router.navigate(['/dashboard/task']);
    });
  }
}