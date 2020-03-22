import { Status } from '../status.enum';
import { Priority } from '../priority.enum';
import { Category } from '../category.model';

export default class UpdateCommonTaskRequest{
    public  taskId:number;
	public taskName:string;
	public taskDescription:string;
	public deadline:string;
    public startTime:string;
    public status:Status;
    public priority:Priority;
    public category:Category;
	public  users:number[];
}