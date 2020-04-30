import jwt_decode from "jwt-decode";

const ACCESS_TOKEN = "accessToken";

class AuthorizationLogic {
    isUserLogin() {
        return localStorage.getItem(ACCESS_TOKEN) !== null;
    }

    isAdminLogin() {
        return this.getToken() !== null
            && jwt_decode(this.getToken()).role === "ROLE_ADMIN";
    }

    setAccessToken(token) {
        localStorage.setItem(ACCESS_TOKEN, token);
    }

    deleteAccessToken() {
        localStorage.removeItem(ACCESS_TOKEN);
    }

    getToken() {
        return localStorage.getItem(ACCESS_TOKEN);
    }

    getUserLogin() {
        const token = localStorage.getItem(ACCESS_TOKEN);
        if (token) {
            return jwt_decode(token).sub;
        }
    }

    getUserId() {
        const token = localStorage.getItem(ACCESS_TOKEN);
        if (token) {
            return jwt_decode(token).id;
        }
    }

    isValidToken() {
        const token = localStorage.getItem(ACCESS_TOKEN);
        if (token) {
            return Date.now() < jwt_decode(token).exp;
        }
        return true;
    }
}

export default new AuthorizationLogic();