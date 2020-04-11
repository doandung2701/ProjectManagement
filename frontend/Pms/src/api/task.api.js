import Axios from "axios";
import {BASE_URL,TASK_URL} from "../constants/Apiconstant";
const axiosInstance=Axios.create({
    baseURL:BASE_URL+TASK_URL,
    timeout:5000,
    headers:{
        'Access-Control-Allow-Origin':'*'
    }
})
export const getTaskByUserId=(userId,pageSize,pageNumber,filterText)=>{
    return axiosInstance.get(`getUserTasks/${userId}?page=${pageNumber}&&size=${pageSize}&&filter=${filterText}`);
}
export const getTaskById=(taskId)=>{
    return axiosInstance.get(`getDetail/${taskId}`);
}
export const createChecklist=(taskId,{description,status})=>{
    return axiosInstance.post(`${taskId}/addCheckList`,{description,status});
}
export const removeCheckList=(taskId,checkListId)=>{
    return axiosInstance.get(`${taskId}/removeCheckList/${checkListId}`);
}
export const updateCheckList=(taskId,{id,description,status})=>{
    return axiosInstance.post(`${taskId}/updateCheckList`,{id,description,status});
}
export const createComment=(taskID,{userId,username,taskId,content})=>{
    return axiosInstance.post(`addComment/${taskID}`,{userId,username,taskId,content})
}
export const getCommentBytaskId=(taskId)=>{
    return axiosInstance.get(`getComment/${taskId}`);
}