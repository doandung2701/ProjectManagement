import { Component, OnInit, Input, Inject } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CheckListDto } from 'src/app/model/checklistDto.model';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
    selector: 'task-checklist-form',
    templateUrl: './checklist-form.component.html',
    styleUrls: ['./checklist-form.component.css']
})
export class CheckListFormComponent  {
    // @Input() data:CheckListDto;
    id:number;
    checklistForm:FormGroup;
    constructor(private fb:FormBuilder,public dialogRef:MatDialogRef<CheckListFormComponent> ,@Inject(MAT_DIALOG_DATA) public data) { 
        this.checklistForm=fb.group({
            description:['',[Validators.required]],
            statusTask:[false,[]]
        });
    }
    editCheckList(data:CheckListDto){
        this.id=data.id;
        this.checklistForm.setValue({
            description:data.description,
            status:data.status
        })
    }
    createCheckList(){
        this.id=null;
    }
    get createChecklistFormControl() {
        return this.checklistForm.controls;
      }
      close(){
          this.dialogRef.close();
      }
      handleSave(){
          if(this.checklistForm.valid){
              var data:CheckListDto=new CheckListDto();
               if(this.data.checkListId==null){
                   data.id=null;
                   data.status=this.checklistForm.controls.statusTask.value;
                   data.description=this.checklistForm.controls.description.value;
               }else{
                data.id=this.data.checkListId;
                data.status=this.checklistForm.controls.statusTask.value;
                data.description=this.checklistForm.controls.description.value;
               }
               this.dialogRef.close(data);     
          }
      }
}
