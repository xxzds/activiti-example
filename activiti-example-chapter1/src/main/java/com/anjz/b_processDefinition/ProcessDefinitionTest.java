package com.anjz.b_processDefinition;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

/**
 *  流程定义
 * @author shuai.ding
 * @date 2016年10月22日下午12:14:36
 */
public class ProcessDefinitionTest {
	
	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义（从classpath）
	 */
	@Test
	public void deploymentProcessDefinition_classpath(){
		Deployment deployment= processEngine.getRepositoryService()   //与流程定义和部署对象相关的Service
					.createDeployment()  //创建一个部署对象
					.name("流程定义")   //添加部署的名称
					.addClasspathResource("diagrams/helloworld.bpmn")  //从classpath的资源中加载，一次只能加载一个文件
					.addClasspathResource("diagrams/helloworld.png")
					.deploy();  //完成部署
		System.out.println("部署ID:"+deployment.getId());
		System.out.println("部署名称:"+deployment.getName());
	}
	
	/**
	 * 部署流程定义（从zip）
	 */
	@Test
	public void deploymentProcessDefinition_zip(){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
						.createDeployment()//创建一个部署对象
						.name("流程定义")//添加部署的名称
						.addZipInputStream(zipInputStream)//指定zip格式的文件完成部署
						.deploy();//完成部署
		System.out.println("部署ID："+deployment.getId());
		System.out.println("部署名称："+deployment.getName());
	}
	
	
	/**
	 * 查询流程定义
	 */
	@Test
	public void findProcessDefinition(){
		List<ProcessDefinition> list= processEngine.getRepositoryService()
					.createProcessDefinitionQuery()
					//指定查询条件
//					.deploymentId("15001")
//					.processDefinitionId(processDefinitionId)
//					.processDefinitionName(processDefinitionName)
//					.processDefinitionNameLike(processDefinitionNameLike)
//					.processDefinitionVersion(processDefinitionVersion)
					
					//排序
//					 .orderByProcessDefinitionName().asc()
					.orderByProcessDefinitionKey().desc()
					
					//返回的结果集
					.list();
//					.singleResult();//返回惟一结果集
//					.count();//返回结果集数量
//					.listPage(firstResult, maxResults);//分页查询
		if(list!=null && list.size()>0){
			for(ProcessDefinition pd:list){
				System.out.println("流程定义ID:"+pd.getId());//流程定义的key+版本+随机生成数
				System.out.println("流程定义的名称:"+pd.getName());//对应helloworld.bpmn文件中的name属性值
				System.out.println("流程定义的key:"+pd.getKey());//对应helloworld.bpmn文件中的id属性值
				System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
				System.out.println("资源名称bpmn文件:"+pd.getResourceName());
				System.out.println("资源名称png文件:"+pd.getDiagramResourceName());
				System.out.println("部署对象ID："+pd.getDeploymentId());
				System.out.println("#########################################################");
			}
		}			
	}
	
	/**
	 * 删除流程定义
	 */
	@Test
	public void deleteProcessDefinition(){
		//使用部署ID，完成删除
		String deploymentId = "45001";
		/**
		 * 不带级联的删除
		 *    只能删除没有启动的流程，如果流程启动，就会抛出异常
		 */
//		processEngine.getRepositoryService()
//					 .deleteDeployment(deploymentId);
		
		/**
		 * 级联删除
		 * 	  不管流程是否启动，都能可以删除
		 */
		processEngine.getRepositoryService()
					.deleteDeployment(deploymentId, true);		
		System.out.println("删除成功！");
	}
	
}
