# `OAuth2  Config`

## 1. Apache 

文件所在位置:`/Windchill/HTTPServer/conf/conf.d/30-app-Windchill-Auth.conf`
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
重启Apache

## 2.Tomcat 


路径为:`C:\ptc\Windchill_11.0\Windchill\codebase\WEB-INF\`
找到`web.xml`

```
  <filter>
    <description>Github OAuth2 Login</description>
    <filter-name>OAuthIndexPageFilter</filter-name>
    <filter-class>ext.ziang.oauth.OAuthIndexPageFilter</filter-class>
  </filter>
  
  <filter-mapping>
    <filter-name>OAuthIndexPageFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```
重启`Windchill`
执行命令 `Windchill stop & Windchill start`







