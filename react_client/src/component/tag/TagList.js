import React from "react";
import Tag from './Tag.js';

const TagList = (props) => {
    const {tags, addSearchTag} = props;
    const tagList = tags.map(tag =>
        <Tag key={tag.id}
             tag={tag}
             addSearchTag={addSearchTag}/>
    );
    return (
        <ul>
            {tagList}
        </ul>
    )
};

export default TagList