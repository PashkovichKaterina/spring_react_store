import Localization from "../localization/Localization";

class Util {
    format() {
        let s = arguments[0];
        for (let i = 0; i < arguments.length - 1; i += 1) {
            let reg = new RegExp('\\{' + i + '\\}', 'gm');
            s = s.replace(reg, arguments[i + 1]);
        }
        return s;
    }

    capitalize(s) {
        return s.charAt(0).toUpperCase() + s.slice(1);
    }

    dateFormat(date) {
        const dateObj = new Date(date);
        const month = dateObj.toLocaleString(Localization.getLang(), {month: 'long'});
        const day = dateObj.getDate();
        const year = dateObj.getFullYear();
        return this.capitalize(month) + " " + day + ", " + year;
    }
}

export default new Util();