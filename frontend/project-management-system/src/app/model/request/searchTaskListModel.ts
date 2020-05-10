import { Status } from './../status.enum';
export interface SearchTaskListModel{
    name:string;
    user:string;
    status:Status;
}