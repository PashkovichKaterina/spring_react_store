class SearchLogic {
    getSearchString(search, tags, price, minPrice, maxPrice) {
        let value = search ? search : "";
        if (tags) {
            for (let i = 0; i < tags.length; i++) {
                value += "#(" + tags[i] + ")";
            }
        }
        if (price) {
            for (let i = 0; i < price.length; i++) {
                value += "$(=" + price[i] + ")";
            }
        }
        value += minPrice ? "$(>=" + minPrice + ")" : "";
        value += maxPrice ? "$(<=" + maxPrice + ")" : "";
        return value;
    }

    getSearchParameterFromInput(searchString) {
        return {
            search: this.getSearchFromInput(searchString),
            tags: this.getTagParameterFromInput(searchString),
            price: this.getPriceParameterFromInput(searchString),
            minPrice: this.getMinPriceParameterFromInput(searchString),
            maxPrice: this.getMaxPriceParameterFromInput(searchString)
        };
    }

    getSearchParameterFromUrl(url) {
        return {
            search: this.getSearchFromUrl(url),
            tags: this.getTagParameterFromUrl(url),
            price: this.getPriceParameterFromUrl(url),
            minPrice: this.getMinPriceParameterFromUrl(url),
            maxPrice: this.getMaxPriceParameterFromUrl(url),
            certificateCategory: this.getCertificateCategory(url)
        };
    }

    getSearchFromInput(searchString) {
        let inR = searchString.indexOf("#") > -1 ? searchString.indexOf("#") : searchString.length;
        let inD = searchString.indexOf("$") > -1 ? searchString.indexOf("$") : searchString.length;
        return searchString.substr(0, inR > inD ? inD : inR);
    }

    getSearchFromUrl(url) {
        let search = url.match(/search=\w+/g);
        if (search) {
            search = search[0].substr(search[0].indexOf("=") + 1)
        }
        return search;
    }

    getTagParameterFromInput(searchString) {
        let tags = searchString.match(/#\(\w+\)/g);
        if (tags) {
            tags = tags.map(tag => tag.substr(2, tag.length - 3));
        }
        return tags;
    }

    getTagParameterFromUrl(url) {
        let tags = url.match(/tagTitle=\w+/g);
        if (tags) {
            tags = tags.map(tag => tag.substr(tag.indexOf("=") + 1))
        }
        return tags;
    }

    getPriceParameterFromInput(searchString) {
        let price = searchString.match(/\$\(=?[1-9]*\d*[.]?[1-9]\d*\)/g);
        if (price) {
            price = price.map(p => p.match(/[1-9]*\d*[.]?[1-9]\d*/g)[0]);
        }
        return price;
    }

    getPriceParameterFromUrl(url) {
        let price = url.match(/price=[1-9]*\d*[.]?[1-9]\d*/g);
        if (price) {
            price = price.map(p => p.substr(p.indexOf("=") + 1))
        }
        return price;
    }

    getMinPriceParameterFromInput(searchString) {
        let minPrice = searchString.match(/\$\(>=?[1-9]*\d*[.]?[1-9]\d*\)/g);
        if (minPrice) {
            minPrice = minPrice[0].match(/[1-9]*\d*[.]?[1-9]\d*/g)[0];
        }
        return minPrice;
    }

    getMinPriceParameterFromUrl(url) {
        let minPrice = url.match(/minPrice=[0-9.]+/g);
        minPrice = minPrice ? minPrice[0].substr(minPrice[0].indexOf("=") + 1) : "";
        return minPrice;
    }

    getMaxPriceParameterFromInput(searchString) {
        let maxPrice = searchString.match(/\$\(<=?[1-9]*\d*[.]?[1-9]\d*\)/g);
        if (maxPrice) {
            maxPrice = maxPrice[0].match(/[1-9]*\d*[.]?[1-9]\d*/g)[0];
        }
        return maxPrice;
    }

    getMaxPriceParameterFromUrl(url) {
        let maxPrice = url.match(/maxPrice=[0-9.]+/g);
        maxPrice = maxPrice ? maxPrice[0].substr(maxPrice[0].indexOf("=") + 1) : "";
        return maxPrice;
    }

    getCertificateCategory(url) {
        let category = url.match(/category=\w+/g);
        category = category ? category[0].substr(category[0].indexOf("=") + 1) : "all";
        return category;
    }
}

export default new SearchLogic();