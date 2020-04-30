import React from 'react';
import "../../style/common.css"
import userIcon from "../../image/userIcon.png";
import Localization from "../../localization/Localization";
import AuthorizationLogic from "../../service/AuthorizationLogic";
import RedirectLogic from "../../service/RedirectLogic";

class HeaderContainer extends React.PureComponent {
    logout = () => {
        AuthorizationLogic.deleteAccessToken();
        RedirectLogic.reload();
    };

    handleChangeLanguage = (event) => {
        Localization.setLang(event.target.value);
    };

    handleSiteTitleClick = () => {
        RedirectLogic.redirectToCertificates();
    };

    handleLoginButtonClick = () => {
        RedirectLogic.redirectToLogin();
    };
    handleSignupButtonClick = () => {
        RedirectLogic.redirectToSignup();
    };

    render() {
        const button = AuthorizationLogic.isUserLogin()
            ? <li className="right">
                <img src={userIcon} alt={Localization.getString("userIcon")} className="user-icon"/>
                <span>{AuthorizationLogic.getUserLogin()}</span>
                <span onClick={this.logout}>{Localization.getString("logout")}</span>
            </li>
            : <li className="right">
                <span onClick={this.handleLoginButtonClick}>{Localization.getString("login")}</span>
                <span onClick={this.handleSignupButtonClick}>{Localization.getString("signup")}</span>
            </li>;
        return (
            <nav>
                <ul>
                    <li className="left" onClick={this.handleSiteTitleClick}>
                        {Localization.getString("certificates")}
                    </li>
                    <li>
                        <button value="ru"
                                className="localization-button"
                                onClick={this.handleChangeLanguage}>
                            {Localization.getString("ru")}
                        </button>
                        <button value="en"
                                className="localization-button"
                                onClick={this.handleChangeLanguage}>
                            {Localization.getString("en")}
                        </button>
                    </li>
                    {button}
                </ul>
            </nav>
        )
    }
}

export default HeaderContainer;