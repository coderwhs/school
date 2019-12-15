package org.jeecg.modules.demo.test.controller;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

public class BossTaskHandler implements TaskListener {
 
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("医生");
    }
 
}