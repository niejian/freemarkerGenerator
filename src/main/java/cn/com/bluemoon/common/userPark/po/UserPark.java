package  cn.com.bluemoon.common.userPark.po;
import java.io.Serializable;

public class UserPark implements Serializable{ 
	private static final long serialVersionUID = 1484107841872L;
    private java.sql.Timestamp updateTime;  
    private java.lang.String nickName;  
    private java.lang.String name;  
    private java.lang.String mobile;  
    private java.lang.String plate;  
    private java.lang.String address;  
      
    public void setUpdateTime(java.sql.Timestamp updateTime){  
        this.updateTime = updateTime;  
    }  
      
    public java.sql.Timestamp getUpdateTime(){  
        return this.updateTime;  
    }  
      
    public void setNickName(java.lang.String nickName){  
        this.nickName = nickName;  
    }  
      
    public java.lang.String getNickName(){  
        return this.nickName;  
    }  
      
    public void setName(java.lang.String name){  
        this.name = name;  
    }  
      
    public java.lang.String getName(){  
        return this.name;  
    }  
      
    public void setMobile(java.lang.String mobile){  
        this.mobile = mobile;  
    }  
      
    public java.lang.String getMobile(){  
        return this.mobile;  
    }  
      
    public void setPlate(java.lang.String plate){  
        this.plate = plate;  
    }  
      
    public java.lang.String getPlate(){  
        return this.plate;  
    }  
      
    public void setAddress(java.lang.String address){  
        this.address = address;  
    }  
      
    public java.lang.String getAddress(){  
        return this.address;  
    }  
      
}  