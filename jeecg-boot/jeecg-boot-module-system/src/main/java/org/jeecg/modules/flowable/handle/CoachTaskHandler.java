package org.jeecg.modules.flowable.handle;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.jeecg.modules.edusport.entity.Athlete;
import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.jeecg.modules.edusport.service.IDormAthleteLeaveService;
import org.jeecg.modules.edusport.service.impl.AthleteServiceImpl;
import org.jeecg.modules.edusport.service.impl.DormAthleteLeaveServiceImpl;
import org.jeecg.modules.flowable.util.SpringUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 
@Component("coachTaskHandler")
@Transactional
public class CoachTaskHandler implements TaskListener {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
    public void notify(DelegateTask delegateTask) {
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + delegateTask.getVariable("outcome") + ":" + delegateTask.getVariable("nextHandler"));
		
		if ("leave".equals(delegateTask.getVariable("billType"))) {
			IAthleteService athleteService = SpringUtil.getObject(AthleteServiceImpl.class);
			IDormAthleteLeaveService dormAthleteLeaveService = SpringUtil.getObject(DormAthleteLeaveServiceImpl.class);
			String billId = "" + delegateTask.getVariable("billId");
			System.out.println(">>>>>>>>>>billId>>>>>>>>>>>>>" + billId);
			DormAthleteLeave dormAthleteLeave = dormAthleteLeaveService.getById(billId);
			
			Athlete athlete = athleteService.getById(dormAthleteLeave.getAthleteId());
			String coachId = athlete.getCoachId();
			delegateTask.setAssignee(coachId);
			System.out.println(">>>>>>>>>>>>coachId>>>>>>>>>>>" + coachId);
			System.out.println("getExecutionId="+delegateTask.getExecutionId() + "ProcessInstanceId="+delegateTask.getProcessInstanceId()+
	                "assignee"+delegateTask.getAssignee()+"  "+" name="+delegateTask.getName()+
	                " 下一活动节点 getEventName= "+delegateTask.getEventName()+" 下一任务id="+delegateTask.getId());
			
		}
//		List<Coach> coachs = coachMapper.getCoachByBillId(delegateTask.getVariable("nextHandler").toString());
		
//		Map<String, Object> columnMap = Maps.newHashMap();
//		columnMap.put("athleteNo", delegateTask.getVariable("nextHandler").toString());
//		List<Athlete> list = (List<Athlete>)athleteService.listByMap(columnMap);
//		
//		Athlete athlete = list.get(0);
//        delegateTask.setAssignee("T002");
//        delegateTask.setAssignee(coachs.get(0).getCoachNo());
    }
 
}