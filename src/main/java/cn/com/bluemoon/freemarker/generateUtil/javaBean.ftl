package  ${ package };
<#-- 
	<#list importPackages as importPackage>  
	import ${importPackage};
	</#list>
 -->
import java.io.Serializable;

public class ${className} implements Serializable{ 
	private static final long serialVersionUID = ${serialVersionUID}L;
<#list properties as pro>  
    private ${pro.proType}${pro.generic!''} ${pro.proName};  
</#list>  
      
<#list properties as pro>
    public void set<@upperFC>${pro.proName}</@upperFC>(${pro.proType}${pro.generic!''} ${pro.proName}){  
        this.${pro.proName} = ${pro.proName};  
    }  
      
    public ${pro.proType}${pro.generic!''} get<@upperFC>${pro.proName}</@upperFC>(){  
        return this.${pro.proName};  
    }  
      
</#list>
}  