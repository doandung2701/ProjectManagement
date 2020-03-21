import { Status } from './status.enum';

export class CheckListDto{
    public id?:number;
	public  description?:string;
	public  status?:Status;
}