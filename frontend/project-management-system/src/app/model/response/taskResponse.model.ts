import { Status } from '../status.enum';
import { ProjectRespone } from './projectResponse.model';
import { Priority } from '../priority.enum';
import { CheckListResponse } from './checkListResponse.model';
import { UserResponse } from './userResponse.model';
import { Category } from '../category.model';

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
	public  category?:Category;
	public checklists?:CheckListResponse[];
	public users?:UserResponse[];
}