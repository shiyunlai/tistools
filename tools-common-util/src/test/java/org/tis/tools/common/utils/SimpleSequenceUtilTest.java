package org.tis.tools.common.utils;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SimpleSequenceUtilTest {

	@Test
	public void test() {
		System.out.println(SequenceSimpleUtil.instance.getId());
		Assert.assertEquals(17, SequenceSimpleUtil.instance.getId().length());
		Assert.assertEquals(26, SequenceSimpleUtil.instance.getId("shiyunlai").length());
		Assert.assertNotSame(SequenceSimpleUtil.instance.getId("shiyunlai"),
				SequenceSimpleUtil.instance.getId("shiyunlai"));
		Assert.assertEquals("无重复", isReSequence(1000));//一秒内 1000个不重复的序号
		Assert.assertEquals("无重复", isReSequence(5000));//一秒内 5000个不重复的序号
		Assert.assertEquals("无重复", isReSequence(10000));//一秒内 10000个不重复的序号
		Assert.assertEquals("无重复", isReSequence(20000));//一秒内 20000个不重复的序号
		Assert.assertEquals("无重复", isReSequence(30000));//一秒内 30000个不重复的序号
	}
	
	/**
	 * 反复产生retimes个序号，并检查是否有重复
	 * @param reTimes
	 * @return 
	 */
	private String isReSequence(int reTimes ){
		String temp =""; 
		List <String> l = new ArrayList<String>() ;
		
		long start = System.currentTimeMillis() ;
		for( int i =0 ; i < reTimes ; i ++ ){
			temp = SequenceSimpleUtil.instance.getId() ;
			if( l.contains(temp) ){
				return "重复："+temp ;
			}else{
				l.add(temp);
			}
		}
		System.out.println("耗时："+(System.currentTimeMillis() - start));
		
		return "无重复" ;
	}
}
