/*
 * JsonStream.java
 * 
 * 
 * @author ada
 * @version 1.0  2008Äê11ÔÂ25ÈÕ
 */
package com.example.world;

import java.io.Writer;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.AbstractJsonWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 *
 */
public class JsonStream {
	
	private static final Logger log = LogManager.getLogManager().getLogger(JsonStream.class.getName());

	private XStream stream;
	
	public JsonStream() {
		stream = new XStream(new JsonHierarchicalStreamDriver());
	}
	
	/**
	 * 
	 * 
	 * @param root
	 */
	public JsonStream(boolean root) {
		this(root ? AbstractJsonWriter.STRICT_MODE : AbstractJsonWriter.DROP_ROOT_MODE);
	}
	
	/**
	 * 
	 * 
	 * @param mode  see {@link AbstractJsonWriter} for detail. 
	 * <pre><blockquote>
	 * public static final int {@link AbstractJsonWriter#DROP_ROOT_MODE} = 1;
     * public static final int {@link AbstractJsonWriter#STRICT_MODE} = 2;
     * public static final int {@link AbstractJsonWriter#EXPLICIT_MODE} = 4;
     * </blockquote></pre>
	 */
	public JsonStream(final int mode) {
		stream = new XStream(new JsonHierarchicalStreamDriver() {
		    public HierarchicalStreamWriter createWriter(Writer writer) {
		        return new JsonWriter(writer, mode);
		    }
		});
	}
	
	/**
	 * 
	 * @param driver see all implementations of {@link com.thoughtworks.xstream.io.HierarchicalStreamDriver} 
	 * for detail. 
	 * <p>
	 * All implementations of {@link com.thoughtworks.xstream.io.HierarchicalStreamDriver}: 
	 * 
	 * <pre><blockquote>
	 * {@link com.thoughtworks.xstream.io.xml.Dom4JDriver}
	 * {@link com.thoughtworks.xstream.io.xml.DomDriver}
	 * {@link com.thoughtworks.xstream.io.xml.JDomDriver}
	 * {@link com.thoughtworks.xstream.io.xml.StaxDriver}
	 * {@link com.thoughtworks.xstream.io.xml.BEAStaxDriver}
	 * {@link com.thoughtworks.xstream.io.xml.SjsxpDriver}
	 * {@link com.thoughtworks.xstream.io.xml.WstxDriver}
	 * {@link com.thoughtworks.xstream.io.xml.XomDriver}
	 * {@link com.thoughtworks.xstream.io.binary.BinaryStreamDriver}
	 * {@link com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver}
	 * {@link com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver}
	 * </blockquote></pre>
	 */
	public JsonStream(HierarchicalStreamDriver driver) {
		stream = new XStream(driver);
	}
	
	public JsonStream(HierarchicalStreamDriver driver, int mode) {
		stream = new XStream(driver);
		stream.setMode(mode);
	}
	
	public void alias(Class<?> clazz, String alias) {
		stream.alias(alias, clazz);
	}
	
	public void alias(String className, String alias) throws ClassNotFoundException {
		stream.alias(alias, Class.forName(className));
		
	}
	public void alias(Class<?> clazz, String alias, Class<?> implementation) {
		stream.alias(alias, clazz, implementation);
	}
	
	
	public void aliasAttribute(String alias, String attributeName) {
		stream.aliasAttribute(alias, attributeName);
	}
	
	public void aliasField(String alias, Class<?> clazz, String fieldName) {
		stream.aliasField(alias, clazz, fieldName);
	}
	
	/**
	 * @param json
	 * @return
	 * @throws SerializationException
	 */
	public Object deserialize(String json) throws SerializationException {
		try {
			return stream.fromXML(json);
		} catch(Exception e) {
			throw new SerializationException(e);
		}
	}
	
	/**
	 * @param json
	 * @param rootName
	 * @param clazz
	 * @return
	 * @throws SerializationException
	 */
	public Object deserialize(String json, String rootName, Class<?> clazz) throws SerializationException {
		StringBuilder sb = new StringBuilder("{\"").append(rootName).append("\": ").append(json).append("}");
		log.info("deserializing json string: " + sb);
		alias(clazz, rootName);
		try {
			return stream.fromXML(sb.toString());
		} catch(Exception e) {
			throw new SerializationException(e);
		}
	}
	
	/**
	 * @param json
	 * @param root
	 * @return
	 * @throws SerializationException
	 */
	public Object deserialize(String json, Object root) throws SerializationException {
		try {
			return stream.fromXML(json, root);
		} catch(Exception e) {
			throw new SerializationException(e);
		}
	}
	
	/**
	 * @param object
	 * @return
	 * @throws SerializationException
	 */
	public String serialize(Object object) throws SerializationException {
		try {
			return stream.toXML(object);
		} catch(Exception e) {
			throw new SerializationException(e);
		}
	}
}
