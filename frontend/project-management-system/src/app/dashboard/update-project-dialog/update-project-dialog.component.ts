import { ProjectDto } from 'src/app/model/response/ProjectDto';
import { GlobalService } from './../../services/global.service';
import { Project } from './../../model/project.model';
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-update-project-dialog',
  templateUrl: './update-project-dialog.component.html',
  styleUrls: ['./update-project-dialog.component.css']
})
export class UpdateProjectDialogComponent implements OnInit {
  updateFormGroup:FormGroup;
  projectName:string="";
  projectDescription:string="";
  constructor(private fb:FormBuilder,
    public dialogRef: MatDialogRef<UpdateProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public projectCommon: object) { 
      this.projectName=projectCommon["name"];
      this.projectDescription=projectCommon["description"];
    }
  onNoClick(): void {
    this.dialogRef.close();
  }
  onYesClick(): void {
    if(this.updateFormGroup.invalid){
      return;
    }else{
      var data={
        name:this.updateProjectFormControl.name.value,
        description:this.updateProjectFormControl.description.value
      }
      this.dialogRef.close(data);
    }
  }

  ngOnInit(): void { 

    this.updateFormGroup=this.fb.group({
      name:[this.projectName,Validators.compose([Validators.required,Validators.minLength(2)])],
      description:[this.projectDescription,Validators.compose([Validators.required,Validators.minLength(10)])],
  })
  }
  get updateProjectFormControl() {
    return this.updateFormGroup.controls;
  }
}
