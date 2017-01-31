package org.tis.tools.maven.plugin.utils.helper;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="bean" )
public class Bean {
	
	@XmlAttribute(name ="id")
    private Integer id;

	@XmlAttribute(name = "code")
    private String code;

	@XmlElement(name="name")
    private String name;
	
	@XmlElement(name="desc")
	private String description  ;
	
	@XmlElementWrapper(name="levels")
	@XmlElement(name="level")
    private List<Level> levels = new ArrayList<Level>();

	@XmlElementWrapper(name="extras")
	@XmlElement(name="extra")
    private List<Extra> extras = new ArrayList<Extra>();
     
    public Bean(){}
 
    @XmlTransient
    public Integer getId() {
        return id;
    }
 
    @XmlTransient
    public String getCode() {
        return code;
    }
 
    @XmlTransient
    public String getName() {
        return name;
    }
 
    @XmlTransient
    public List<Level> getLevels() {
        return levels;
    }
    
    @XmlTransient
    public List<Extra> getExtras() {
        return extras;
    }
 
    @XmlTransient
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
        this.id = id;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }
 
    public void setExtras(List<Extra> extras) {
        this.extras = extras;
    }
     
}