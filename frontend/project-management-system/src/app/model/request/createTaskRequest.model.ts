import { Status } from '../status.enum';
import { Priority } from '../priority.enum';
import { CheckListRequest } from './checkListRequest.model';
export class CreateTaskRequest{
    public createdBy?:number;
    public deadline?:Date;
    public description?:string;
    public name?:string;
    public projectId?:number;
    public startTime?:Date;
    public status?:Status;
    public priority?:Priority;
    public categoryId?:Number;
    public checklists?:CheckListRequest[];
    public users?:number[];
}