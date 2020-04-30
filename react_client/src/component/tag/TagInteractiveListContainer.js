import React from "react";
import "../../style/tag.css"
import InputElement from "../authentication/InputElement";
import FormValidator from "../../service/FormValidator";
import Localization from "../../localization/Localization";

const MAX_TAG_TITLE_LENGTH = 15;

class TagInteractiveListContainer extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            tag: "",
            isValidTagTitle: true
        }
    }

    handleChange = (event) => {
        this.setState({
            tag: event.target.value,
            isValidTagTitle: FormValidator.isValidTagTitle(event.target.value)
        });
    };

    onKeyDown = (event) => {
        const {tag} = this.state;
        if (event.keyCode === 13 && FormValidator.isValidTagTitle(tag)) {
            event.preventDefault();
            this.props.handleAddition(tag);
            this.setState({tag: ''});
        }
    };

    render() {
        const {tag, isValidTagTitle} = this.state;
        const {tags, handleDelete} = this.props;
        const tagList = tags &&
            tags.map(tag =>
                <p className="tag-element" key={tag}>
                    {tag}
                    <span className="delete-tag" title={tag} onClick={handleDelete}>x</span>
                </p>);
        return (
            <div className="add-certificate-tags-block">
                <InputElement type="text"
                              name="tag"
                              value={tag}
                              isValid={isValidTagTitle}
                              onChange={this.handleChange}
                              onKeyDown={this.onKeyDown}
                              explanation={Localization.getString("tagExplanation")}/>
                <p className={isValidTagTitle ? "input-length" : "input-length-error"}>
                    {tag.length}/{MAX_TAG_TITLE_LENGTH}
                </p>
                {tagList}
            </div>
        );
    }
}


export default TagInteractiveListContainer;