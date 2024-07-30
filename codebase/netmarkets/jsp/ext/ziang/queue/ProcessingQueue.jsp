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
    Class[] classes = new Class[]{String.class};
    CommonQueueHelper.addProcessEntry(queue, CommonQueueHelper.class.getName(), "test", classes, new String[]{ "test" });
    out.print(queueAllEntry);
%>