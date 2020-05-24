import { GlobalService } from './../../services/global.service';
import { NotificationService } from './../../common/service/notification.sevice';
import { UserService } from './../../services/user.service';
import { UserDto } from 'src/app/model/userDto.model';
import { Component, OnInit, ViewChildren, Inject, AfterViewInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { PageResponse } from 'src/app/model/pageResponse.model';
import { MatSelectionList, PageEvent, MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { MessageType } from 'src/app/model/typeMessage';
import { debounceTime } from 'rxjs/operators';
import { ConfirmationDialogComponent } from 'src/app/common/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-change-member-project-dialog',
  templateUrl: './change-member-project-dialog.component.html',
  styleUrls: ['./change-member-project-dialog.component.css']
})
export class ChangeMemberProjectDialogComponent implements OnInit,AfterViewInit {
  userEmail = new FormControl();

  seachUserForm: FormGroup = this.fb.group({
    userEmail: this.userEmail
  });
  sourceUser: UserDto[];
  pageSoureUser: PageResponse = new PageResponse(0, 0, 0, 0);
  selectedUser: UserDto[];
  @ViewChildren(MatSelectionList) userListSearched;

  constructor(private fb: FormBuilder, private userService: UserService, private notificationService: NotificationService,private globalService:GlobalService,
    public dialogRef: MatDialogRef<ChangeMemberProjectDialogComponent>,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public selectedUserBefore : object
    ) { 
      this.selectedUser = JSON.parse(JSON.stringify(selectedUserBefore['users']));
    }
    ngAfterViewInit(): void {
      this.userEmail.valueChanges.pipe(
          debounceTime(2000)
      ).subscribe(value=>{
           this.searchUser(value);
      });
  }
  ngOnInit() {
  }
  changPage(event: PageEvent) {
    this.userService.findEmail(this.userEmail.value, event.pageIndex, event.pageSize).subscribe(data => {
      this.sourceUser = data.content;
      this.pageSoureUser = data.page;
      if (data.content.length == 0) {
        this.notificationService.showNotification(MessageType.INFO, "No record found");
      }
    }, (error) => {
      if (error.ok == false) {
        this.notificationService.showNotification(MessageType.ERROR, "Unknow error");
      }
    });
  }
  searchUser(value) {
    // console.log(value);
    if (value.trim() == '') {
      this.sourceUser=[];
      return;
    }
    this.userService.findEmail(value, 0, 5).subscribe(data => {
      this.sourceUser = data.content;
      this.pageSoureUser = data.page;
    }, (error) => {
      if (error.ok == false) {
        this.notificationService.showNotification(MessageType.ERROR, "Unknow error");
      }
    });
  }
  addPeople() {
    this.userListSearched._results[0]._value.forEach((element:UserDto) => {
      var indexSelectedUser=this.selectedUser.find(u=>u.id==element.id);
      if(indexSelectedUser){

      }else{
        this.selectedUser.push(element);
      }
    });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
  onYesClick(): void {
    if(this.selectedUser.length==0){
      this.notificationService.showNotification(MessageType.INFO, "Member in project can not be empty");
      return ;
    }
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '350px',
      data: "If you remove people already in your project and and again in the future. He/she need to join project again with passcode And all task of this user will be remove.Are you sure?"
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.dialogRef.close(this.selectedUser);
      }else{
       
      }
    });
  }
  remove(user:UserDto){
    var indexSelectedUser=this.selectedUser.indexOf(user);
    if(indexSelectedUser!=-1){
      if(this.globalService.getCurrentProjectDetail().admin==user.id){
        this.notificationService.showNotification(MessageType.INFO, "You can not remove admin from project");

      }else{
        this.selectedUser.splice(indexSelectedUser,1);

      }
    }
}
}
