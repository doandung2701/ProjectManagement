import React, { Fragment } from 'react';
import { Switch, Route, useHistory } from 'react-router-dom';
import LoginPage from './page/loginPage/loginPage';
import SignupPage from './page/signupPage/signupPage';
import HomePage from './page/homePage/homePage';
import NotFound404Page from './page/notFound404Page/NotFound404Page';
function App(props) {
  // let history = useHistory();
  // const isLoginPage = history.location.pathname.endsWith("/auth/login") || history.location.pathname.endsWith("/auth/signup") 
  return (
    <Fragment>
      <Switch>
        <Route path="/" exact component={HomePage}></Route>
        <Route path="/home" exact component={HomePage}></Route>
        <Route path="/auth/login" exact component={LoginPage}></Route>
        <Route path="/auth/signup" exact component={SignupPage}></Route>
        <Route component={NotFound404Page}></Route>
      </Switch>
    </Fragment>

  );
}

export default App;
