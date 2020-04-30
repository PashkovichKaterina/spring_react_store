import React from "react";
import '../../style/errorPage.css';
import notFoundImage from '../../image/notFound.png'
import unavailableServerImage from '../../image/unavailableServer.png'
import Localization from "../../localization/Localization";

const ErrorPage = (props) => {
    const type = props.type;
    const image = type === "unavailableServer" ? unavailableServerImage : notFoundImage;
    return (
        <main className="authentication">
            <div>
                <div className="half">
                    <p className="header">{Localization.getString(`${type}Header`)}</p>
                    <p className="text">{Localization.getString(`${type}Text`)}</p>
                </div>
                <div className="half right">
                    <img src={image} className="error-image" alt={Localization.getString(`${type}Picture`)}/>
                </div>
            </div>
        </main>
    )
};

export default ErrorPage