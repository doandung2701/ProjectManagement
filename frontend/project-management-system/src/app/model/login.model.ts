import { Deserializable } from './deserializable.model';

export class LoginModel implements Deserializable{
    deserialize(input: any) {
        Object.assign(this,input);
        return this;
    }
    username:string;
    password:string;
}