import FormValidator from "../../service/FormValidator";
import {describe, it} from "mocha";
import {expect} from 'chai';

describe('FormValidator', () => {
    describe('isValidLogin', () => {
            it("'user1'", () => {
                const login = "user1";
                const result = FormValidator.isValidLogin(login);
                expect(Boolean(result)).to.be.true;
            });
            it("'user123456789012345678901234567890'", () => {
                const login = "user123456789012345678901234567890";
                const result = FormValidator.isValidLogin(login);
                expect(Boolean(result)).to.be.false;
            });
            it("empty", () => {
                let login;
                const result = FormValidator.isValidLogin(login);
                expect(Boolean(result)).to.be.false;
            });
        }
    );
    describe('isValidPassword', () => {
        it("'qwerty1234'", () => {
            const password = "qwerty1234";
            const result = FormValidator.isValidPassword(password);
            expect(Boolean(result)).to.be.true;
        });
        it("'12345678'", () => {
            const password = "12345678";
            const result = FormValidator.isValidPassword(password);
            expect(Boolean(result)).to.be.false;
        });
        it("'qwertyui'", () => {
            const password = "qwertyui";
            const result = FormValidator.isValidPassword(password);
            expect(Boolean(result)).to.be.false;
        });
        it("'12qw'", () => {
            const password = "qwertyui";
            const result = FormValidator.isValidPassword(password);
            expect(Boolean(result)).to.be.false;
        });
    });
    describe('isValidConfirmPassword', () => {
        it("'qwerty1234' - 'qwerty1234'", () => {
            const password = "qwerty1234";
            const confirmPassword = "qwerty1234";
            const result = FormValidator.isValidConfirmPassword(password, confirmPassword);
            expect(Boolean(result)).to.be.true;
        });
        it("'qwerty1234' - 'qwerty12345'", () => {
            const password = "qwerty1234";
            const confirmPassword = "qwerty12345";
            const result = FormValidator.isValidConfirmPassword(password, confirmPassword);
            expect(Boolean(result)).to.be.false;
        });
    });
    describe('isValidEmail', () => {
        it("'user@gmail.com'", () => {
            const email = "user@gmail.com";
            const result = FormValidator.isValidEmail(email);
            expect(Boolean(result)).to.be.true;
        });
        it("'user'", () => {
            const email = "user";
            const result = FormValidator.isValidEmail(email);
            expect(Boolean(result)).to.be.false;
        });
        it("empty", () => {
            let email;
            const result = FormValidator.isValidEmail(email);
            expect(Boolean(result)).to.be.false;
        });
    });
    describe('isValidCertificatePrice', () => {
        it("'12.5'", () => {
            const price = "12.5";
            const result = FormValidator.isValidCertificatePrice(price);
            expect(Boolean(result)).to.be.true;
        });
        it("'12'", () => {
            const price = "12";
            const result = FormValidator.isValidCertificatePrice(price);
            expect(Boolean(result)).to.be.true;
        });
        it("'0'", () => {
            const price = "0";
            const result = FormValidator.isValidCertificatePrice(price);
            expect(Boolean(result)).to.be.false;
        });
        it("'-1'", () => {
            const price = "-1";
            const result = FormValidator.isValidCertificatePrice(price);
            expect(Boolean(result)).to.be.false;
        });
        it("'string'", () => {
            const price = "string";
            const result = FormValidator.isValidCertificatePrice(price);
            expect(Boolean(result)).to.be.false;
        });
        it("empty", () => {
            let price;
            const result = FormValidator.isValidCertificatePrice(price);
            expect(Boolean(result)).to.be.false;
        });
    });
    describe('isValidCertificateDuration', () => {
        it("'12'", () => {
            const duration = "12";
            const result = FormValidator.isValidCertificateDuration(duration);
            expect(Boolean(result)).to.be.true;
        });
        it("'12.5'", () => {
            const duration = "12.5";
            const result = FormValidator.isValidCertificateDuration(duration);
            expect(Boolean(result)).to.be.false;
        });
        it("'0'", () => {
            const duration = "0";
            const result = FormValidator.isValidCertificateDuration(duration);
            expect(Boolean(result)).to.be.false;
        });
        it("'-1'", () => {
            const duration = "-1";
            const result = FormValidator.isValidCertificateDuration(duration);
            expect(Boolean(result)).to.be.false;
        });
        it("empty", () => {
            let duration;
            const result = FormValidator.isValidCertificateDuration(duration);
            expect(Boolean(result)).to.be.false;
        });
    });
});
