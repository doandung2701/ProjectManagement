export class Notification {
    id: string;
    title: string;
    content: string;
    url: string;
    userId: number;
    type: string;
    read?: boolean;
    send?: boolean;
    createdDate: Date;
    updatedDate?: Date;
    sendDate?: Date;
    readDate?: Date;
    taskId?:number;
    projectId?:number;
}