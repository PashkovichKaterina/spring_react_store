import React from 'react';
import "../../style/popup.css"
import {faTimesCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import Util from "../../service/Util";
import Localization from "../../localization/Localization";

const CertificatePopup = props => {
    const {close, certificate} = props;
    return (
        <div className="certificate-popup">
            <div>
                <FontAwesomeIcon icon={faTimesCircle}
                                 className="close-certificate-button"
                                 onClick={close}/>
                <h1>{Util.capitalize(certificate.name)}</h1>
                <table>
                    <tr>
                        <td className="name">{Localization.getString("description")}</td>
                        <td>{certificate.description}</td>
                    </tr>
                    <tr className="even">
                        <td className="name">{Localization.getString("price")}</td>
                        <td>{certificate.price}BYN</td>
                    </tr>
                    <tr>
                        <td className="name">{Localization.getString("duration")}</td>
                        <td>{certificate.duration}</td>
                    </tr>
                    <tr className="even">
                        <td className="name">{Localization.getString("creationDate")}</td>
                        <td>{Util.dateFormat(certificate.creationDate)}</td>
                    </tr>
                    <tr>
                        <td className="name">{Localization.getString("modificationDate")}</td>
                        <td>{Util.dateFormat(certificate.modificationDate)}</td>
                    </tr>
                    <tr className="even">
                        <td className="name">{Localization.getString("tags")}</td>
                        <td>{certificate.tags.map(tag => tag.title + " ")}</td>
                    </tr>
                </table>
            </div>
        </div>
    )
};

export default CertificatePopup;