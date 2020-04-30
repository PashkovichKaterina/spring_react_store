import React from 'react';
import "../../style/common.css";
import NavButton from "./NavButton";

const PAGE_SIDE_LENGTH = 4;

class PageListContainer extends React.PureComponent {
    getPageBorder() {
        const {currentPage, lastPage, firstPage} = this.props;
        let first = 1;
        let last = 1;
        if (lastPage > firstPage) {
            let firstRange = PAGE_SIDE_LENGTH - (currentPage - firstPage);
            let lastRange = PAGE_SIDE_LENGTH - (lastPage - currentPage);
            firstRange = firstRange > 0 ? firstRange : 0;
            lastRange = lastRange > 0 ? lastRange : 0;
            first = currentPage - PAGE_SIDE_LENGTH + firstRange - lastRange;
            last = currentPage + PAGE_SIDE_LENGTH + firstRange - lastRange;
            first = first < firstPage ? firstPage : first;
            last = last > lastPage ? lastPage : last;
        }
        return {first: first, last: last};
    }

    getPageList(first, last) {
        const {currentPage} = this.props;
        let pageList = [];
        for (let i = first; i <= last; i++) {
            pageList.push(
                i === currentPage
                    ? <li key={i} className="active">{i}</li>
                    : <li key={i} value={i} onClick={this.props.changePage}>{i}</li>
            );
        }
        return pageList;
    }

    render() {
        const {lastPage, currentPage, firstPage, changePage} = this.props;
        const pageBorder = this.getPageBorder();
        const isDisplayStart = pageBorder.first > firstPage;
        const isDisplayFinish = pageBorder.last < lastPage;
        const isDisplayNext = currentPage < pageBorder.last;
        const isDisplayPrev = currentPage > pageBorder.first;
        const pageList = this.getPageList(pageBorder.first, pageBorder.last);
        const nextButton = isDisplayFinish &&
            <li key={lastPage} className="right" value={lastPage}
                onClick={changePage}>»</li>;
        const prevButton = isDisplayStart &&
            <li key={1} className="left" value={1} onClick={changePage}>«</li>;

        return (
            <div className="pagination">
                <div className="page-block">
                    <ul className="page-list">
                        {prevButton}
                        {pageList}
                        {nextButton}
                    </ul>
                </div>
                <ul>
                    <NavButton type="older"
                               value={(currentPage - 1)}
                               onClick={changePage}
                               isDisplay={isDisplayPrev}/>
                    <NavButton type="newer"
                               value={(Number(currentPage) + 1)}
                               onClick={changePage}
                               isDisplay={isDisplayNext}/>
                </ul>
            </div>
        )
    }
}

export default PageListContainer;