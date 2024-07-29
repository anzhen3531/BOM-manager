<%@ page import="ext.ziang.common.helper.queue.CommonQueueHelper" %>
<%@ page import="wt.queue.ProcessingQueue" %>
<%@ page import="wt.queue.QueueEntry" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedHashMap" %>

<%
    // D:\private\product\codebase\netmarkets\jsp\ext\ziang\queue\ProcessingQueue.jsp
    ProcessingQueue queue = CommonQueueHelper.createProcessingQueue("ProcessingQueueTest");
    List<QueueEntry> queueAllEntry = CommonQueueHelper.getQueueAllEntry(queue, null);
    out.print(queueAllEntry);
    LinkedHashMap<Class, Object> classObjectLinkedHashMap = new LinkedHashMap<>();
    classObjectLinkedHashMap.put(String.class, "test");
    classObjectLinkedHashMap.put(Object.class, "test2");
    Class[] classes = new Class[]{String.class, Object.class};
    CommonQueueHelper.addProcessEntry(queue, CommonQueueHelper.class.getName(), "test", classes, new String(){ "test",
            "test2"});
    out.print(queueAllEntry);
%>