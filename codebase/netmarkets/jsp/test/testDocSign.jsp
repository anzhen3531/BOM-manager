<%@ page import="java.util.Hashtable" %>
<%@ page import="ext.ziang.doc.sign.OpenPython" %>


<%
    Hashtable hashTable = new Hashtable<>();
    hashTable.put("审核", "admin 2023-02-01");
    hashTable.put("编制", "admin 2023-02-01");
    hashTable.put("批准", "admin 2023-02-01");
    hashTable.put("制作", "admin 2023-02-01");
    String signKey = hashTable.toString();
    // 增加签名参数
    String s = OpenPython.handlerWordSignToPython("C:\\test\\testDoc.docx",
            hashTable, "C:\\test\\newTestDoc.docx");
    out.println(s);
%>