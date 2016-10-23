package com.anjz.a_helloworld;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 
 * @author shuai.ding
 * @date 2016年10月22日上午10:56:38
 */
public class Helloworld {
	 //默认加载activiti.cfg.xml
	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deploymentProcessDefinition(){
		Deployment deployment=processEngine.getRepositoryService()  //与流程定义和部署对象相关的service
							  .createDeployment()   //创建一个部署对象
							  .name("helloworld")   //添加部署的名称
							  .addClasspathResource("diagrams/helloworld.bpmn")  //从classpath的资源中加载，一次只能加载一个文件
							  .addClasspathResource("diagrams/helloworld.png")
							  .deploy();  //完成部署
		System.out.println("部署ID:"+deployment.getId());
		System.out.println("部署名称:"+deployment.getName());
	}
	
	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcessInstance(){
		//流程定义的key
		String processDefinitionKey="helloworld";
		ProcessInstance pi = processEngine.getRuntimeService()  //与正在执行的流程实例和执行对象相关的service
					.startProcessInstanceByKey(processDefinitionKey);  // //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值
		System.out.println("流程实例ID："+pi.getId());  //流程实例ID
		System.out.println("流程定义ID："+pi.getProcessDefinitionId());  //流程定义ID
	}
	
	/**
	 * 查询当前人的个人任务
	 */
	@Test
	public void findMyPersonalTask(){
		String assignee="张三";
		List<Task>  list= processEngine.getTaskService()   //与正在执行的任务管理相关的Service
					 .createTaskQuery()  //创建任务查询对象
					 .taskAssignee(assignee)  //指定个人任务查询，指定办理人
					 .list();
		
		if(list!=null && list.size()>0){
			for(Task task:list){
				System.out.println("任务ID:"+task.getId());
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务创建时间:"+task.getCreateTime());
				System.out.println("任务办理人:"+task.getAssignee());
				System.out.println("流程实例ID:"+task.getProcessInstanceId());
				System.out.println("执行对象ID:"+task.getExecutionId());
				System.out.println("流程定义ID:"+task.getProcessDefinitionId());
			}
		}
	}
	
	/**
	 * 完成我的任务
	 */
	@Test
	public void completeMyPersonalTask(){
		//任务ID
		String taskId="2504";
		processEngine.getTaskService()   //与正在执行的任务管理相关的Service
					 .complete(taskId);
		System.out.println("任务完成：任务ID:"+taskId);
	}
}
