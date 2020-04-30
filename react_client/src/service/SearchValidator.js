class SearchValidator {
    isValidSearchString(searchString) {
        return this.isValidBracket(searchString) && this.isValidSymbol(searchString) && this.isValidPriceValue(searchString)
            && this.isValidPriceParameter(searchString) && this.isValidMaxMinPrice(searchString);
    }

    isValidBracket(searchString) {
        const openBracketCount = searchString.match(/\(/g) ? searchString.match(/\(/g).length : 0;
        const closeBracketCount = searchString.match(/\)/g) ? searchString.match(/\)/g).length : 0;
        return openBracketCount === closeBracketCount;
    }

    isValidSymbol(searchString) {
        const openBracketCount = searchString.match(/\(/g) ? searchString.match(/\(/g).length : 0;
        const bracketWithSymbolCount = searchString.match(/(#|\$)/g) ? searchString.match(/(#|\$)/g).length : 0;
        return openBracketCount === bracketWithSymbolCount;
    }

    isValidPriceValue(searchString) {
        const priceCount = searchString.match(/\$\(<?>?=?[\w.-]*\)/g) ? searchString.match(/\$\(<?>?=?[\w.-]*\)/g).length : 0;
        const correctPriceCount = searchString.match(/\$\(<?>?=?[1-9]*\d*[.]?[1-9]\d*\)/g) ? searchString.match(/\$\(<?>?=?[1-9]*\d*[.]?[1-9]\d*\)/g).length : 0;
        return priceCount === correctPriceCount;
    }

    isValidPriceParameter(searchString) {
        const priceCount = searchString.match(/\$\(=?\w*\)/g) ? searchString.match(/\$\(=?\w*\)/g).length : 0;
        const minPriceCount = searchString.match(/\$\(<=?\w*\)/g) ? searchString.match(/\$\(<=?\w*\)/g).length : 0;
        const maxPriceCount = searchString.match(/\$\(>=?\w*\)/g) ? searchString.match(/\$\(>=?\w*\)/g).length : 0;
        return (priceCount === 0 && (maxPriceCount <= 1 && minPriceCount <= 1)) || (maxPriceCount === 0 && minPriceCount === 0);
    }

    isValidMaxMinPrice(searchString) {
        let minPrice = searchString.match(/\$\(>=?\S*\)/g);
        if (minPrice) {
            minPrice = minPrice[0].match(/[1-9]*\d*\.?[1-9]\d*/) && minPrice[0].match(/[1-9]*\d*\.?[1-9]\d*/)[0];
        }
        let maxPrice = searchString.match(/\$\(<=?\S*\)/g);
        if (maxPrice) {
            maxPrice = maxPrice[0].match(/[1-9]*\d*\.?[1-9]\d*/) && maxPrice[0].match(/[1-9]*\d*\.?[1-9]\d*/)[0];
        }
        console.log(minPrice, maxPrice);
        return !minPrice || !maxPrice || Number(minPrice) < Number(maxPrice);
    }
}

export default new SearchValidator();