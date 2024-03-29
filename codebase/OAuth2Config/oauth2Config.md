# `OAuth2  Config`

## 1. Apache 



```xml
# 注释系统默认的
#<LocationMatch ^/+Windchill/+(;.*)?>
#AuthName "Windchill"
#AuthType Basic
#AuthBasicProvider Windchill-AdministrativeLdap Windchill-EnterpriseLdap
#Require valid-user
#</LocationMatch>


#Windchill/netmarkets/jsp/gwt/login.jsp
<LocationMatch ^/+Windchill/+netmarkets/+jsp/+gwt/+login.jsp(;.*)?>
  Satisfy Any
  Allow from all
</LocationMatch>
```




## 2.Tomcat 



找到`mvc.xml`



```

```







