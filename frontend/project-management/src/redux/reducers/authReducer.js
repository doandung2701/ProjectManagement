import { LOGGING_IN, LOG_IN_SUCCESSFULLY, LOG_IN_FAILED, SIGN_UP_SUCCESSFULLY, SIGN_UP_FAILED, LOG_OUT, SIGNING_UP } from "../../contants";

let authPayload = localStorage.getItem('authPayload');
try {
    if (authPayload) {
        authPayload = JSON.parse(authPayload);
    }
} catch{
    authPayload = undefined;
}
const initialState = {
    user: authPayload ? authPayload.user : undefined,
    token: authPayload ? authPayload.token : undefined,
    loading: false,
    errorLogin: false,
    errorSignup: false
}
export default function authReducer(state = initialState, action) {
    switch (action.type) {
        case LOGGING_IN:
            return {
                ...state,
                loading: true,
            }
        case LOG_IN_SUCCESSFULLY:
            return {
                ...state,
                user: action.authPayload.user,
                token: action.authPayload.token,
                loading: false,
            }
        case LOG_IN_FAILED:
            return {
                ...state,
                loading: false
            }
        case SIGNING_UP:
            return {
                ...state,
                loading: true,
            }
        case SIGN_UP_SUCCESSFULLY:
            return {
                ...state,
                loading: false,
            }
        case SIGN_UP_FAILED:
            return {
                ...state,
                loading: false
            }
        case LOG_OUT:
            return {
                user: undefined,
                token: undefined,
                loading: false,
                errorLogin: false,
                errorSignup: false
            }
        default: return state;
    }
}