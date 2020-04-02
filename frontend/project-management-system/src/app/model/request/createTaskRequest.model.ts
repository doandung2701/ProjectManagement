import { Status } from '../status.enum';
import { Priority } from '../priority.enum';
import { CheckListRequest } from './checkListRequest.model';
import { Category } from '../category.model';
export class CreateTaskRequest{
    public createdBy?:number;
    public deadlineView?:Date;
    public description?:string;
    public name?:string;
    public projectId?:number;
    public startTimeView?:Date;
    public status?:Status;
    public priority?:Priority;
    public category?:Category;
    public checklists?:CheckListRequest[];
    public users?:number[];
    public startTime?:string;
    public deadline?:string;
    public createdTime?:string;
}