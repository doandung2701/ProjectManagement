import Axios from "axios";
import { BASE_API } from "../contants";
const axios=Axios.create({
    baseURL:BASE_API+"authentication/",
    timeout:5000
})
export const loginApi=(loginPayload)=>{
    return axios.post("login",JSON.stringify(loginPayload));
}
export const signUpApi=(signupPayload)=>{
    return axios.post("signup",JSON.stringify(signupPayload));
}