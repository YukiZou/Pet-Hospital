package com.ecnu.service;

import org.junit.Test;
import com.ecnu.entity.Text;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class TextServiceTest extends BaseServiceTest {

    @Resource
    private TextService textService;

    @Test
    public void addText() {
        Text text = new Text();
        text.setCaseId(1);
        text.setStage(2);
        text.setInfo("testInfo");
        assertTrue(textService.addText(text));
        //前端限制了传入的caseId和stage不会是数据库里已经有的，所以下行注释
        //assertFalse(textService.addText(text));
        assertTrue(textService.deleteText(text));
    }

    @Test
    public void deleteText() {
        Text text = new Text();
        text.setId(2);
        text.setCaseId(1);
        text.setStage(2);
        text.setInfo("testInfo");
        assertTrue(textService.addText(text));
        assertTrue(textService.deleteText(text));
    }

    @Test
    public void findTextById() {
        //该id存在的情况
        Text t = textService.findTextById(1);
        assertTrue(t.getId() > 0);

        //该id不存在的情况
        Text tt = textService.findTextById(2);
        assertTrue(tt == null);
    }

    @Test
    public void updateText() {
        Text addText = new Text();
        addText.setCaseId(1);
        addText.setStage(3);
        addText.setInfo("testInfo");
        assertTrue(textService.addText(addText));
        int id = addText.getId();
        Text updateText = new Text();
        updateText.setId(-1);
        assertFalse(textService.updateText(updateText));
        updateText.setId(id);
        assertFalse(textService.updateText(updateText));
        updateText.setInfo("textUpdate");

        assertTrue(textService.updateText(updateText));
        assertTrue(textService.deleteText(updateText));

        //没有该Text时不能进行update
        assertFalse(textService.updateText(updateText));

    }

    @Test
    public void findTextByCIdS() {
        Text text = new Text();
        text.setCaseId(1);
        text.setStage(1);
        assertTrue(textService.findTextByCIdS(text).getId() > 0);
    }
}