import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskResponse } from 'src/app/model/response/taskResponse.model';
import { TaskService } from 'src/app/services/task.service';

@Component({
    selector: 'task-task-detail',
    templateUrl: './task-detail.component.html',
    styleUrls: ['./task-detail.component.css']
})
export class TaskDetailComponent implements OnInit {
    taskDetail:TaskResponse;
    constructor(private route:ActivatedRoute,private taskService:TaskService) { }
    
    ngOnInit(): void { 
        this.taskService.getById(this.route.snapshot.paramMap.get("taskId")).subscribe(data=>{
            this.taskDetail=data;
        });
    }
}
