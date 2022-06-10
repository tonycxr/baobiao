package com.sungcor.baobiao.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.dom4j.xpath.DefaultXPath;

import java.io.File;
import java.util.List;

public class Dom4jHelper {

    private Document doc;

    private String filePath;

    public Dom4jHelper(File file) throws DocumentException {
        filePath = file.getPath().substring(file.getPath().indexOf("bpm"),file.getPath().length());
        SAXReader reader = new SAXReader();
        doc = reader.read(file);
    }

    public Dom4jHelper(String infile) throws DocumentException {
        SAXReader reader = new SAXReader();
        doc = reader.read(new File(infile));
    }

    public Dom4jHelper(java.io.InputStream in) throws DocumentException {
        SAXReader reader = new SAXReader();
        doc = reader.read(in);
    }
    public Element getRootElement(){
        return doc.getRootElement();
    }

    public Element selectSingleNode(String xql){
        XPath xPath = new DefaultXPath(xql);
        return (Element)(xPath.selectSingleNode(doc));
    }

    public Element selectSingleNode(Element el,String xql){
        XPath xPath = new DefaultXPath(xql);
        return (Element)(xPath.selectSingleNode(el));
    }

    public String selectText(String xql){
        XPath xPath = new DefaultXPath(xql);
        return xPath.selectSingleNode(doc).getText();
    }

    public String selectText(Element el,String xql){
        XPath xPath = new DefaultXPath(xql);
        return xPath.selectSingleNode(el).getText();
    }

    public List selectNodeList(String xql){
        XPath xPath = new DefaultXPath(xql);
        return xPath.selectNodes(doc);
    }

    public List selectNodeList(Element el,String xql){
        XPath xPath = new DefaultXPath(xql);
        return xPath.selectNodes(el);
    }

    public String getFilePath() {
        return filePath;
    }

}
