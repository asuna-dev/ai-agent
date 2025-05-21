package org.zepe.aiagent.app;

import cn.hutool.core.lang.UUID;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zzpus
 * @datetime 2025/5/21 15:07
 * @description
 */
@Slf4j
@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是程序员鱼皮";
        // log.info("message: {}", message);
        String answer = loveApp.chat(message, chatId);
        Assertions.assertNotNull(answer);
        // log.info("answer: {}", answer);
        // 第二轮
        message = "我想让另一半（编程导航）更爱我";
        // log.info("message: {}", message);
        answer = loveApp.chat(message, chatId);
        Assertions.assertNotNull(answer);
        // log.info("answer: {}", answer);
        // 第三轮
        message = "我的另一半叫什么来着？刚跟你说过，帮我回忆一下";
        // log.info("message: {}", message);
        answer = loveApp.chat(message, chatId);
        Assertions.assertNotNull(answer);
        // log.info("answer: {}", answer);
    }

    @Test
    void chatWithReport() {
        String message = "我是张三，我想让另一半（凤儿）更爱我,但我不知道怎么做";
        LoveApp.LoveReport loveReport = loveApp.chatWithReport(message, UUID.randomUUID().toString());
        Assertions.assertNotNull(loveReport);
        log.info("loveReport: {}", loveReport);
    }

    @Test
    void chatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太亲密，怎么办？";
        String answer = loveApp.chatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void chatWithCloudRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我单身很久了，为什么我总是遇不到合适的人？";
        String answer = loveApp.chatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }
}
