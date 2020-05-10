import { UserDto } from './../userDto.model';
export interface  ProjectDto{
    id:number;
    name:string;
    description:string;
    users:UserDto[];
    admin:number;
}