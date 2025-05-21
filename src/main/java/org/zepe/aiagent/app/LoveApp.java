package org.zepe.aiagent.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;
import org.zepe.aiagent.advisor.MyLoggerAdvisor;
import org.zepe.aiagent.advisor.ReReadAdvisor;
import org.zepe.aiagent.chatmemeory.FileBasedChatMemory;

import java.util.List;

/**
 * @author zzpus
 * @datetime 2025/5/21 14:58
 * @description
 */
@Slf4j
@Component
public class LoveApp {

    private static final String SYSTEM_PROMPT =
        "请扮演深耕恋爱心理领域的专家。开场向用户表明身份，告知用户可倾诉恋爱难题。" +
        "围绕单身、恋爱、已婚三种状态提问：单身状态询问社交圈拓展及追求心仪对象的困扰；" +
        "恋爱状态询问沟通、习惯差异引发的矛盾；已婚状态询问家庭责任与亲属关系处理的问题。" +
        "引导用户详述事情经过、对方反应及自身想法，以便给出专属解决方案。";
    private static final String CHAT_MEMORY_SESSION_ID_KEY = "sessionId";
    private static final String CHAT_MEMORY_RETRIEVE_SIZE_KEY = "retrieveSize";
    private final ChatClient chatClient;

    public LoveApp(ChatModel dashscopeChatModel) {
        // 初始化基于文件的对话记忆
        String dir = System.getProperty("user.dir") + "/chat-memory";
        ChatMemory chatMemory = new FileBasedChatMemory(dir);
        chatClient = ChatClient.builder(dashscopeChatModel)
            .defaultSystem(SYSTEM_PROMPT)
            .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory),
                new MyLoggerAdvisor(),
                new ReReadAdvisor(),
                new SimpleLoggerAdvisor()
            )
            .build();
    }

    public String chat(String message, String chatId) {
        ChatClient.CallResponseSpec call = chatClient.prompt()
            .user(message)
            .advisors(spec -> spec.param(CHAT_MEMORY_SESSION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
            .call();
        ChatResponse response = call.chatResponse();
        return response.getResult().getOutput().getText();
    }

    public LoveReport chatWithReport(String message, String chatId) {
        LoveReport loveReport = chatClient.prompt()
            .system(SYSTEM_PROMPT + "每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")
            .user(message)
            .advisors(spec -> spec.param(CHAT_MEMORY_SESSION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
            .call()
            .entity(LoveReport.class);
        log.info("loveReport: {}", loveReport);
        return loveReport;
    }

    /**
     * 恋爱报告 record 不可变对象
     */
    public record LoveReport(String title, List<String> suggestions) {

    }
}
