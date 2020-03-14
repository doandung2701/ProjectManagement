import { Component, OnInit, ViewChild, ViewChildren, AfterViewInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Project } from 'src/app/model/project.model';
import { MatSelectionList, PageEvent } from '@angular/material';
import { UserService } from 'src/app/services/user.service';
import { NotificationService } from 'src/app/common/service/notification.sevice';
import { UserDto } from 'src/app/model/userDto.model';
import { debounceTime, tap } from 'rxjs/operators'; 
import { MessageType } from 'src/app/model/typeMessage';
import { PageResponse } from 'src/app/model/pageResponse.model';
import { APIPaginationResponse } from 'src/app/model/APIPaginationResponse.model';
import { ProjectService } from 'src/app/services/project.service';
import { NewProjectDto } from 'src/app/model/newProjectDTO.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';
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
    pageSoureUser:PageResponse=new PageResponse(0,0,0,0);
    selectedUser:UserDto[];
    newProject=new Project();
    isLinear = true;
    @ViewChildren(MatSelectionList) userListSearched;
    constructor(private fb:FormBuilder,private userService:UserService,private notificationService:NotificationService,
        private projectService:ProjectService,private authenticationService:AuthenticationService,
        private router: Router) { }
    ngOnInit(): void { 
        this.createFormGroup=this.fb.group({
            name:['',Validators.compose([Validators.required,Validators.minLength(2)])],
            description:['',Validators.compose([Validators.required,Validators.minLength(10)])],
        })
        
    }
    createProject(){
        if(this.createProjectFormControl.name.value.trim()==''){
            this.notificationService.showNotification(MessageType.ERROR,"Name of project is required");
            return;
        }
        if(this.createProjectFormControl.description.value.trim()==''){
            this.notificationService.showNotification(MessageType.ERROR,"Description of project is required");
            return;
        }
        if(this.selectedUser.length==0){
            this.notificationService.showNotification(MessageType.INFO,"No user was added to project?");
        }
        let projectDto=new NewProjectDto();
        projectDto.name=this.createProjectFormControl.name.value;
        projectDto.description=this.createProjectFormControl.description.value;
        projectDto.admin=this.authenticationService.currentUserValue.uid;
        projectDto.users=this.selectedUser.map(x=>x.id);
        this.projectService.createProject(projectDto).subscribe(data=>{
            if(data==true){
                this.notificationService.showNotification(MessageType.SUCCESS,"Create project success");
                this.router.navigate(['/dashboard/project/joinedProject']);
            }else{
                this.notificationService.showNotification(MessageType.ERROR,"Create project fail");
            }
        },err=>{
            this.notificationService.showNotification(MessageType.ERROR,"Unknow error");
        })
    }
    changPage(event:PageEvent){
        this.userService.findEmail(this.userEmail.value,event.pageIndex,event.pageSize).subscribe(data=>{
            // if(data.userList){
            //     this.sourceUser=data.userList;
            // }
            this.sourceUser=data.content;
            this.pageSoureUser=data.page;
            if(data.content.length==0){
                this.notificationService.showNotification(MessageType.INFO,"No record found");
            }
        },(error)=>{
            if(error.ok==false){
                this.notificationService.showNotification(MessageType.ERROR,"Unknow error");
            }
        });
    }
    searchUser(value){
        // console.log(value);
        if(value.trim()==''){
            return;
        }
        this.userService.findEmail(value,0,5).subscribe(data=>{
            // if(data.userList){
            //     this.sourceUser=data.userList;
            // }
            this.sourceUser=data.content;
            this.pageSoureUser=data.page;
        },(error)=>{
            if(error.ok==false){
                this.notificationService.showNotification(MessageType.ERROR,"Unknow error");
            }
        });
    }
    addPeople(){
        // console.log(this.userListSearched);
        this.selectedUser=this.userListSearched._results[0]._value;
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
