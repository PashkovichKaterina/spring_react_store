import React from 'react';
import "../../style/popup.css"
import Localization from "../../localization/Localization";
import Util from "../../service/Util";

const WarningPopup = props => {
    const {price, title, cancel, text} = props;
    const certificateMessage = title &&
        <p className="warning-popup-message">
            <span className="warning-popup-message-title">
                {Util.capitalize(Localization.getString("certificate"))}:
            </span>
            <span>{title}</span>
        </p>;
    const priceMessage = price &&
        <p className="warning-popup-message">
            <span className="warning-popup-message-title">{Localization.getString("price")}:</span>
            <span>{price} BYN</span>
        </p>;
    const continueButton = props.continue &&
        <button className="warning-button" onClick={props.continue}>
            {Localization.getString("continue")}
        </button>;
    return (
        <div className="warning-popup">
            <div>
                <p className="confirmation">{Localization.getString("confirmation")}</p>
                <p className="warning-text">{text}</p>
                {certificateMessage}
                {priceMessage}
                <div className="warning-button-block">
                    <button className="warning-button"
                            onClick={cancel}>{Localization.getString("cancel")}</button>
                    {continueButton}
                </div>
            </div>
        </div>
    )
};

export default WarningPopup;