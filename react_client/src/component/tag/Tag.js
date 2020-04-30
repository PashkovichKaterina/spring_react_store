import React from "react";
import "../../style/certificate.css";
import Util from "../../service/Util";

const Tag = props => {
    const {tag, addSearchTag} = props;
    return (
        <li className="tag-block"
            value={tag.title}
            onClick={addSearchTag}>
            {Util.capitalize(tag.title)}
        </li>
    )
};

export default Tag