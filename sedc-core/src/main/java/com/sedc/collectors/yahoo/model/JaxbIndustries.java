package com.sedc.collectors.yahoo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Java class for industries complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="industries">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="jaxbIndustry" type="{}jaxbIndustry" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yahooindustries", propOrder = {"yahooindustry"})
public class JaxbIndustries {

    protected List<JaxbIndustry> jaxbIndustry;

    /**
     * Gets the value of the jaxbIndustry property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jaxbIndustry property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJaxbIndustry().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JaxbIndustry }
     */
    public List<JaxbIndustry> getJaxbIndustry() {
        if (jaxbIndustry == null) {
            jaxbIndustry = new ArrayList<JaxbIndustry>();
        }
        return this.jaxbIndustry;
    }

}
