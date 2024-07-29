<%@ page import="ext.ziang.common.helper.queue.CommonQueueHelper" %>
<%@ page import="wt.queue.ProcessingQueue" %>
<%@ page import="wt.queue.QueueEntry" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedHashMap" %>

<%
    ProcessingQueue queue = CommonQueueHelper.createProcessingQueue("ProcessingQueueTest");
    List<QueueEntry> queueAllEntry = CommonQueueHelper.getQueueAllEntry(queue, null);
    out.print(queueAllEntry);
    LinkedHashMap<Class, Object> classObjectLinkedHashMap = new LinkedHashMap<>();
    classObjectLinkedHashMap.put(String.class, "test");
    classObjectLinkedHashMap.put(Object.class, "test2");
    CommonQueueHelper.addProcessEntry(queue, CommonQueueHelper.class.getName(), "test", classObjectLinkedHashMap);
    out.print(queueAllEntry);
%>