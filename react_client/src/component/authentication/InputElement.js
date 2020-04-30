import React from 'react';
import '../../style/authentication.css'
import Localization from "../../localization/Localization";

const InputElement = (props) => {
    const {name, type, isValid, value, onChange, onKeyDown, explanation} = props;
    const errorMessage = !isValid &&
        <p className="error-message">
            {Localization.getString(name + "Error")}
        </p>;
    const explanationMessage = explanation && `(${explanation})`;
    return (
        <div className="form-element">
            <label htmlFor={name}>
                {Localization.getString(name)} <p className="explanation-message">{explanationMessage}</p>
            </label>
            <input
                type={type}
                name={name}
                className={isValid ? undefined : "error-field"}
                placeholder={Localization.getString(name + "Placeholder")}
                value={value}
                onChange={onChange}
                onKeyDown={onKeyDown}/>
            {errorMessage}
        </div>)
};

export default InputElement;