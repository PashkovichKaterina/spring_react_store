import LocalizedStrings from "react-localization";

class Localization {
    constructor() {
        this.strings = new LocalizedStrings({
            en: {
                login: "Login",
                loginPlaceholder: "Enter login",
                email: "Email",
                emailPlaceholder: "Enter login",
                password: "Password",
                passwordPlaceholder: "Enter password",
                loginOrEmail: "Login or email",
                loginOrEmailPlaceholder: "Enter login or email",
                confirmPassword: "Confirm password",
                confirmPasswordPlaceholder: "Enter confirm password",
                cancel: "Cancel",
                signup: "Signup",
                googleLogin: "Login from Google account",
                githubLogin: "Login from Github account",
                googleSignup: "Signup from Google account",
                githubSignup: "Signup from Github account",
                emailError: "Incorrect email",
                loginError: "Login field length must be from 1 to 30 characters without space",
                passwordError: "Password must be 8 characters including 1 numeric character and 1 alphabetical character",
                confirmPasswordError: "Passwords do not match",
                titleError: "Title field length must be from 1 to 30 characters without space",
                descriptionError: "Description field length must be from 1 to 30 characters",
                priceError: "Price must be positive double number",
                durationError: "Duration must be positive integer number",
                welcomePicture: "Welcome picture",
                authenticationException: "Invalid login or password",
                repeatLoginAndEmail: "User with this login and email is already exist",
                repeatLogin: "User with this login is already exist",
                repeatEmail: "User with this email is already exist",
                noSuchEntity: "Certificate is not exists",
                existEntity: "Certificate with this title is already exist",
                generalException: "Make sure the request is correct and try again",
                tagError: "Tag title field length must be from 1 to 15 characters without space",
                title: "Title",
                description: "Description",
                price: "Price",
                duration: "Duration",
                tag: "Tag",
                tags: "Tags",
                creationDate: "Creation date",
                modificationDate: "Modification date",
                delete: "Delete",
                edit: "Edit",
                continue: "Continue",
                buy: "Buy",
                add: "Add",
                searchCertificatePlaceholder: "Search certificate...",
                all: "All",
                myCertificate: "My certificates",
                older: "Older",
                newer: "Newer",
                logout: "Logout",
                certificates: "Certificates",
                certificate: "certificate",
                isDelete: "Click \"Continue\" to remove the certificate, or \"Cancel\" to return to the list of certificates.",
                isBuy: "Click \"Continue\" to buy the certificate, or \"Cancel\" to return to the list of certificates.",
                successOperation: "Operation completed successfully",
                notFoundHeader: "Page not found",
                notFoundText: "We can't find the page you are looking for. Please make sure you have typed the correct URL.",
                unavailableServerHeader: "Server is temporarily unavailable",
                unavailableServerText: "Unfortunately, the server is temporarily down. Please try again later.",
                unavailableServerPicture: "Unavailable server picture",
                userIcon: "User icon",
                tagExplanation: "Enter a tag title and press \"Enter\"",
                emptySearchResult: "No certificates were found for your request",
                confirmation: "Confirmation required",
                ru: "ru",
                en: "en"
            },
            ru: {
                login: "Логин",
                loginPlaceholder: "Введите логин",
                email: "Электронная почта",
                emailPlaceholder: "Введите электронную почту",
                password: "Пароль",
                passwordPlaceholder: "Введите пароль",
                loginOrEmail: "Логин или электронная почта",
                loginOrEmailPlaceholder: "Введите логин или электронную почту",
                confirmPassword: "Повторите пароль",
                confirmPasswordPlaceholder: "Введите повтор пароля",
                cancel: "Отмена",
                signup: "Регистрация",
                googleLogin: "Войти через Google аккаунт",
                githubLogin: "Войти через Github аккаунт",
                googleSignup: "Зарегистрироваться через Google аккаунт",
                githubSignup: "Зарегистрироваться через Github аккаунт",
                emailError: "Неккоректная электронная почта",
                loginError: "Длина логина должна быть от 1 до 30 символов без пробелов",
                passwordError: "Пароль должен содержать не менее 8 символов включая 1 цифру и 1 букву",
                confirmPasswordError: "Пароли не совпадают",
                titleError: "Длина заголовка должна быть от 1 до 30 символов без пробелов",
                descriptionError: "Длина описания должна быть от 1 до 30",
                priceError: "Стоимость должна быть положительным вещественным числом",
                durationError: "Продолжительность должна быть положительным целым числом",
                tagError: "Длина тега должна быть от 1 до 15 символов без пробелов",
                welcomePicture: "Приветственная картинка",
                authenticationException: "Неверный логин или пароль",
                repeatLoginAndEmail: "Пользователь с такими логином и электронной почтой уже зарегистрирован",
                repeatLogin: "Пользователь с таким логином почтой уже зарегистрирован",
                repeatEmail: "Пользователь с такой электронной почтой уже зарегистрирован",
                noSuchEntity: "Сертификат не существует",
                existEntity: "Сертификат с таким заголовком уже существует",
                generalException: "Убедитесь в правильности запроса и попробуйте еще раз",
                title: "Заголовок",
                description: "Описание",
                price: "Стоимость",
                duration: "Длительность",
                tag: "Тег",
                tags: "Теги",
                creationDate: "Дата создания",
                modificationDate: "Дата редактирования",
                delete: "Удалить",
                edit: "Редактировать",
                continue: "Продолжить",
                buy: "Купить",
                add: "Добавить",
                searchCertificatePlaceholder: "Поиск сертификата...",
                all: "Все",
                myCertificate: "Мои сертификаты",
                older: "Предыдущая",
                newer: "Следующая",
                logout: "Выйти",
                certificates: "Сертификаты",
                certificate: "сертификат",
                isDelete: "Нажмите кнопку \"Продолжить\", чтобы удалить сертификат, или \"Отмена\", чтобы вернуться к списку сертификатов.",
                isBuy: "Нажмите кнопку \"Продолжить\", чтобы купить сертификат, или \"Отмена\", чтобы вернуться к списку сертификатов.",
                successOperation: "Операция выполнена успешно",
                notFoundHeader: "Страница не найдена",
                notFoundText: "Мы не можем найти страницу, которую вы ищете. Пожалуйста, убедитесь, что вы ввели правильный URL.",
                unavailableServerHeader: "Сервер временно недоступен",
                unavailableServerText: "К сожалению, сервер временно не работает. Повторите запрос позже.",
                unavailableServerPicture: "Сервер временно не работает",
                userIcon: "Изображение пользователя",
                tagExplanation: "Введите заголовок тега и нажмите \"Enter\"",
                emptySearchResult: "Не найдено ни одного сертификата по Вашему запросу",
                confirmation: "Требуется подтверждение",
                ru: "рус",
                en: "анг"
            }
        });
    }

    setLang(lang) {
        localStorage.setItem("lang", lang);
        window.location.reload();
    }

    getLang() {
        return localStorage.getItem("lang") !== null ? localStorage.getItem("lang") : "default";
    }

    getString(name) {
        this.strings.setLanguage(this.getLang());
        return this.strings[name];
    }
}

export default new Localization();