package com.sample;

 
 
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.cn.point.PointDomain;


public class PointTest {
	public static final void main(String[] args) {  
        try {  
            // load up the knowledge base  
            KnowledgeBase kbase = readKnowledgeBase();  
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();//创建会话  
             
            // go !  
            final PointDomain pointDomain = new PointDomain();
			pointDomain.setUserName("于斌");
			pointDomain.setBackMondy(100d);
			pointDomain.setBuyMoney(500d);
			pointDomain.setBackNums(1);
			pointDomain.setBuyNums(5);
			pointDomain.setBillThisMonth(5);
			pointDomain.setBirthDay(true);
			pointDomain.setPoint(0l);
			ksession.insert(pointDomain);//插入  
            ksession.fireAllRules();//执行规则  
            System.out.println("执行完毕BillThisMonth："+pointDomain.getBillThisMonth());
			System.out.println("执行完毕BuyMoney："+pointDomain.getBuyMoney());
			System.out.println("执行完毕BuyNums："+pointDomain.getBuyNums());
			System.out.println("执行完毕规则引擎决定发送积分："+pointDomain.getPoint());  
        } catch (Throwable t) {  
            t.printStackTrace();  
        }  
    }  
  
    private static KnowledgeBase readKnowledgeBase() throws Exception {  
    	System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();//创建规则构建器
        kbuilder.add(ResourceFactory.newClassPathResource("addpoint.drl"), ResourceType.DRL);//加载规则文件，并增加到构建器  
        kbuilder.add(ResourceFactory.newClassPathResource("subpoint.drl"), ResourceType.DRL);//加载规则文件，并增加到构建器  
        KnowledgeBuilderErrors errors = kbuilder.getErrors();  
        if (errors.size() > 0) {//编译规则过程中发现规则是否有错误  
            for (KnowledgeBuilderError error: errors) {System.out.println("规则中存在错误，错误消息如下：");  
                System.err.println(error);  
            }  
            throw new IllegalArgumentException("Could not parse knowledge.");  
        }  
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();//创建规则构建库  
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());//构建器加载的资源文件包放入构建库  
        return kbase;  
    }  
  
    public static class Message {  
  
        public static final int HELLO = 0;  
        public static final int GOODBYE = 1;  
        public static final int CANCLE=2;   
        private String message;  
  
        private int status;  
  
        public String getMessage() {  
            return this.message;  
        }  
  
        public void setMessage(String message) {  
            this.message = message;  
        }  
  
        public int getStatus() {  
            return this.status;  
        }  
  
        public void setStatus(int status) {  
            this.status = status;  
        }  
  
    }  
}
