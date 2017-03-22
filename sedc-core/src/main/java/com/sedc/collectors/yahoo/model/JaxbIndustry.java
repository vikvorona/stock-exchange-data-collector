package com.sedc.collectors.yahoo.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>Java class for jaxbIndustry complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="jaxbIndustry">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sector" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlRootElement(name = "yahooindustry")
public class JaxbIndustry {

    private Integer id;

    private String name;

    private String sector;

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link Integer }
     */
    @XmlAttribute(name = "id")
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the sector property.
     *
     * @return possible object is
     * {@link String }
     */
    @XmlAttribute(name = "sector")
    public String getSector() {
        return sector;
    }

    /**
     * Sets the value of the sector property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSector(String value) {
        this.sector = value;
    }

}
