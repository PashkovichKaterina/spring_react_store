import React from 'react';
import '../../style/app.css';
import {
    BrowserRouter,
    Route, Switch
} from 'react-router-dom';
import OAuth2RedirectHandler from "../authentication/OAuth2RedirectHandler";
import PrivateRoute from "../route/PrivateRoute";
import PublicRoute from "../route/PublicRoute";
import Footer from "../common/Footer";
import ErrorPage from "./ErrorPage";
import HeaderContainer from "../common/HeaderContainer";
import LoginContainer from "../authentication/LoginContainer";
import SignupContainer from "../authentication/SignupContainer";
import CertificatesContainer from "../certificates/CertificatesContainer";
import CertificateFormContainer from "../certificates/CertificateFormContainer";

const App = () => (
    <div className="wrapper">
        <HeaderContainer/>
        <BrowserRouter>
            <Switch>
                <PublicRoute component={LoginContainer} path="/login"/>
                <PublicRoute component={SignupContainer} path="/signup"/>
                <Route path="/certificates" render={() => <CertificatesContainer/>}/>
                <PrivateRoute component={CertificateFormContainer} path="/add"/>
                <PrivateRoute component={CertificateFormContainer} path="/edit/:id"/>
                <Route path="/oauth2/redirect" render={() => <OAuth2RedirectHandler/>}/>
                <Route path="/unavailable-server" render={() => <ErrorPage type="unavailableServer"/>}/>
                <Route path="/" render={() => <ErrorPage type="notFound"/>}/>
            </Switch>
        </BrowserRouter>
        <Footer/>
    </div>
);

export default App;