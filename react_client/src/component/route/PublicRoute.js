import React from 'react';
import {Route} from 'react-router-dom';
import AuthorizationLogic from "../../service/AuthorizationLogic";
import RedirectLogic from "../../service/RedirectLogic";

const PublicRoute = ({component: Component}) => {
    return (
        <Route render={props => (
            AuthorizationLogic.isUserLogin()
                ? RedirectLogic.redirectToCertificates()
                : <Component {...props} />
        )}/>
    )
};

export default PublicRoute;