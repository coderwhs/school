package org.jeecg.modules.flowable.handle;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import org.jeecg.modules.edusport.service.IDormAthleteLeaveService;
import org.jeecg.modules.edusport.service.impl.DormAthleteLeaveServiceImpl;
import org.jeecg.modules.flowable.util.SpringUtil;

public class EndTaskHandler  implements JavaDelegate{
 


	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("EndTaskHandler已经执行已经执行！");
		
		IDormAthleteLeaveService dormAthleteLeaveService = SpringUtil.getObject(DormAthleteLeaveServiceImpl.class);
		String billId = "" + execution.getVariable("billId");
		System.out.println(">>>>>>>>>>billId>>>>>>>>>>>>>" + billId);
		DormAthleteLeave dormAthleteLeave = dormAthleteLeaveService.getById(billId);
		dormAthleteLeave.setWorkflowStatus("3");
		dormAthleteLeaveService.saveOrUpdate(dormAthleteLeave);
		
	}
 
}