import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ProjectService } from 'src/app/services/project.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import {  MatPaginator, PageEvent, MatMenuTrigger } from '@angular/material';
import { fromEvent } from 'rxjs';
import {  distinctUntilChanged, debounceTime, tap } from 'rxjs/operators';
import { Project } from 'src/app/model/project.model';
import { GlobalService } from 'src/app/services/global.service';
import { Router } from '@angular/router';
import { TaskDataSource } from './task.datasource';
import { TaskService } from 'src/app/services/task.service';
import { TaskResponse } from 'src/app/model/response/taskResponse.model';


@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit, AfterViewInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild('input', { static: false }) input: ElementRef;
  @ViewChild(MatMenuTrigger,{static:false}) contextMenu: MatMenuTrigger;
  contextMenuPosition = { x: '0px', y: '0px' };
  displayedColumns: string[] = ['name', 'status','priority'];
  dataSource: TaskDataSource;
  userId:number;
  constructor(private taskService: TaskService, private autService: AuthenticationService, private notificationService: NotificationService,
    private globalService:GlobalService,private router:Router) {
  }
  ngOnInit() {
    this.userId=this.autService.currentUserValue.uid;
    this.dataSource = new TaskDataSource(this.taskService);
    this.dataSource.loadTasks('', 0, 5);
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
          this.loadTasks();
        })
      ).subscribe();
  }
  loadTasks() {
    this.dataSource.loadTasks(
      this.input.nativeElement.value, this.paginator.pageIndex, this.paginator.pageSize
    )
  }
  changPage(event: PageEvent) {
    this.loadTasks();
  }
  onContextMenu(event: MouseEvent, item: Project) {
    event.preventDefault();
    this.contextMenuPosition.x = event.clientX + 'px';
    this.contextMenuPosition.y = event.clientY + 'px';
    this.contextMenu.menuData = { 'item': item };
    this.contextMenu.menu.focusFirstItem('mouse');
    this.contextMenu.openMenu();
  }
  goToDetail(item:TaskResponse){
     this.router.navigate([`/dashboard/task/detail/${item.id}`]);
    //open modal
  }
}