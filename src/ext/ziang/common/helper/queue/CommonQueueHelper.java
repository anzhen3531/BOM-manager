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
import wt.queue.*;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import java.sql.Timestamp;
import java.util.*;

/**
 * 队列帮助程序
 *
 * @author anzhen
 * @date 2024/04/29
 */
public class CommonQueueHelper {
    public static void test(String test, Object test2) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println(String.format("CommonQueueHelper Main Exec %s -> %s", test, test2));
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonQueueHelper.class);


    public static void main(String[] args) {
        long var8 = new Date().getTime();
        System.out.println("new Date() = " + new Date());
        System.out.println("var8 = " + var8);
        var8 = var8 / 1000L * 1000L;
        System.out.println("var8 = " + var8);
        Timestamp var10 = new Timestamp(var8);
        System.out.println("new Date(var10.getTime()) = " + new Date(var10.getTime()));
    }

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
        queue.addEntry(SessionHelper.getPrincipal(), methodName, className, argsType, argsValue);
    }

    /**
     * 获取队列所有条目
     *
     * @param queue 队列
     * @return {@link List }<{@link QueueEntry }>
     */
    public static List<QueueEntry> getQueueAllEntry(WtQueue queue, List<String> statusList) {
        boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            QuerySpec qs = new QuerySpec(QueueEntry.class);
            SearchCondition isDeleteCondition = new SearchCondition(WtQueueEntry.class, "thePersistInfo.markForDelete", SearchCondition.EQUAL, 0L);
            qs.appendWhere(isDeleteCondition, new int[]{0});
            qs.appendAnd();
            qs.appendWhere(new SearchCondition(QueueEntry.class, "queueRef.key", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(queue)), new int[]{0});
            if (CollUtil.isNotEmpty(statusList)) {
                qs.appendAnd();
                qs.appendWhere(new SearchCondition(new ClassAttribute(QueueEntry.class, "statusInfo.code"), SearchCondition.IN, new ArrayExpression(statusList.toArray(new String[0]))), new int[]{0});
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

    /**
     * 删除进程队列
     *
     * @param queueName 队列名称
     */
    public static void deleteProcessQueue(String queueName) {
        boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            ProcessingQueue processingQueue = findProcessingQueue(queueName);
            if (processingQueue == null) {
                return;
            }
            QueueHelper.manager.deleteQueue(processingQueue);
        } catch (Exception e) {
            LOGGER.error("exec createProcessingQueue Error", e);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(flag);
        }
    }

    /**
     * 查找处理队列
     *
     * @param name 名字
     * @return {@link ProcessingQueue }
     */
    public static ScheduleQueue findScheduleQueue(String name) throws WTException {
        return (ScheduleQueue) QueueHelper.manager.getQueue(name, ScheduleQueue.class);
    }

    /**
     * 创建排程队列
     *
     * @param name 名字
     * @return {@link ProcessingQueue } 进程队列对象
     */
    public static ScheduleQueue createScheduleQueue(String name) {
        boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            ScheduleQueue queue = findScheduleQueue(name);
            if (Objects.isNull(queue)) {
                queue = QueueHelper.manager.createScheduleQueue(name);
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
     * 删除排程队列
     *
     * @param name 名字
     * @return {@link ProcessingQueue } 进程队列对象
     */
    public static void deleteScheduleQueue(String name) {
        boolean flag = SessionServerHelper.manager.setAccessEnforced(false);
        try {
            ScheduleQueue queue = findScheduleQueue(name);
            if (Objects.nonNull(queue)) {
                QueueHelper.manager.deleteQueue(queue);
            }
        } catch (Exception e) {
            LOGGER.error("exec createProcessingQueue Error", e);
        } finally {
            SessionServerHelper.manager.setAccessEnforced(flag);
        }
    }


    /**
     * 添加排程队列
     * <p>
     * 设置时间会自动去除毫秒和
     * long var8 = var6.getTime();
     * var8 = var8 / 1000L * 1000L;
     * Timestamp var10 = new Timestamp(var8);
     * var7.setScheduleTime(var10);
     *
     * @param queue      队列
     * @param className  类名
     * @param methodName 方法名称
     * @param argsType   args 类型
     * @param argsValue  args 值
     * @param timestamp  时间戳
     * @throws WTException WTException
     */
    public static ScheduleQueueEntry addScheduleEntry(ScheduleQueue queue, String className, String methodName,
                                                      Class[] argsType, Object[] argsValue, Timestamp timestamp) throws WTException {
        return queue.addEntry(SessionHelper.getPrincipal(), methodName, className, argsType, argsValue, timestamp);
    }
}

