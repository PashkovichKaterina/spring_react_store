import React from 'react';
import "../../style/certificate.css"
import InputElement from "../authentication/InputElement";
import WarningPopup from "../popup/WarningPopup";
import InformPopup from "../popup/InformPopup";
import Localization from "../../localization/Localization";
import Util from "../../service/Util";
import FormValidator from "../../service/FormValidator";
import CertificateService from "../../service/CertificateService";
import RedirectLogic from "../../service/RedirectLogic";
import TagInteractiveListContainer from "../tag/TagInteractiveListContainer";

const TITLE = "title";
const DESCRIPTION = "description";
const PRICE = "price";
const DURATION = "duration";
const MAX_TITLE_LENGTH = 30;
const MAX_DESCRIPTION_LENGTH = 1000;

class CertificateFormContainer extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            title: "",
            description: "",
            price: "",
            duration: "",
            type: this.getType(),
            tags: [],
            isValidTitle: true,
            isValidDescription: true,
            isValidPrice: true,
            isValidDuration: true
        };
        if (this.getType() === "edit") {
            this.setCertificate()
        }
    }

    getType() {
        let path = window.location.pathname.substr(1);
        if (path.includes("/")) {
            path = path.substr(0, path.indexOf("/"));
        }
        return path;
    }

    setCertificate() {
        let href = window.location.href;
        let id = href.substr(href.lastIndexOf("/") + 1);
        CertificateService.getCertificate(id)
            .then(response => {
                this.setState({isShowErrorPopup: !response.ok});
                return response.json();
            })
            .then(json => {
                if (!this.state.isShowErrorPopup) {
                    this.setState({
                        id: id,
                        title: json.name,
                        description: json.description,
                        price: json.price,
                        duration: json.duration,
                        tags: json.tags && json.tags.map(tag => tag.title)
                    })
                } else {
                    this.setState({errorCode: json.errorCode});
                }
            });
    }

    handleChange = (event) => {
        const {name, value} = event.target;
        this.setState({
            [name]: value
        });
        this.checkInputField(name, value);
    };

    checkInputField(name, value) {
        let field = `isValid${Util.capitalize(name)}`;
        let isValidField = false;
        switch (name) {
            case TITLE:
                isValidField = FormValidator.isValidCertificateName(value);
                break;
            case DESCRIPTION:
                isValidField = FormValidator.isValidCertificateDescription(value);
                break;
            case PRICE:
                isValidField = FormValidator.isValidCertificatePrice(value);
                break;
            case DURATION:
                isValidField = FormValidator.isValidCertificateDuration(value);
        }
        this.setState({[field]: isValidField});
    }


    handleDelete = (i) => {
        const {tags} = this.state;
        this.setState({
            tags: tags.filter(tag => tag !== i.target.title),
        });
    };

    handleAddition = (tag) => {
        if (!this.state.tags.includes(tag)) {
            this.setState(state => ({tags: [...state.tags, tag]}));
        }
    };

    handleKeyDown = (event) => {
        if (event.keyCode === 13) {
            event.preventDefault();
        }
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const {id, title, description, price, duration, tags, type} = this.state;
        const certificate = {
            name: title,
            description: description,
            price: price,
            duration: duration,
            tags: tags.map(tag => {
                return {
                    title: tag
                }
            })
        };
        const action = type === "edit"
            ? CertificateService.edit(id, certificate)
            : CertificateService.add(certificate);
        action
            .then(response => {
                this.setState({isShowErrorPopup: !response.ok});
                return response.json()
            })
            .then(json => {
                if (!this.state.isShowErrorPopup) {
                    RedirectLogic.redirectToCertificates();
                } else {
                    this.setState({errorCode: json.errorCode});
                }
            });
    };

    deleteCertificate = (event) => {
        event.preventDefault();
        this.setState({isShowWarningPopup: true})
    };

    closeErrorPopup = () => {
        this.setState({isShowErrorPopup: false});
    };

    closeWarningPopup = () => {
        this.setState({isShowWarningPopup: false});
    };

    continueDelete = () => {
        this.setState({isShowWarningPopup: false});
        CertificateService.delete(this.state.id);
        RedirectLogic.redirectToCertificates();
    };

    cancel = () => {
        RedirectLogic.redirectToCertificates();
    };

    render() {
        const {
            title, description, price, duration, tags, type, isValidTitle, isValidDescription, isValidPrice,
            isValidDuration, isShowErrorPopup, isShowWarningPopup, errorCode
        } = this.state;
        const errorPopup = (isShowErrorPopup && errorCode !== "noSuchEntity") &&
            <InformPopup type="error" text={Localization.getString(errorCode)} close={this.closeErrorPopup}/>;
        const warningPopup = isShowWarningPopup &&
            <WarningPopup continue={this.continueDelete}
                          cancel={this.closeWarningPopup}
                          text={Localization.getString("isDelete")}/>;
        const noCertificatePopup = (isShowErrorPopup && errorCode === "noSuchEntity") &&
            <WarningPopup cancel={this.cancel}
                          text={Localization.getString(errorCode)}/>;
        const submitButton = FormValidator.isValidCertificateForm(title, description, price, duration)
            ? <button className="edit-certificate-button">{Localization.getString(type)}</button>
            : <button disabled className="disabled-edit-certificate-button">{Localization.getString(type)}</button>;
        const editButton = type === "edit" &&
            <button className="edit-certificate-button" onClick={this.deleteCertificate}>
                {Localization.getString("delete")}
            </button>;
        return (
            <main className="authentication">
                <div>
                    {noCertificatePopup}
                    {errorPopup}
                    {warningPopup}
                    <h1>
                        {Localization.getString(type)} {Localization.getString("certificate")}
                    </h1>
                    <form className="certificate-form" onSubmit={this.handleSubmit} onKeyDown={this.handleKeyDown}>
                        <InputElement type="text"
                                      name="title"
                                      value={title}
                                      isValid={isValidTitle}
                                      onChange={this.handleChange}/>
                        <p className={isValidTitle ? "input-length" : "input-length-error"}>
                            {title.length}/{MAX_TITLE_LENGTH}
                        </p>
                        <InputElement type="text"
                                      name="description"
                                      value={description}
                                      isValid={isValidDescription}
                                      onChange={this.handleChange}/>
                        <p className={isValidDescription ? "input-length" : "input-length-error"}>
                            {description.length}/{MAX_DESCRIPTION_LENGTH}
                        </p>
                        <InputElement type="text"
                                      name="price"
                                      value={price}
                                      isValid={isValidPrice}
                                      onChange={this.handleChange}/>
                        <InputElement type="text"
                                      name="duration"
                                      value={duration}
                                      isValid={isValidDuration}
                                      onChange={this.handleChange}/>
                        <div className="interactive-tag">
                            <button className="add-tag">+</button>
                            <TagInteractiveListContainer tags={tags}
                                                         handleDelete={this.handleDelete}
                                                         handleAddition={this.handleAddition}
                                                         handleInputChange={this.handleInputChange}/>
                        </div>
                        <div className="edit-certificate-button-block">
                            {submitButton}
                            {editButton}
                        </div>
                    </form>
                </div>
            </main>
        )
    }
}

export default CertificateFormContainer;