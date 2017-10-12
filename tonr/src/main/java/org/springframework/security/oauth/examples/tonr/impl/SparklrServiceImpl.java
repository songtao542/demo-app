package org.springframework.security.oauth.examples.tonr.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.oauth.examples.Photo;
import org.springframework.security.oauth.examples.tonr.SparklrException;
import org.springframework.security.oauth.examples.tonr.SparklrService;
import org.springframework.security.oauth.examples.tonr.mvc.SparklrController;
import org.springframework.web.client.RestOperations;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Ryan Heaton
 */
public class SparklrServiceImpl implements SparklrService {
    private static final Log logger = LogFactory.getLog(SparklrController.class);

    private String sparklrPhotoListURL;
    private String sparklrPhotoListURL1;
    private String sparklrTrustedMessageURL;
    private String sparklrPhotoURLPattern;
    private RestOperations sparklrRestTemplate;
    private RestOperations trustedClientRestTemplate;

    public List<String> getSparklrPhotoIds() throws SparklrException {
        try {
            InputStream photosXML = new ByteArrayInputStream(sparklrRestTemplate.getForObject(
                    URI.create(sparklrPhotoListURL), byte[].class));

            final List<String> photoIds = new ArrayList<String>();
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setValidating(false);
            parserFactory.setXIncludeAware(false);
            parserFactory.setNamespaceAware(false);
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(photosXML, new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                        throws SAXException {
                    if ("photo".equals(qName)) {
                        photoIds.add(attributes.getValue("id"));
                    }
                }
            });
            return photoIds;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } catch (SAXException e) {
            throw new IllegalStateException(e);
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<String> getSparklrPhotoIds1() throws SparklrException {
        try {

            InputStream photosStream = new ByteArrayInputStream(sparklrRestTemplate.getForObject(
                    URI.create(sparklrPhotoListURL1), byte[].class));


//			 JsonFactory factory = new  JsonFactory();
//			 JsonParser parser = factory.createParser(photosXML);
            ObjectMapper mapper = new ObjectMapper();

            ObjectNode node = mapper.readValue(photosStream, ObjectNode.class);
//            TypeFactory factory = mapper.getTypeFactory();
//            JavaType type = factory.constructParametricType(ArrayList.class, Photo.class);
            logger.debug("node======" + node.getNodeType());
            logger.debug("node======" + node.getClass());
            logger.debug("node======" + node);
            logger.debug("node======" + node.get("photos").toString());
            logger.debug("node======" + node.findValuesAsText("id"));
            List<String> photoIds =  node.findValuesAsText("id");

            return photoIds;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public InputStream loadSparklrPhoto(String id) throws SparklrException {
        return new ByteArrayInputStream(sparklrRestTemplate.getForObject(
                URI.create(String.format(sparklrPhotoURLPattern, id)), byte[].class));
    }

    public String getTrustedMessage() {
        return this.trustedClientRestTemplate.getForObject(URI.create(sparklrTrustedMessageURL), String.class);
    }

    public void setSparklrPhotoURLPattern(String sparklrPhotoURLPattern) {
        this.sparklrPhotoURLPattern = sparklrPhotoURLPattern;
    }

    public void setSparklrPhotoListURL(String sparklrPhotoListURL) {
        this.sparklrPhotoListURL = sparklrPhotoListURL;
    }

    public void setSparklrPhotoListURL1(String sparklrPhotoListURL) {
        this.sparklrPhotoListURL1 = sparklrPhotoListURL;
    }

    public void setSparklrTrustedMessageURL(String sparklrTrustedMessageURL) {
        this.sparklrTrustedMessageURL = sparklrTrustedMessageURL;
    }

    public void setSparklrRestTemplate(RestOperations sparklrRestTemplate) {
        this.sparklrRestTemplate = sparklrRestTemplate;
    }

    public void setTrustedClientRestTemplate(RestOperations trustedClientRestTemplate) {
        this.trustedClientRestTemplate = trustedClientRestTemplate;
    }

}
