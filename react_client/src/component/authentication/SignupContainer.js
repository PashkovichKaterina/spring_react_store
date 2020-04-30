import React from 'react';
import '../../style/authentication.css';
import ButtonElementContainer from "./ButtonElementContainer";
import InputElement from "./InputElement";
import InformPopup from "../popup/InformPopup";
import welcomeImage from '../../image/welcome.png'
import AuthenticationService from "../../service/AuthenticationService";
import Util from "../../service/Util";
import FormValidator from "../../service/FormValidator";
import Localization from "../../localization/Localization";
import AuthorizationLogic from "../../service/AuthorizationLogic";
import RedirectLogic from "../../service/RedirectLogic";

const LOGIN = "login";
const EMAIL = "email";
const PASSWORD = "password";
const CONFIRM_PASSWORD = "confirmPassword";

class SignupContainer extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            login: "",
            email: "",
            password: "",
            confirmPassword: "",
            isValidLogin: true,
            isValidEmail: true,
            isValidPassword: true,
            isValidConfirmPassword: true
        };
    }

    handleKeyDown = (event) => {
        if (event.keyCode === 13 && !FormValidator.isValidSignupForm(this.state.login, this.state.email, this.state.password, this.state.confirmPassword)) {
            event.preventDefault();
        }
    };

    handleSubmitSignupForm = (event) => {
        event.preventDefault();
        const user = {
            login: this.state.login,
            email: this.state.email,
            password: this.state.password
        };
        AuthenticationService.signup(user)
            .then(response => {
                this.setState({isShowPopup: !response.ok});
                return response.json();
            })
            .then((json) => {
                if (!this.state.isShowPopup) {
                    AuthenticationService.login(user)
                        .then(response => response.json())
                        .then(json => {
                            AuthorizationLogic.setAccessToken(json.token);
                            RedirectLogic.redirectToCertificates();
                        })
                } else {
                    this.setState({errorCode: json.errorCode});
                }
            });
    };

    handleChangeInputField = (event) => {
        const {name, value} = event.target;
        this.setState({
            [name]: value
        });
        this.checkInputField(name, value);
    };

    checkInputField(name, value) {
        const {password, confirmPassword} = this.state;
        let field = `isValid${Util.capitalize(name)}`;
        let isValidField = false;
        switch (name) {
            case LOGIN:
                isValidField = FormValidator.isValidLogin(value);
                break;
            case EMAIL:
                isValidField = FormValidator.isValidEmail(value);
                break;
            case PASSWORD:
                isValidField = FormValidator.isValidPassword(value);
                if (confirmPassword) {
                    this.setState({
                        isValidConfirmPassword: FormValidator.isValidConfirmPassword(value, confirmPassword)
                    });
                }
                break;
            case CONFIRM_PASSWORD:
                isValidField = FormValidator.isValidConfirmPassword(password, value);
        }
        this.setState({[field]: isValidField});
    }

    closePopup = () => {
        this.setState({isShowPopup: false});
    };

    oauth = (event) => {
        RedirectLogic.redirectToOAuthRegistration(event.target.value);
    };

    render() {
        const {
            login, email, password, confirmPassword, isValidLogin, isValidEmail, isValidPassword,
            isValidConfirmPassword, isShowPopup, errorCode
        } = this.state;
        const errorPopup = isShowPopup &&
            <InformPopup type="error" text={Localization.getString(errorCode)} close={this.closePopup}/>;
        return (
            <main className="authentication">
                <div>
                    {errorPopup}
                    <h1>SiteName</h1>
                    <div className="half">
                        <form className="authentication" onSubmit={this.handleSubmitSignupForm}
                              onKeyDown={this.handleKeyDown}>
                            <InputElement type="text"
                                          name="login"
                                          value={login}
                                          isValid={isValidLogin}
                                          onChange={this.handleChangeInputField}/>
                            <InputElement type="text"
                                          name="email"
                                          value={email}
                                          isValid={isValidEmail}
                                          onChange={this.handleChangeInputField}/>
                            <InputElement type="password"
                                          name="password"
                                          value={password}
                                          isValid={isValidPassword}
                                          onChange={this.handleChangeInputField}/>
                            <InputElement type="password"
                                          name="confirmPassword"
                                          value={confirmPassword}
                                          isValid={isValidConfirmPassword}
                                          onChange={this.handleChangeInputField}/>
                            <ButtonElementContainer name="signup"
                                                    isSubmitEnable={FormValidator.isValidSignupForm(login, email, password, confirmPassword)}/>
                            <div className="oauth-button-block">
                                <button type="button"
                                        className="oauth-button"
                                        value="google"
                                        onClick={this.oauth}>
                                    {Localization.getString("googleSignup")}
                                </button>
                                <button type="button"
                                        className="oauth-button"
                                        value="github"
                                        onClick={this.oauth}>
                                    {Localization.getString("githubSignup")}
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

export default SignupContainer;