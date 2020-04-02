export class CodeDTO{
    public code:string;
    public uid:number;
    constructor(
        code:string,
        uid:number
    ){
        this.code=code;
        this.uid=uid;
    }
}