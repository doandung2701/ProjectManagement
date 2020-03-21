import { Status } from '../status.enum';
import { ProjectRespone } from './projectResponse.model';
import { Priority } from '../priority.enum';
import { CategoryResponse } from './categoryResponse.model';
import { CheckListResponse } from './checkListResponse.model';
import { UserResponse } from './userResponse.model';

export class TaskResponse{
    public  id?:number;
	public  createdBy?:number;
	public  createdTime?:Date;
	public  deadline?:Date;
	public description?:string;
	public  modifiedTime?:Date;
	public name?:string;
	public  project?:ProjectRespone;
	public  startTime?:Date;
	public  status?:Status;
	public  priority?:Priority;
	public  category?:CategoryResponse;
	public checklists?:CheckListResponse[];
	public users?:UserResponse[];
}