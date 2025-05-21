package org.zepe.aiagent.rag;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetriever;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrieverOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzpus
 * @datetime 2025/5/21 22:47
 * @description
 */
@Slf4j
@Configuration
public class LoveAppRagCloudAdvisorConfig {
    private static final String KNOWLEDGE_INDEX = "love_master";
    @Value("${spring.ai.dashscope.api-key}")
    private String dashScopeApiKey;

    @Bean
    public Advisor loveAppRagCloudAdvisor() {
        DashScopeApi dashScopeApi = new DashScopeApi(dashScopeApiKey);
        DashScopeDocumentRetriever documentRetriever = new DashScopeDocumentRetriever(dashScopeApi,
            DashScopeDocumentRetrieverOptions.builder().withIndexName(KNOWLEDGE_INDEX).build()
        );
        return RetrievalAugmentationAdvisor.builder().documentRetriever(documentRetriever).build();
    }
}
