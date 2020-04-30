import React from 'react';
import '../../style/authentication.css';
import InputElement from "./InputElement";
import InformPopup from "../popup/InformPopup";
import welcomeImage from '../../image/welcome.png'
import ButtonElementContainer from "./ButtonElementContainer";
import AuthenticationService from "../../service/AuthenticationService";
import FormValidator from "../../service/FormValidator";
import AuthorizationLogic from "../../service/AuthorizationLogic";
import RedirectLogic from "../../service/RedirectLogic";
import Localization from "../../localization/Localization";

class LoginContainer extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            loginOrEmail: "",
            password: ""
        };
    }

    handleKeyDown = (event) => {
        if (event.keyCode === 13 && !FormValidator.isValidLoginForm(this.state.loginOrEmail, this.state.password)) {
            event.preventDefault();
        }
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const user = {
            login: this.state.loginOrEmail,
            password: this.state.password
        };
        AuthenticationService.login(user)
            .then(response => {
                this.setState({isShowPopup: !response.ok});
                return response.json()
            })
            .then(json => {
                if (!this.state.isShowPopup) {
                    AuthorizationLogic.setAccessToken(json.token);
                    RedirectLogic.redirectToCertificates();
                } else {
                    this.setState({errorCode: json.errorCode});
                }
            });
    };

    handleChange = (event) => {
        const {name, value} = event.target;
        this.setState({
            [name]: value
        });
    };

    closePopup = () => {
        this.setState({
            isShowPopup: false
        });
    };

    oauth = (event) => {
        RedirectLogic.redirectToOAuthRegistration(event.target.value);
    };

    render() {
        const {loginOrEmail, password, isShowPopup, errorCode} = this.state;
        const errorPopup = isShowPopup &&
            <InformPopup type="error" text={Localization.getString(errorCode)} close={this.closePopup}/>;
        return (
            <main className="authentication">
                <div>
                    {errorPopup}
                    <h1>SiteName</h1>
                    <div className="half">
                        <form className="authentication" onSubmit={this.handleSubmit} onKeyDown={this.handleKeyDown}>
                            <InputElement type="text"
                                          name="loginOrEmail"
                                          value={loginOrEmail}
                                          isValid={true}
                                          onChange={this.handleChange}/>
                            <InputElement type="password"
                                          name="password"
                                          value={password}
                                          isValid={true}
                                          onChange={this.handleChange}/>
                            <ButtonElementContainer name="login"
                                                    isSubmitEnable={FormValidator.isValidLoginForm(loginOrEmail, password)}/>
                            <div className="oauth-button-block">
                                <button type="button"
                                        className="oauth-button"
                                        value="google"
                                        onClick={this.oauth}>
                                    {Localization.getString("googleLogin")}
                                </button>
                                <button type="button"
                                        className="oauth-button"
                                        value="github"
                                        onClick={this.oauth}>
                                    {Localization.getString("githubLogin")}
                                </button>
                            </div>
                        </form>
                    </div>
                    <div className="half right">
                        <img src={welcomeImage} alt={Localization.getString("welcomePicture")}/>
                    </div>
                </div>
            </main>
        )
    }
}

export default LoginContainer;