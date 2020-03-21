import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { Observable, BehaviorSubject } from 'rxjs';
import {  finalize } from 'rxjs/operators';
import { PageResponse } from 'src/app/model/pageResponse.model';
import { Task } from 'src/app/model/task.model';
import { TaskService } from 'src/app/services/task.service';
import { TaskResponse } from 'src/app/model/response/taskResponse.model';
export class TaskDataSource implements DataSource<TaskResponse> {

    private tasksSubject = new BehaviorSubject<TaskResponse[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private paginationSubject = new BehaviorSubject<PageResponse>(new PageResponse(0, 0, 0, 0));
    public loading$ = this.loadingSubject.asObservable();
    constructor(private taskService: TaskService) { }
    connect(collectionViewer: CollectionViewer): Observable<TaskResponse[]> {
        return this.tasksSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.tasksSubject.complete();
        this.loadingSubject.complete();
    }

    loadTasks(filter: string,
        pageIndex: number, pageSize: number) {
        this.loadingSubject.next(true);
        this.taskService.getTaskOfUser(pageIndex, pageSize, filter).pipe(
            finalize(() => this.loadingSubject.next(false))
        ).subscribe(response => { this.tasksSubject.next(response.content); this.paginationSubject.next(response.page) });
    }

    public get paginiation() {
        return this.paginationSubject.value;
    }

}
