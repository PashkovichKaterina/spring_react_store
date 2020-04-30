import React from 'react';
import "../../style/popup.css"
import {faTimesCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const InformPopup = props => {
    const popupClass = props.type + "-popup";
    const messageClass = props.type + "-popup-message";
    const buttonClass = props.type + "-close-button";
    return (
        <div className={popupClass}>
            <span className={messageClass}>{props.text}</span>
            <FontAwesomeIcon icon={faTimesCircle}
                             className={buttonClass}
                             onClick={props.close}/>
        </div>
    )
};

export default InformPopup;