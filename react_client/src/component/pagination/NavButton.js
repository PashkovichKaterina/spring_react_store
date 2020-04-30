import React from 'react';
import "../../style/common.css"
import Localization from "../../localization/Localization";

const NavButton = props => {
    const {isDisplay, type, value, onClick} = props;
    const button = isDisplay &&
        <li className={type + " page-nav"}
            value={value}
            onClick={onClick}>
            {Localization.getString(type)}
        </li>;
    return (
        <span>
            {button}
        </span>
    )
};

export default NavButton;