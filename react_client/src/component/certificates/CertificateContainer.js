import React from "react";
import "../../style/certificate.css";
import TagList from "../tag/TagList";
import Util from "../../service/Util";
import Localization from "../../localization/Localization";
import AuthorizationLogic from "../../service/AuthorizationLogic";
import RedirectLogic from "../../service/RedirectLogic";

const MAX_DESCRIPTION_LENGTH = 250;

class CertificateContainer extends React.PureComponent {
    formDescriptionString(description) {
        return description.length > MAX_DESCRIPTION_LENGTH
            ? description.substr(0, MAX_DESCRIPTION_LENGTH) + "..."
            : description;
    }

    handleCertificateTitleClick = () => {
        this.props.showCertificatePopup(this.props.certificate)
    };

    handleBuyButtonClick = () => {
        this.props.showBuyPopup(this.props.certificate);
    };

    handleEditButtonClick = () => {
        RedirectLogic.redirectToEditPage(this.props.certificate.id);
    };

    render() {
        const {category, deleteCertificate, certificate} = this.props;
        const {id, name, description, creationDate, price, tags} = certificate;
        const buyButton = (AuthorizationLogic.isUserLogin() && category !== "my") &&
            <button className="certificate-button" onClick={this.handleBuyButtonClick} value={id}>
                {Localization.getString("buy")}
            </button>;
        const adminButton = (AuthorizationLogic.isAdminLogin() && category !== "my") &&
            <div className="inline-block">
                <button className="certificate-button" onClick={this.handleEditButtonClick}>
                    {Localization.getString("edit")}
                </button>
                <button className="certificate-button" onClick={deleteCertificate}
                        value={id}>
                    {Localization.getString("delete")}
                </button>
            </div>;

        return (
            <div className="certificate">
                <div className="certificate-header">
                    <div className="certificate-title" onClick={this.handleCertificateTitleClick}>
                        {Util.capitalize(name)}
                    </div>
                    <div className="certificate-creation-date">
                        {Util.dateFormat(creationDate)}
                    </div>
                </div>
                <div className="certificate-content">
                    <TagList tags={tags}
                             addSearchTag={this.props.addSearchTag}/>
                    <div className="certificate-description">
                        {Localization.getString("description")}: {this.formDescriptionString(description)}
                    </div>
                </div>
                <div className="certificate-footer">
                    {adminButton}
                    <div className="right">
                        {buyButton}
                        <span className="certificate-price">{price} BYN</span>
                    </div>
                </div>
            </div>
        )
    }
}

export default CertificateContainer