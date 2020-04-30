import React from 'react';
import '../../style/authentication.css'
import Localization from "../../localization/Localization";
import RedirectLogic from "../../service/RedirectLogic";

class ButtonElementContainer extends React.PureComponent {
    handleCancelButtonClick = () => {
        RedirectLogic.redirectToCertificates();
    };

    render() {
        const {name, isSubmitEnable} = this.props;
        const submitButton = isSubmitEnable
            ? <button type="submit">{Localization.getString(name)}</button>
            : <button type="submit" disabled className="disabled-button">{Localization.getString(name)}</button>;
        return (
            <div className="form-element">
                {submitButton}
                <button className="right" onClick={this.handleCancelButtonClick}>
                    {Localization.getString("cancel")}
                </button>
            </div>
        )
    }
}

export default ButtonElementContainer;