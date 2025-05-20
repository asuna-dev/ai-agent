package org.zepe.aiagent.api;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author zzpus
 * @datetime 2025/5/20 19:07
 * @description
 */
@Slf4j
@Component
public class AiInvoke implements CommandLineRunner {
    @Resource
    private ChatModel chatModel;

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage output = chatModel.call(new Prompt("你好，请你做个自我介绍")).getResult().getOutput();
        log.info("result: {}", output);
    }
}
