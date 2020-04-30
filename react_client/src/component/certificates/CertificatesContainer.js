import React from 'react';
import "../../style/certificate.css"
import CertificatePopup from "../popup/CertificatePopup";
import PaginationPanelContainer from "../pagination/PaginationPanelContainer";
import WarningPopup from "../popup/WarningPopup";
import CertificateList from "./CertificateList";
import InformPopup from "../popup/InformPopup";
import {createBrowserHistory} from "history";
import Localization from "../../localization/Localization";
import SearchPanelContainer from "./SearchPanelContainer";
import PaginationLogic from "../../service/PaginationLogic";
import CertificateService from "../../service/CertificateService";
import AuthorizationLogic from "../../service/AuthorizationLogic";
import RedirectLogic from "../../service/RedirectLogic";
import SearchLogic from "../../service/SearchLogic";

class CertificatesContainer extends React.PureComponent {
    constructor(props) {
        super(props);
        const paginationParameter = PaginationLogic.getUrlParameter(window.location.href);
        const searchParameter = SearchLogic.getSearchParameterFromUrl(window.location.href);
        this.state = {
            certificates: [],
            currentPage: paginationParameter.page,
            perPage: paginationParameter.perPage,
            search: searchParameter.search,
            tags: searchParameter.tags,
            certificateCategory: searchParameter.certificateCategory,
            price: searchParameter.price,
            minPrice: searchParameter.minPrice,
            maxPrice: searchParameter.maxPrice
        };
    }

    componentDidMount() {
        this.setCertificate();
    }

    setCertificate() {
        const {search, tags, currentPage, perPage, price, minPrice, maxPrice, certificateCategory} = this.state;
        let linkHeader;
        const certificates = certificateCategory === "my"
            ? CertificateService.getCertificateByUser(search, tags, price, minPrice, maxPrice, currentPage, perPage)
            : CertificateService.getAllCertificate(search, tags, price, minPrice, maxPrice, currentPage, perPage);
        certificates
            .then(response => {
                linkHeader = response.headers.get("link");
                return response.json();
            })
            .then(json => {
                const pageInfo = PaginationLogic.getPageInfoFromLinkHeader(linkHeader);
                this.setState({
                    certificates: json,
                    firstPage: Number(pageInfo.first),
                    lastPage: Number(pageInfo.last)
                });
            });
        createBrowserHistory().push(RedirectLogic.formUrl(search, tags, price, minPrice, maxPrice, currentPage, perPage, certificateCategory));
    }

    changePage = (event) => {
        this.setState({
            currentPage: event.target.value
        }, () => this.setCertificate());

    };

    changePerPage = (event) => {
        const {perPage, currentPage} = this.state;
        const {value} = event.target;
        let newPage = ((currentPage - 1) * perPage + 1) / value;
        this.setState({
            currentPage: Math.ceil(newPage),
            perPage: value
        }, () => this.setCertificate());
    };

    changeCategory = (event) => {
        this.setState({
            currentPage: 1,
            certificateCategory: event.target.value
        }, () => this.setCertificate());
    };

    search = (searchString) => {
        const searchParameter = SearchLogic.getSearchParameterFromInput(searchString);
        this.setState({
            currentPage: 1,
            search: searchParameter.search,
            tags: searchParameter.tags,
            price: searchParameter.price,
            minPrice: searchParameter.minPrice,
            maxPrice: searchParameter.maxPrice,
        }, () => this.setCertificate());
    };

    addSearchTag = (event) => {
        this.setState({
            searchTag: event.target.innerText
        });
    };

    showCertificatePopup = (certificate) => {
        this.setState({
            isShowPopup: true,
            showCertificate: certificate
        });
    };

    closeCertificatePopup = () => {
        this.setState({
            isShowPopup: false
        });
    };

    showDeletePopup = (event) => {
        this.setState({
            isShowDeletePopup: true,
            deleteCertificateId: event.target.value
        });
    };

    showBuyPopup = (certificate) => {
        this.setState({
            isShowBuyPopup: true,
            buyCertificate: certificate
        });
    };

    closeBuyPopup = () => {
        this.setState({
            isShowBuyPopup: false
        });
    };

    closeSuccessPopup = () => {
        this.setState({
            isShowSuccessPopup: false
        });
    };

