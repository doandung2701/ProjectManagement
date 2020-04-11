import Axios from "axios";
import {BASE_URL,PROJECT_URL} from "../constants/Apiconstant";
const axiosInstance=Axios.create({
    baseURL:BASE_URL+PROJECT_URL,
    timeout:5000,
    headers:{
        'Access-Control-Allow-Origin':'*'
    }
})
export const getProjectByUserId=(userId,pageSize,pageNumber,filterText)=>{
    return axiosInstance.get(`getProjectUserJoined/${userId}?page=${pageNumber}&&size=${pageSize}&&filter=${filterText}`);
}
