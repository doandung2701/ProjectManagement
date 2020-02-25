import React, { useState } from 'react';
import { NavLink, Redirect, useLocation } from 'react-router-dom';
import { login as loginAction } from '../../redux/actions/authAction';
import { connect } from 'react-redux';
import { Button } from 'antd';
import isTokenValid from '../../utils/tokenValidation';
import validator from 'validator';

function LoginPage(props) {

    const { auth, login } = props;
  
    const [inputs, setInputs] = useState({
      username: '',
      password: ''
    });
  
    const location = useLocation()
  
    const handleInputsChange = (e) => {
      const { target } = e;
      const { name, value } = target;
      setInputs(prev => ({
        ...prev,
        [name]: value
      }))
      if (!value) {
      }
    }
  
    const onSubmit = (e) => {
      e.preventDefault();
      const { username, password } = inputs;
      if (username && password) {
        login(username, password);
      }
    }
    if (isTokenValid(auth.token)){
      return <Redirect to={location.state&&location.state.from?location.state.from:'/'} />
    }
  
    return (<div className="auth-page">
      <div className="limiter">
        <div className="container-login100" style={{ backgroundImage: 'url("https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1567&q=80")' }}>
          <div className="wrap-login100 p-l-70 p-r-70 p-t-50 p-b-33">
            <div className="d-flex justify-content-center p-b-20">
              <NavLink to="/"><img src="/images/logo/logo.png" height={50} /></NavLink>
            </div>
            <form onSubmit={onSubmit} className="login100-form validate-form flex-sb flex-w">
              <span className="login100-form-title p-b-20">
                Đăng nhập
                  </span>
              <div className="p-t-20 p-b-9">
                <span className="txt1">
                  Tên người dùng
                    </span>
              </div>
              <div className="w-100">
                <div className={`wrap-input100 validate-input${inputs.username?'':' error'}`} data-validate="Username is required">
                  <input className={`input100`} type="text" name="username"
                    onChange={handleInputsChange} value={inputs.username} />
                  <span className={`focus-input100${inputs.username?'':' error'}`} />
                </div>
                {!inputs.username&&<span className="error-txt">Username không được để trống</span>}
              </div>
              <div className="p-t-13 p-b-9">
                <span className="txt1">
                  Mật khẩu
                    </span>
                <a href="#" className="txt2 bo1 m-l-5">
                  Quên?
                    </a>
              </div>
              <div className="w-100">
                <div className={`wrap-input100 validate-input`} data-validate="Password is required">
                  <input className={`input100`} type="password" name="password"
                    onChange={handleInputsChange} value={inputs.password} />
                  <span className={`focus-input100${inputs.password?'':' error'}`} />
                </div>
                {!inputs.password&&<span className="error-txt">Password không được để trống</span>}
              </div>
              <div className="container-login100-form-btn m-t-17">
                <Button htmlType="submit" type="primary" loading={auth.loading} className="login100-form-btn">
                  Đăng nhập
              </Button>
              </div>
              <div className="w-full text-center p-t-55">
                <span className="txt2 p-r-4">
                  Chưa có tài khoản?
                    </span>
                <NavLink to="/auth/signup" className="txt2 bo1">
                  Đăng ký ngay
                    </NavLink>
              </div>
            </form>
          </div>
        </div>
      </div>
      <div id="dropDownSelect1" />
    </div>)
  
  }
  
  const mapStateToProps = state => {
    return {
      auth: state.auth
    }
  }
  
  const mapDispatchToProps = (dispatch, ownProps) => {
    return {
      login: (username, password) => {
        dispatch(loginAction({ username, password }))
      }
    }
  }
  
  export default connect(mapStateToProps, mapDispatchToProps)(LoginPage);