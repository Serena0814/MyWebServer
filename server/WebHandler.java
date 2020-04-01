package server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import servlet.Entity;
import servlet.Mapping;

import java.util.ArrayList;
import java.util.List;

public class WebHandler extends DefaultHandler {
    private List<servlet.Entity> entities;
    private List<servlet.Mapping> mappings;
    private servlet.Entity entity;
    private servlet.Mapping mapping;
    private String tag;
    private boolean isServlet;

    @Override
    public void startDocument() throws SAXException {
        entities = new ArrayList<>();
        mappings = new ArrayList<>();
    }

    /**
     * 开始解析元素
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName != null) {
            tag = qName;
            if (tag.equals("servlet")) {
                entity = new servlet.Entity();
                isServlet = true;
            } else if (tag.equals("servlet-mapping")) {
                mapping = new servlet.Mapping();
                isServlet = false;
            }
        }
    }

    /**
     * 解析内容
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String character = new String(ch, start, length).trim();
        if (tag != null) {
            if (isServlet) {
                if (tag.equals("servlet-name")) {
                    entity.setName(character);
                } else if (tag.equals("servlet-class")) {
                    entity.setClz(character);
                }
            } else {
                if (tag.equals("servlet-name")) {
                    mapping.setName(character);
                } else if (tag.equals("url-pattern")) {
                    mapping.addPattern(character);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName != null) {
            if (qName.equals("servlet")) {
                entities.add(entity);
            } else if (qName.equals("servlet-mapping")) {
                mappings.add(mapping);
            }
        }
        tag = null;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }
}
