import React from 'react';
import "../../style/pagination.css"
import PerPageList from "./PerPageList";
import PageListContainer from "./PageListContainer";

const perPageValues = [5, 25, 50, 100];

const PaginationPanelContainer = (props) => {
    const {perPage, firstPage, lastPage, currentPage, changePage, changePerPage} = props;
    const perPageList = (firstPage < lastPage || perPage > perPageValues[0]) &&
        <PerPageList perPage={perPage}
                     perPageValues={perPageValues}
                     changePerPage={changePerPage}/>;
    const pageList = (firstPage < lastPage) &&
        <PageListContainer firstPage={firstPage}
                           lastPage={lastPage}
                           currentPage={currentPage}
                           changePage={changePage}/>;
    return (
        <div>
            {perPageList}
            {pageList}
        </div>
    )
};

export default PaginationPanelContainer;
