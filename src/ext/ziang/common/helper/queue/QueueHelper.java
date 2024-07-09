package ext.ziang.common.helper.queue;

import wt.queue.StandardQueueService;
import wt.queue.WtQueue;

/**
 * 队列帮助程序
 *
 * @author anzhen
 * @date 2024/04/29
 */
public class QueueHelper {
	/**
	 * 按名称查找队列
	 *
	 * @param name
	 *            名字
	 * @return {@link WtQueue}
	 */
	public static WtQueue findQueueByName(String name) {
		try {
			// 创建队列
			StandardQueueService standardQueueService = StandardQueueService.newStandardQueueService();
			return standardQueueService.getQueue(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 发布定时队列
	// 设置定时时间即可

	// 创建对垒
}
