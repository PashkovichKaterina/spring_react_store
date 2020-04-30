import React from 'react';
import ReactDOM from 'react-dom';
import {JSDOM} from 'jsdom';
import Tag from "../../component/tag/Tag";
import {expect} from 'chai';
import {describe, it} from "mocha";

describe('Tag', () => {
    it('render', () => {
        const dom = new JSDOM('<!DOCTYPE html><html><head></head><body></body></html>');
        global.window = dom.window;
        global.document = dom.window.document;
        const div = document.createElement('div');
        const tag = {id: 1, title: "tag1"};
        ReactDOM.render(<Tag tag={tag}/>, div);
        expect(ReactDOM.unmountComponentAtNode(div)).to.be.true;
    });
});