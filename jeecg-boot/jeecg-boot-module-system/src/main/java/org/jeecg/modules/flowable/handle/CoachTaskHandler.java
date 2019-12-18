package org.jeecg.modules.flowable.handle;
import java.util.List;

import javax.annotation.Resource;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.jeecg.modules.edusport.entity.Coach;
import org.jeecg.modules.edusport.mapper.CoachMapper;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.springframework.beans.factory.annotation.Autowired;
 
public class CoachTaskHandler implements TaskListener {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private CoachMapper coachMapper;
	
	@Autowired
	private IAthleteService athleteService;
	

	@Override
    public void notify(DelegateTask delegateTask) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + delegateTask.getVariable("outcome") + ":" + delegateTask.getVariable("nextHandler"));
		
//		List<Coach> coachs = coachMapper.getCoachByBillId(delegateTask.getVariable("nextHandler").toString());
		
//		Map<String, Object> columnMap = Maps.newHashMap();
//		columnMap.put("athleteNo", delegateTask.getVariable("nextHandler").toString());
//		List<Athlete> list = (List<Athlete>)athleteService.listByMap(columnMap);
//		
//		Athlete athlete = list.get(0);
        delegateTask.setAssignee("T002");
//        delegateTask.setAssignee(coachs.get(0).getCoachNo());
    }
 
}