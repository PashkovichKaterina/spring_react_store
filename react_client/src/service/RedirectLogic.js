import Util from "./Util";

const LOGIN_URL = "/login";
const SIGUP_URL = "/signup";
const ADD_URL = "/add";
const EDIT_URL = "/edit/{0}";
const CERTIFICATES_URL = "/certificates";

class RedirectLogic {
    redirect(url) {
        window.location.href = url;
    }

    reload() {
        window.location.reload();
    }

    redirectToLogin() {
        this.redirect(LOGIN_URL);
    }

    redirectToSignup() {
        this.redirect(SIGUP_URL);
    }

    redirectToAddPage() {
        this.redirect(ADD_URL);
    }

    redirectToEditPage(certificateId) {
        this.redirect(Util.format(EDIT_URL, certificateId));
    }

    redirectToCertificates() {
        this.redirect(CERTIFICATES_URL);
    }

    redirectToOAuthRegistration(provider) {
        this.redirect("http://localhost:8080/oauth2/authorization/" + provider + "?redirect_uri=http://localhost:3000/oauth2/redirect");
    }

    redirectToUnavailableServer() {
        this.redirect("/unavailable-server");
    }

    formUrl(searchParam, tagParam, price, minPrice, maxPrice, page, perPage, certificateCategory) {
        let url = "?";
        if (searchParam) {
            url += "search=" + searchParam + "&";
        }
        if (tagParam) {
            tagParam.forEach(tag => url += "tagTitle=" + tag + "&");
        }
        if (price) {
            price.forEach(p => url += "price=" + p + "&");
        }
        if (minPrice) {
            url += "minPrice=" + minPrice + "&";
        }
        if (maxPrice) {
            url += "maxPrice=" + maxPrice + "&";
        }
        url += "page=" + page + "&per_page=" + perPage;
        if (certificateCategory === "my") {
            url += "&category=my";
        }
        return url;
    }
}

export default new RedirectLogic();