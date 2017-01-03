package  cn.com.bluemoon.freemarker;  
  
//这个地方可以事先定义好需要的类   
import  java.util.Date;  
  
import  java.io.Serializable;  
  
public   class  Student  implements  Serializable{  
    private  String name;  
    private  String sex;  
    private  Integer age;  
      
    public   void  setName(String name){  
        this.name=name;  
    }  
      
    public  String getName(){  
        return   this.name;  
    }  
      
    public   void  setSex(String sex){  
        this.sex=sex;  
    }  
      
    public  String getSex(){  
        return   this.sex;  
    }  
      
    public   void  setAge(Integer age){  
        this.age=age;  
    }  
      
    public  Integer getAge(){  
        return   this.age;  
    }  
      
}  