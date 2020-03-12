import { Component, OnInit, ViewChild, ViewChildren, AfterViewInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Project } from 'src/app/model/project.model';
import { MatSelectionList } from '@angular/material';
import { UserService } from 'src/app/services/user.service';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import { UserDto } from 'src/app/model/userDto.model';
import { debounceTime, tap } from 'rxjs/operators'; 
import { MessageType } from 'src/app/model/typeMessage';
@Component({
    selector: 'dashboard-project-create',
    templateUrl: './project-create.component.html',
    styleUrls: ['./project-create.component.css']
})
export class ProjectCreateComponent implements OnInit ,AfterViewInit{
    ngAfterViewInit(): void {
        this.userEmail.valueChanges.pipe(
            debounceTime(2000)
        ).subscribe(value=>{
             this.searchUser(value);
        });
    }
    createFormGroup:FormGroup;
    userEmail=new FormControl();
    seachUserForm:FormGroup=this.fb.group({
        userEmail:this.userEmail
    });
    sourceUser:UserDto[];
    selectedUser:UserDto[];
    newProject=new Project();
    isLinear = true;
    @ViewChildren(MatSelectionList) userListSearched;
    constructor(private fb:FormBuilder,private userService:UserService,private notificationService:NotificationService) { }
    ngOnInit(): void { 
        this.createFormGroup=this.fb.group({
            name:['',Validators.compose([Validators.required,Validators.minLength(2)])],
            description:['',Validators.compose([Validators.required,Validators.minLength(10)])],
        })
        
    }

    searchUser(value){
        // console.log(value);
        if(value.trim()==''){
            return;
        }
        this.userService.findEmail(value).subscribe(data=>{
            if(data.userList){
                this.sourceUser=data.userList;
            }
        },(error)=>{
            if(error.ok==false){
                this.notificationService.showNotification(MessageType.ERROR,"Unknow error");
            }
        });
    }
    addPeople(){
        console.log(this.userListSearched);
    }
    stepChange(ev) {
        if ( ev.selectedIndex) {
        }
      }
      get createProjectFormControl() {
        return this.createFormGroup.controls;
      }
      remove(user:UserDto){
          var indexSelectedUser=this.selectedUser.indexOf(user);
          if(indexSelectedUser!=-1){
              this.selectedUser.splice(indexSelectedUser,1);
          }
      }
}
