import AuthorizationLogic from "../../service/AuthorizationLogic";
import RedirectLogic from "../../service/RedirectLogic";

const OAuth2RedirectHandler = () => {
    const token = window.location.search.substr(7);

    if (token) {
        AuthorizationLogic.setAccessToken(token);
        RedirectLogic.redirectToCertificates();
    } else {
        RedirectLogic.redirectToLogin();
    }
};

export default OAuth2RedirectHandler;