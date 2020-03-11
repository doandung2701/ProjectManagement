import { Injectable } from '@angular/core';
import { MessageType } from 'src/app/model/typeMessage';
import { MatSnackBar } from '@angular/material';

@Injectable({
    providedIn:'root'
})
export class NotificationService {
    constructor(private _snackBar:MatSnackBar){}
    showNotification(type:MessageType, content:string){
        switch (type) {
            case MessageType.SUCCESS:
                this._snackBar.open(content,'Close',{
                    duration:2000,
                    panelClass:'alert-success'
                });
                break;
            case MessageType.ERROR:
                this._snackBar.open(content,'Close',{
                    duration:2000,
                    panelClass:'alert-danger'
                });
                break;
            case MessageType.INFO:   
            this._snackBar.open(content,'Close',{
                duration:2000,
                panelClass:'alert-info'
            });
                break; 
            default:
                break;
        }
    }
}