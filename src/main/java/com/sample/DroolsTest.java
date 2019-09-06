package com.sample;

 
 
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class DroolsTest {
	public static final void main(String[] args) {  
        try {  
            // load up the knowledge base  
            KieBase kbase = readKnowledgeBase();
            KieSession ksession = kbase.newKieSession();//创建会话
            KieRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            // go !  
            Message message = new Message();  
            message.setMessage("yubinHello World");  
            message.setStatus(Message.HELLO); 
            List<Message> messages=new ArrayList<Message>();
            messages.add(message);
            ksession.insert(message);//插入  
            ksession.fireAllRules();//执行规则  
            logger.close();//关闭  
        } catch (Throwable t) {  
            t.printStackTrace();  
        }  
    }  
  
    private static KieBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();//创建规则构建器
        kbuilder.add(ResourceFactory.newClassPathResource("sample.drl"), ResourceType.DRL);//加载规则文件，并增加到构建器
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {//编译规则过程中发现规则是否有错误  
            for (KnowledgeBuilderError error: errors) {System.out.println("规则中存在错误，错误消息如下：");
                System.err.println(error);  
            }  
            throw new IllegalArgumentException("Could not parse knowledge.");  
        }  
        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();//创建规则构建库
        kbase.addPackages(kbuilder.getKnowledgePackages());//构建器加载的资源文件包放入构建库
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
