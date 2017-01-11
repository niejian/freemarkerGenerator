package cn.com.bluemoon.freemarker.feature.method;

import java.util.List;

import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * freemarker的方法类型,并在ftl文件中调用该方法 ${indexOf{xx, xx}}
 * @author niejian
 *
 */
public class IndexOfMeThod implements TemplateMethodModelEx{

	// 判断元素2位于元素1中的位置
	@Override
	public TemplateModel exec(List arguments) throws TemplateModelException {
		if(arguments.size() < 2){
			throw new TemplateModelException(this.getClass().getMethods()[0] + " 参数不对");
		}
		
		String arg1 = ((SimpleScalar ) arguments.get(0)).toString();
		String arg2 = ((SimpleScalar ) arguments.get(1)).toString();
		return new SimpleNumber(arg1.indexOf(arg2));
		//return null;
	}

}
