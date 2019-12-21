package org.jeecg.modules.flowable.handle;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

public class BossTaskHandler implements TaskListener {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("headmaster");
    }
 
}