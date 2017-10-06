/**
 * 
 */
package org.tis.tools.common.utils.xpath;

import java.util.HashMap;
import java.util.ListIterator;

/**
 * 
  */

@SuppressWarnings("unchecked")
public class XPathParser {

	private static HashMap pool = null;
	// private static HashMap pool = new HashMap();

	public static final short PATH_ABSOLUTE = 0;
	public static final short PATH_RELATIVE = 1;

	private Object[] nodes = new Object[8];
	private int cursor;
	private int max = 8;

	// xPath: /node/nodename[@attrname=attrval][...]...
	public XPathParser(String xPath) {
		if (xPath == null) {
			return;
		}
		xPath = xPath.trim();

		if (xPath.length() == 0) {
			return;
		}

		// 是否在\之后
		boolean afterBacklash = false;
		// 是否在双引号内
		boolean inDoubleIngo = false;
		// 是否在单引号内
		boolean inSingeIngo = false;
		// 是否在方括号内
		boolean inSquareBrackets = false;

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < xPath.length(); i++) {
			char c = xPath.charAt(i);
			if (afterBacklash) {
				buf.append(c);
				afterBacklash = false;
				continue;
			}
			if (c == '\\') {
				buf.append(c);
				afterBacklash = true;
				continue;
			}

			if (c == '"') {
				buf.append(c);
				inDoubleIngo = !inDoubleIngo;
				continue;
			}
			if (c == '\'') {
				buf.append(c);
				inSingeIngo = !inSingeIngo;
				continue;
			}
			if (c == '[') {
				buf.append(c);
				if (!inDoubleIngo && !inSingeIngo) {
					inSquareBrackets = true;
					continue;
				}
			}
			if (c == ']') {
				buf.append(c);
				if (!inDoubleIngo && !inSingeIngo) {
					inSquareBrackets = false;
					continue;
				}
			}
			if (c == '/') {
				if (!inSquareBrackets) {
					if (buf.length() == 0) {
						continue;
					}
					nodes[cursor] = new NodeDescription(buf.toString());
					cursor++;
					if (cursor >= max) {
						expand();
					}

					buf = new StringBuffer();
					continue;
				}
			}
			buf.append(c);
		}

		if (buf.length() > 0) {
			nodes[cursor] = new NodeDescription(buf.toString());
			cursor++;
			if (cursor >= max) {
				expand();
			}
		}

		// StringTokenizer stk = new StringTokenizer(xPath, "/");
		// while (stk.hasMoreTokens()) {
		// nodes[cursor] = new NodeDescription(stk.nextToken());
		// cursor++;
		// if (cursor >= max)
		// expand();
		// }
	}

	public ListIterator listIterator() {
		return new XPathListIterator(nodes, cursor);
	}

	private void expand() {
		Object[] oldObjs = nodes;
		max += 8;
		nodes = new Object[max];
		System.arraycopy(oldObjs, 0, nodes, 0, cursor);
	}

	public static int newXPathSize = 0;
	public static final int flushSize = 10;

	public static XPathParser create(String xPath) {
		return new XPathParser(xPath);
	}

	public static Object[] getXPaths() {
		return pool.keySet().toArray();
	}

}

@SuppressWarnings("unchecked")
class XPathListIterator implements ListIterator {
	private Object[] objs = null;
	private int index;
	private int max;

	public XPathListIterator(Object[] objs, int max) {
		this.objs = objs;
		this.max = max;
	}

	public void add(Object o) {
	}

	public boolean hasNext() {
		return index < max;
	}

	public boolean hasPrevious() {
		return index > 0;
	}

	public Object next() {
		return objs[index++];
	}

	public int nextIndex() {
		return index + 1;
	}

	public Object previous() {
		return objs[index--];
	}

	public int previousIndex() {
		return index - 1;
	}

	public void remove() {
	}

	public void set(Object o) {
	}
}
