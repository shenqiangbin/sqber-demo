package com.sqber.dianzi.guangming;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginInfoModel {
    public String Key;
    public String UserName ;
    public String UserIp ;
    public String SubUserName ;
    public String GroupName ;
    public String RealName ;
    public String UnitName ;
    public String Email ;
    public String Mobile ;
    public String UserReturnUrl ;
}
