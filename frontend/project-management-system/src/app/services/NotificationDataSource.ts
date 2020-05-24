import { Notification } from './../model/response/Notification.model';
import { NotiService } from './noti.service';
import { DataSource } from '@angular/cdk/table';
import { BehaviorSubject, Observable } from 'rxjs';
import { PageResponse } from '../model/pageResponse.model';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';

export class NotificationDataSource implements DataSource<Notification>{
    private notificationsSubject = new BehaviorSubject<Notification[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private paginationSubject = new BehaviorSubject<PageResponse>(new PageResponse(0, 0, 0, 0));
    public loading$ = this.loadingSubject.asObservable();
    constructor(private notifyService: NotiService) { }
    connect(collectionViewer: CollectionViewer): Observable<Notification[]> {
        return this.notificationsSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.notificationsSubject.complete();
        this.loadingSubject.complete();
    }

    loadNotifications(userId: number,
        pageIndex: number, pageSize: number) {
        this.loadingSubject.next(true);
        this.notifyService.getNotifyOfUser(pageIndex, pageSize, userId).pipe(
            finalize(() => this.loadingSubject.next(false))
        ).subscribe(response => { this.notificationsSubject.next(response.content); this.paginationSubject.next(response.page) });
    }
    loadMoreNotifications(userId: number,
        pageIndex: number, pageSize: number){
            this.loadingSubject.next(true);
        this.notifyService.getNotifyOfUser(pageIndex, pageSize, userId).pipe(
            finalize(() => this.loadingSubject.next(false))
        ).subscribe(response => { 
            if(response.content.length>0){
                this.notificationsSubject.next([...this.notificationsSubject.getValue(),...response.content]); this.paginationSubject.next(response.page)
            }
         }); 
        }
    public get pagination() {
        return this.paginationSubject.value;
    }
    public get values(){
        return this.notificationsSubject.getValue();
    }

}