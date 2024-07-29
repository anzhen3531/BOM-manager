package ext.ziang.common.helper.queue;

import cn.hutool.core.collection.CollUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.queue.ProcessingQueue;
import wt.queue.QueueEntry;
import wt.queue.QueueHelper;
import wt.queue.WtQueueEntry;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import java.util.*;

/**
 * 队列帮助程序
 *
 * @author anzhen
 * @date 2024/04/29
 */
public class CommonQueueHelper {
    public static void test(String test, Object test2) {
        System.out.println(String.format("CommonQueueHelper Main Exec %s -> %s", test, test2));
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonQueueHelper.class);

    /**
     * 查找处理队列
     *
     * @param name 名字
     * @return {@link ProcessingQueue }
     */
    public static ProcessingQueue findProcessingQueue(String name) throws WTException {
        return QueueHelper.manager.getQueue(name);
    }


    /**
     * 创建进程队列
     *
     * @param name 名字
     * @return {@link ProcessingQueue } 进程队列对象
     */
    public static ProcessingQueue createProcessingQueue(String name) {
        boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            ProcessingQueue queue = findProcessingQueue(name);
            if (Objects.isNull(queue)) {
                queue = QueueHelper.manager.createQueue(name);
            }
            return queue;
        } catch (Exception e) {
            LOGGER.error("exec createProcessingQueue Error", e);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(flag);
        }
        return null;
    }

    /**
     * 添加进程条目
     *
     * @param queue      队列
     * @param className  类名
     * @param methodName 方法名称
     * @throws WTException WTException
     */
    public static void addProcessEntry(ProcessingQueue queue, String className, String methodName, Class[] argsType, Object[] argsValue) throws WTException {
        queue.addEntry(SessionHelper.getPrincipal(), className, methodName, argsType, argsValue);
    }

    /**
     * 获取队列所有条目
     *
     * @param queue 队列
     * @return {@link List }<{@link QueueEntry }>
     */
    public static List<QueueEntry> getQueueAllEntry(ProcessingQueue queue, List<String> statusList) {
        boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            QuerySpec qs = new QuerySpec(QueueEntry.class);
            SearchCondition isDeleteCondition = new SearchCondition(WtQueueEntry.class, "thePersistInfo.markForDelete",
                    SearchCondition.EQUAL, 0L);
            qs.appendWhere(isDeleteCondition, new int[]{0});
            qs.appendAnd();
            qs.appendWhere(new SearchCondition(QueueEntry.class, "queueRef.key",
                    SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(queue)), new int[]{0});
            if (CollUtil.isNotEmpty(statusList)) {
                qs.appendAnd();
                qs.appendWhere(new SearchCondition(new ClassAttribute(QueueEntry.class, "statusInfo.code"),
                        SearchCondition.IN, new ArrayExpression(statusList.toArray(new String[0]))), new int[]{0});
            }
            LOGGER.debug("CommonQueueHelper getQueueAllEntry SQL:{}", qs);
            QueryResult qr = PersistenceServerHelper.manager.query(qs);
            List<QueueEntry> queueEntries = new ArrayList<>(qr.size());
            while (qr.hasMoreElements()) {
                queueEntries.add((QueueEntry) qr.nextElement());
            }
            return queueEntries;
        } catch (Exception e) {
            LOGGER.error("exec createProcessingQueue Error", e);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(flag);
        }
        return Collections.emptyList();
    }

}
