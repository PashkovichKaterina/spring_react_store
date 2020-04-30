const perPageValues = [5, 25, 50, 100];

class PaginationLogic {
    getUrlParameter(url) {
        return {
            page: this.getPage(url),
            perPage: this.getPerPage(url)
        }
    }

    getPageInfoFromLinkHeader(linkHeader) {
        if (linkHeader != null) {
            let arr1 = linkHeader.split(",");
            let arr2 = {};
            for (let i = 0; i < arr1.length; i++) {
                const value = arr1[i].split(";")[0].match(/page=\d+/)[0].match(/\d+/)[0];
                let key = arr1[i].split(";")[1].match(/"\w+"/)[0];
                key = key.substr(1, key.length - 2)
                arr2[key] = value;
            }
            return arr2;
        }
    }

    isValidPageValue(page) {
        return !isNaN(page) && page > 0;
    }

    getPage(url) {
        let page = url.match(/page=[\w-]+/g);
        if (page) {
            page = page[0].substr(page[0].indexOf("=") + 1)
        }
        return !this.isValidPageValue(page) ? 1 : Number(page);
    }

    getPerPage(url) {
        let perPage = url.match(/per_page=\w+/g);
        if (perPage) {
            perPage = perPage[0].substr(perPage[0].indexOf("=") + 1)
        }
        perPage = !this.isValidPageValue(perPage) ? 5 : Number(perPage);
        if (!perPageValues.includes(perPage)) {
            let i = perPageValues.length - 1;
            while (perPage < perPageValues[i] && i > 0) {
                i--;
            }
            perPage = perPageValues[i];
        }
        return perPage;
    }
}

export default new PaginationLogic();