package pm.keypart;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import com.bos.tis.tools.util.PinyinUtil;

@XmlRootElement
public class UiMethod {
	private String id;
	private String name;

	@XmlAttribute
	public String getId() {
		if (StringUtils.isNotEmpty(id)) {
			return id;
		}
		return PinyinUtil.convert(name);
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
