import { Router } from '@angular/router';
import { ProjectService } from './../../services/project.service';
import { GlobalService } from 'src/app/services/global.service';
import { TaskResponse } from './../../model/response/taskResponse.model';
import { TaskService } from 'src/app/services/task.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-top5taskdeadline',
  templateUrl: './top5taskdeadline.component.html',
  styleUrls: ['./top5taskdeadline.component.css']
})
export class Top5taskdeadlineComponent implements OnInit {

  constructor(private taskService:TaskService,private globalService:GlobalService,private projectService:ProjectService,private router:Router) { }
  data:TaskResponse[];
  ngOnInit() {
    this.taskService.getTop5TaskOrderByDeadline().subscribe(response=>{
      this.data=response;
      console.log(response);
    });
  }
  goToDetail(item:TaskResponse){
    this.globalService.setCurrentprojectId(item.project.id);
    this.projectService.GetDetailProjectById(item.project.id).subscribe(response=>{
       this.globalService.setCurrentProjectDetail(response);
      this.router.navigate([`/dashboard/task/detail/${item.id}`]);
    });
  }
  convertArrayToDate(input:number[]){
    let dateInstring=input[0]+"-"+input[1]+"-"+input[2]+" "+input[3]+":"+input[4];
     return new Date(dateInstring);
  }
}
