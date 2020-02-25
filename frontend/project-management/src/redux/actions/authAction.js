import { LOGGING_IN, LOG_IN_SUCCESSFULLY, LOG_IN_FAILED, SIGNING_UP, SIGN_UP_SUCCESSFULLY, SIGN_UP_FAILED, LOG_OUT } from "../../contants";
import { loginApi, signUpApi } from "../../api/authAPI";
import history from "../../utils/history";

const loggingIn = () => ({
    type: LOGGING_IN
});

const loginSuccessfully = (authPayload) => ({
    type: LOG_IN_SUCCESSFULLY,
    authPayload
})

const loginFailed = () => ({
    type: LOG_IN_FAILED,
})


const signingUp = () => ({
    type: SIGNING_UP
});

const signUpSuccessfully = (authPayload) => ({
    type: SIGN_UP_SUCCESSFULLY,
    authPayload
})

const signUpFailed = () => ({
    type: SIGN_UP_FAILED,
})

export const logout = ()=>{
    localStorage.removeItem('authPayload');
    localStorage.removeItem('token');
    history.push('/');
    return {
        type: LOG_OUT
    }
}

export const login = ({ username, password }) => {
    return async dispatch => {
        dispatch(loggingIn());
        try {
            const res = await loginApi({username,password});
            localStorage.setItem('authPayload', JSON.stringify(res.data));
            localStorage.setItem('token', res.data.token);
            dispatch(loginSuccessfully(res.data));
        } catch (ex) {
            console.log(ex)
            dispatch(loginFailed());
        }
    }
}

export const signUp = ({ name,username, email, password,user }) => {
    return async dispatch => {
        dispatch(signingUp());
        try {
            const res =await signUpApi({ name,username, email, password,user });
            if(res.data){
                dispatch(signUpSuccessfully());
                history.push("/login");
            }else{
                dispatch(signUpFailed());
            }
           
        } catch (ex) {
            dispatch(signUpFailed());
        }
    }
}