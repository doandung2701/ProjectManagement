import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { CheckListDto } from 'src/app/model/checklistDto.model';

@Component({
    selector: 'task-checklist-update',
    templateUrl: './checklist-update.component.html',
    styleUrls: ['./checklist-update.component.css']
})
export class CheckListUpdateComponent implements OnInit {
    constructor(private fb:FormBuilder,public dialogRef:MatDialogRef<CheckListUpdateComponent> ,@Inject(MAT_DIALOG_DATA) public data:CheckListDto) { 
        this.checklistForm=fb.group({
            description:[this.data.description,[Validators.required]],
            statusTask:[this.data.status,[]]
        });
    }
    checklistForm:FormGroup;
    ngOnInit(): void { }
    close(){
        this.dialogRef.close();
    }
    handleSave(){
        if(this.checklistForm.valid){
            this.data.status=this.checklistForm.controls.statusTask.value;
            this.data.description=this.checklistForm.controls.description.value;
             this.dialogRef.close(this.data);     
        }
    }
}
