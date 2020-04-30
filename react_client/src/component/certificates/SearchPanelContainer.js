import React from 'react';
import "../../style/certificate.css"
import {faSearch} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import Localization from "../../localization/Localization";
import AuthorizationLogic from "../../service/AuthorizationLogic";
import SearchValidator from "../../service/SearchValidator";

class SearchPanelContainer extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            search: props.value,
            isValidSearch: true
        };
    }

    componentDidUpdate(previousProps) {
        const {search, isSubmit} = this.state;
        const {searchTag, value} = this.props;
        if (isSubmit) {
            this.setState({isSubmit: false, search: value});
        }
        if (previousProps.searchTag !== searchTag && searchTag && !search.includes("#(" + searchTag + ")")) {
            this.setState({search: search + "#(" + searchTag + ")"});
        }
    }

    handleChange = (event) => {
        this.setState({
            search: event.target.value
        });
        this.setState({isValidSearch: SearchValidator.isValidSearchString(event.target.value)});
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const {search} = this.state;
        if (SearchValidator.isValidSearchString(search)) {
            this.props.search(search);
            this.setState({isSubmit: true});
        }
    };

    render() {
        const {search, isValidSearch} = this.state;
        const {category, changeCategory} = this.props;
        const selectPanel = AuthorizationLogic.isUserLogin() &&
            <select onChange={changeCategory} value={category}>
                <option value="all" key="all">{Localization.getString("all")}</option>
                <option value="my" key="my">{Localization.getString("myCertificate")}</option>
            </select>;
        return (
            <form className="search" onSubmit={this.handleSubmit}>
                {selectPanel}
                <input type="text"
                       className={isValidSearch ? "search" : "search-error"}
                       placeholder={Localization.getString("searchCertificatePlaceholder")}
                       onChange={this.handleChange}
                       value={search}/>
                <button className={isValidSearch ? "search" : "search-error"}>
                    <FontAwesomeIcon icon={faSearch}/>
                </button>
            </form>
        )
    }
}

export default SearchPanelContainer;