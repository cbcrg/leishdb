//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.08 at 04:06:38 PM CEST 
//


package org.uniprot.uniprot;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Contains all information about the source this citation is referring to (RC line). The
 *                 used child-element names are equivalent to the tokens used in the RC line. Examples:
 *                 RC STRAIN=Sprague-Dawley; TISSUE=Liver;
 *                 RC STRAIN=Holstein; TISSUE=Lymph node, and Mammary gland;
 *                 RC PLASMID=IncFII R100;
 *             
 * 
 * <p>Java class for sourceDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sourceDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="strain">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="plasmid">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="transposon">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="tissue">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sourceDataType", propOrder = {
    "strainOrPlasmidOrTransposon"
})
public class SourceDataType {

    @XmlElements({
        @XmlElement(name = "plasmid", type = SourceDataType.Plasmid.class),
        @XmlElement(name = "tissue", type = SourceDataType.Tissue.class),
        @XmlElement(name = "strain", type = SourceDataType.Strain.class),
        @XmlElement(name = "transposon", type = SourceDataType.Transposon.class)
    })
    protected List<Object> strainOrPlasmidOrTransposon;

    /**
     * Gets the value of the strainOrPlasmidOrTransposon property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the strainOrPlasmidOrTransposon property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStrainOrPlasmidOrTransposon().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SourceDataType.Plasmid }
     * {@link SourceDataType.Tissue }
     * {@link SourceDataType.Strain }
     * {@link SourceDataType.Transposon }
     * 
     * 
     */
    public List<Object> getStrainOrPlasmidOrTransposon() {
        if (strainOrPlasmidOrTransposon == null) {
            strainOrPlasmidOrTransposon = new ArrayList<Object>();
        }
        return this.strainOrPlasmidOrTransposon;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Plasmid {

        @XmlValue
        protected String value;
        @XmlAttribute
        protected String evidence;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the evidence property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEvidence() {
            return evidence;
        }

        /**
         * Sets the value of the evidence property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEvidence(String value) {
            this.evidence = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Strain {

        @XmlValue
        protected String value;
        @XmlAttribute
        protected String evidence;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the evidence property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEvidence() {
            return evidence;
        }

        /**
         * Sets the value of the evidence property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEvidence(String value) {
            this.evidence = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Tissue {

        @XmlValue
        protected String value;
        @XmlAttribute
        protected String evidence;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the evidence property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEvidence() {
            return evidence;
        }

        /**
         * Sets the value of the evidence property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEvidence(String value) {
            this.evidence = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="evidence" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Transposon {

        @XmlValue
        protected String value;
        @XmlAttribute
        protected String evidence;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the evidence property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEvidence() {
            return evidence;
        }

        /**
         * Sets the value of the evidence property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEvidence(String value) {
            this.evidence = value;
        }

    }

}
