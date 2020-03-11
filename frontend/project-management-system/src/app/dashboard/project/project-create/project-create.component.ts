import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
    selector: 'dashboard-project-create',
    templateUrl: './project-create.component.html',
    styleUrls: ['./project-create.component.css']
})
export class ProjectCreateComponent implements OnInit {
    createFormGroup:FormGroup;
    constructor(private fb:FormBuilder) { }
    ngOnInit(): void { 
        this.createFormGroup=this.fb.group({
            name:['',Validators.compose([Validators.required,Validators.minLength(2)])],
            description:['',Validators.compose([Validators.required,Validators.minLength(10)])],
        })
    }
}
