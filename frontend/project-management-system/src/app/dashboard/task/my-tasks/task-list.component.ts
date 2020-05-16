import { APIResponse, APIStatus } from './../../../model/APIResponse';
import { ConfirmationDialogComponent } from 'src/app/common/confirmation-dialog/confirmation-dialog.component';
import { Status } from './../../../model/status.enum';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import { MatPaginator, PageEvent, MatMenuTrigger, MatDialog } from '@angular/material';
import { Project } from 'src/app/model/project.model';
import { GlobalService } from 'src/app/services/global.service';
import { Router } from '@angular/router';
import { TaskDataSource } from './task.datasource';
import { TaskService } from 'src/app/services/task.service';
import { TaskResponse } from 'src/app/model/response/taskResponse.model';
import { ProjectDto } from 'src/app/model/response/ProjectDto';
import { User } from 'src/app/model/user';
import { SearchTaskListModel } from 'src/app/model/request/searchTaskListModel';
import { MessageType } from 'src/app/model/typeMessage';


@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild('input', { static: false }) input: ElementRef;
  @ViewChild('inputUser', { static: false }) inputUser: ElementRef;
  statusSelected: Status;
  @ViewChild(MatMenuTrigger, { static: false }) contextMenu: MatMenuTrigger;
  contextMenuPosition = { x: '0px', y: '0px' };
  displayedColumns: string[] = ['name', 'status', 'priority', 'startTime', 'deadline', 'detail', 'remove'];
  dataSource: TaskDataSource;
  userId: number;
  projectDetail: ProjectDto;
  statusesOptions = [];
  statuses = Status;
  constructor(private taskService: TaskService, private autService: AuthenticationService, private notificationService: NotificationService,
    private globalService: GlobalService, private router: Router,
    public dialog: MatDialog) {
    this.projectDetail = globalService.getCurrentProjectDetail();
  }
  formatDateFromString(dateString: string) {
    return new Date(dateString[0] + "-" + dateString[1] + "-" + dateString[2] + " " + dateString[3] + ":" + dateString[4]);

  }
  initLoadTask() {
    var searchElement = {
      name: '',
      user: '',
      status: null
    };
    this.dataSource.loadTasks(searchElement, 0, 5);
  }
  ngOnInit() {
    this.statusesOptions = Object.keys(this.statuses);
    this.userId = this.autService.currentUserValue.uid;
    this.dataSource = new TaskDataSource(this.taskService);
    this.initLoadTask();
  }
  isAdmin(member) {
    return member.id == this.projectDetail.admin;
  }
  search($event) {
    console.log(this.input.nativeElement);
    console.log(this.inputUser.nativeElement);
    console.log(this.statusSelected);
    var searchElement = {
      name: this.input.nativeElement.value,
      user: this.inputUser.nativeElement.value,
      status: this.statusSelected
    };

    this.paginator.pageIndex = 0;
    this.loadTasks(searchElement);
  }
  loadTasks(seachElement: SearchTaskListModel) {
    this.dataSource.loadTasks(
      seachElement, this.paginator.pageIndex, this.paginator.pageSize
    )
  }
  changPage(event: PageEvent) {
    var searchElement = {
      name: this.input.nativeElement.value,
      user: this.inputUser.nativeElement.value,
      status: this.statusSelected
    };
    this.loadTasks(searchElement);
  }
  onContextMenu(event: MouseEvent, item: Project) {
    event.preventDefault();
    this.contextMenuPosition.x = event.clientX + 'px';
    this.contextMenuPosition.y = event.clientY + 'px';
    this.contextMenu.menuData = { 'item': item };
    this.contextMenu.menu.focusFirstItem('mouse');
    this.contextMenu.openMenu();
  }
  goToDetail(item: TaskResponse) {
    this.router.navigate([`/dashboard/task/detail/${item.id}`]);
  }
  showDialogDeleteTask(item: TaskResponse) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '350px',
      data: "Do you want to delete this task?"
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.removeTask(item);
      }
    });
  }
  removeTask(item: TaskResponse) {
    this.taskService.removeTask(item.id).subscribe((response: APIResponse) => {
      if (response.status == APIStatus.OK) {
        this.notificationService.showNotification(MessageType.SUCCESS, response.data);
        this.initLoadTask();
      } else {
        this.notificationService.showNotification(MessageType.ERROR, response.data);
      }
    });
  }
}