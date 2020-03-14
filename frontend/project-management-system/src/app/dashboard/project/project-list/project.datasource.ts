import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { Project } from 'src/app/model/project.model';
import { ProjectService } from 'src/app/services/project.service';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { PageResponse } from 'src/app/model/pageResponse.model';
export class ProjectDataSource implements DataSource<Project> {

    private projectsSubject = new BehaviorSubject<Project[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private paginationSubject = new BehaviorSubject<PageResponse>(new PageResponse(0, 0, 0, 0));
    public loading$ = this.loadingSubject.asObservable();
    constructor(private projectService: ProjectService) { }
    connect(collectionViewer: CollectionViewer): Observable<Project[]> {
        return this.projectsSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.projectsSubject.complete();
        this.loadingSubject.complete();
    }

    loadProjects(filter: string,
        pageIndex: number, pageSize: number) {
        this.loadingSubject.next(true);
        this.projectService.getAllProjectUserJoin(pageIndex, pageSize, filter).pipe(
            finalize(() => this.loadingSubject.next(false))
        ).subscribe(response => { this.projectsSubject.next(response.content); this.paginationSubject.next(response.page) });
    }

    public get paginiation() {
        return this.paginationSubject.value;
    }

}
