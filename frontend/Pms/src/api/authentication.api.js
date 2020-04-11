import Axios from "axios";
import {BASE_URL,AUTHENTICATION_URL} from "../constants/Apiconstant";
const axiosInstance=Axios.create({
    baseURL:BASE_URL+AUTHENTICATION_URL,
    timeout:5000,
    headers:{
        'Access-Control-Allow-Origin':'*'
    }
})
export const loginApi=(username,password)=>{
    return axiosInstance.post("login",{username,password});
}
