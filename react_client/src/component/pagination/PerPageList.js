import React from 'react';
import "../../style/common.css";

const PerPageList = (props) => {
    const {perPage, perPageValues, changePerPage} = props;
    const perPageValueList = perPageValues.map(perPageValue => {
        return <option key={perPageValue}>{perPageValue}</option>;
    });
    return (
        <select className="per-page" onChange={changePerPage} value={perPage}>
            {perPageValueList}
        </select>
    )
};

export default PerPageList;