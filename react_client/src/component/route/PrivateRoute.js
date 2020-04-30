import React from 'react';
import {Route} from 'react-router-dom';
import AuthorizationLogic from "../../service/AuthorizationLogic";
import RedirectLogic from "../../service/RedirectLogic";

const PrivateRoute = ({component: Component}) => {
    return (
        <Route render={props => (
            AuthorizationLogic.isAdminLogin()
                ? <Component {...props} />
                : RedirectLogic.redirectToLogin()
        )}/>
    );
};

export default PrivateRoute;