package org.jeecg.modules.flowable.handle;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import org.jeecg.modules.edusport.service.IDormAthleteLeaveService;
import org.jeecg.modules.edusport.service.impl.DormAthleteLeaveServiceImpl;
import org.jeecg.modules.flowable.util.SpringUtil;

public class EndTaskHandler implements TaskListener {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void notify(DelegateTask delegateTask) {
		IDormAthleteLeaveService dormAthleteLeaveService = SpringUtil.getObject(DormAthleteLeaveServiceImpl.class);
		String billId = "" + delegateTask.getVariable("billId");
		System.out.println(">>>>>>>>>>billId>>>>>>>>>>>>>" + billId);
		DormAthleteLeave dormAthleteLeave = dormAthleteLeaveService.getById(billId);
		dormAthleteLeave.setWorkflowState("3");
		dormAthleteLeaveService.saveOrUpdate(dormAthleteLeave);
    }
 
}