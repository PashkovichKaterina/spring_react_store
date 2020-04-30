import React from "react";
import '../../style/certificate.css';
import CertificateContainer from './CertificateContainer.js';

const CertificateList = (props) => {
    const {certificates, addSearchTag, showCertificatePopup, deleteCertificate, showBuyPopup, category} = props;
    const certificateList = certificates.map(certificate =>
        <CertificateContainer key={certificate.id}
                              certificate={certificate}
                              addSearchTag={addSearchTag}
                              showCertificatePopup={showCertificatePopup}
                              deleteCertificate={deleteCertificate}
                              showBuyPopup={showBuyPopup}
                              category={category}/>
    );
    return (
        <div>
            {certificateList}
        </div>
    )
};

export default CertificateList