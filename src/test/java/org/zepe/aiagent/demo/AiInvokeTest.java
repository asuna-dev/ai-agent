package org.zepe.aiagent.demo;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zzpus
 * @datetime 2025/5/20 17:57
 * @description
 */
@Slf4j
@SpringBootTest
public class AiInvokeTest {
    @Resource
    private ChatModel chatModel;

    @Test
    public void run() throws Exception {
        AssistantMessage output = chatModel.call(new Prompt("你好，请你做个自我介绍")).getResult().getOutput();
        log.info("result: {}", output);
    }

}