    closeErrorPopup = () => {
        this.setState({
            isShowErrorPopup: false
        });
    };

    closeDeletePopup = () => {
        this.setState({
            isShowDeletePopup: false
        });
    };

    deleteCertificate = () => {
        const {currentPage, firstPage, lastPage, certificates} = this.state;
        this.setState({
            isShowDeletePopup: false
        });
        CertificateService.delete(this.state.deleteCertificateId)
            .then(response => {
                this.setState({isShowErrorPopup: !response.ok});
                return !response.ok && response.json();
            })
            .then(json => {
                if (this.state.isShowErrorPopup) {
                    this.setState({errorCode: json.errorCode});
                } else {
                    this.setState({isShowSuccessPopup: true});
                    if (currentPage === lastPage && certificates.length <= 1 && firstPage < lastPage) {
                        this.setState({
                            currentPage: currentPage - 1
                        });
                    }
                    this.setCertificate();
                }
            });
    };

    buyCertificate = () => {
        CertificateService.buy(this.state.buyCertificate.id)
            .then(response => {
                this.setState({isShowErrorPopup: !response.ok, isShowBuyPopup: false});
                return !response.ok && response.json();
            })
            .then(json => {
                if (this.state.isShowErrorPopup) {
                    this.setState({errorCode: json.errorCode});
                } else {
                    this.setState({isShowSuccessPopup: true});
                    this.setCertificate();
                }
            });
    };

    redirectToAddWindow = () => {
        RedirectLogic.redirectToAddPage();
    };

    render() {
        const {
            certificates, firstPage, lastPage, currentPage, perPage, search, tags, certificateCategory, searchTag,
            isShowPopup, showCertificate, isShowDeletePopup, isShowBuyPopup, isShowSuccessPopup, isShowErrorPopup,
            price, minPrice, maxPrice, buyCertificate, errorCode
        } = this.state;
        const searchValue = SearchLogic.getSearchString(search, tags, price, minPrice, maxPrice);
        const certificatePopup = isShowPopup &&
            <CertificatePopup certificate={showCertificate}
                              close={this.closeCertificatePopup}/>;
        const deletePopup = isShowDeletePopup &&
            <WarningPopup text={Localization.getString("isDelete")}
                          continue={this.deleteCertificate}
                          cancel={this.closeDeletePopup}/>;
        const buyPopup = isShowBuyPopup &&
            <WarningPopup text={Localization.getString("isBuy")}
                          title={buyCertificate.name}
                          price={buyCertificate.price}
                          continue={this.buyCertificate}
                          cancel={this.closeBuyPopup}/>;
        const successPopup = isShowSuccessPopup &&
            <InformPopup type="success" text={Localization.getString("successOperation")}
                         close={this.closeSuccessPopup}/>;
        const errorPopup = isShowErrorPopup &&
            <InformPopup type="error" text={Localization.getString(errorCode)}
                         close={this.closeErrorPopup}/>;
        const addCertificateButton = AuthorizationLogic.isAdminLogin() &&
            <button className="add-certificate-button"
                    onClick={this.redirectToAddWindow}>
                {Localization.getString("add")}
            </button>;
        const certificateList = certificates.length > 0
            ? <CertificateList certificates={certificates}
                               addSearchTag={this.addSearchTag}
                               showCertificatePopup={this.showCertificatePopup}
                               deleteCertificate={this.showDeletePopup}
                               showBuyPopup={this.showBuyPopup}
                               category={certificateCategory}/>
            : <p className="empty-search-result">{Localization.getString("emptySearchResult")}</p>;
        return (
            <main>
                {certificatePopup}
                {deletePopup}
                {buyPopup}
                <div>
                    {successPopup}
                    {errorPopup}
                    <h1>
                        {Localization.getString("certificates")}
                        {addCertificateButton}
                    </h1>
                    <SearchPanelContainer searchTag={searchTag}
                                          search={this.search}
                                          value={searchValue}
                                          changeCategory={this.changeCategory}
                                          category={certificateCategory}/>
                    {certificateList}
                    <PaginationPanelContainer firstPage={firstPage}
                                              lastPage={lastPage}
                                              currentPage={currentPage}
                                              perPage={perPage}
                                              changePage={this.changePage}
                                              changePerPage={this.changePerPage}/>
                </div>
            </main>
        )
    }
}

export default CertificatesContainer;