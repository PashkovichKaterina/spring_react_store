const DURATION_MIN_VALUE = 0;
const DURATION_MAX_VALUE = 2147483647;
const PRICE_MIN_VALUE = 0;
const PRICE_MAX_VALUE = 92233720368547759;

class FormValidator {
    isValidSignupForm(login, email, password, confirmPassword) {
        return this.isValidLogin(login) && this.isValidEmail(email) && this.isValidPassword(password)
            && this.isValidConfirmPassword(password, confirmPassword);
    }

    isValidLoginForm(email, password) {
        return email && password
            && email.length > 0 && password.length > 0;
    }

    isValidCertificateForm(title, description, price, duration) {
        return this.isValidCertificateName(title) && this.isValidCertificateDescription(description)
            && this.isValidCertificatePrice(price) && this.isValidCertificateDuration(duration);
    }

    isValidLogin(login) {
        return login && login.match(/^\w{1,30}$/);
    }

    isValidPassword(password) {
        return password && password.match(/(?=.*[0-9])(?=.*[A-z])[0-9A-z]{8,}/);
    }

    isValidConfirmPassword(password, confirmPassword) {
        return password === confirmPassword;
    }

    isValidEmail(email) {
        return email && email.match(/^([a-zA-Z0-9_\-.]+)@([a-zA-Z0-9_\-.]+)\.([a-zA-Z]{2,5})$/) != null && email.length < 250;
    }

    isValidCertificateName(name) {
        return name && name.match(/^\w{1,30}$/);
    }

    isValidCertificateDescription(description) {
        return description && description.match(/^.{1,1000}$/);
    }

    isValidCertificateDuration(duration) {
        duration = Number(duration);
        return Number.isInteger(duration) && duration > DURATION_MIN_VALUE && duration < DURATION_MAX_VALUE;
    }

    isValidCertificatePrice(price) {
        return !isNaN(price) && price > PRICE_MIN_VALUE && price < PRICE_MAX_VALUE;
    }

    isValidTagTitle(title) {
        return title && title.match(/^\w{1,15}$/);
    }
}

export default new FormValidator();