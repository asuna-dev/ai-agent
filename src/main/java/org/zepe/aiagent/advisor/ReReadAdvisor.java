package org.zepe.aiagent.advisor;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.advisor.api.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;

/**
 * @author zzpus
 * @datetime 2025/5/21 15:35
 * @description
 */
@Slf4j
public class ReReadAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {
    @NotNull
    @Override
    public AdvisedResponse aroundCall(@NotNull AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        return chain.nextAroundCall(before(advisedRequest));
    }

    @NotNull
    @Override
    public Flux<AdvisedResponse> aroundStream(@NotNull AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        return chain.nextAroundStream(before(advisedRequest));
    }

    @NotNull
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 1;
    }

    private AdvisedRequest before(AdvisedRequest request) {
        HashMap<String, Object> advisedParams = new HashMap<>(request.userParams());
        advisedParams.put("re2_input_query", request.userText());

        return AdvisedRequest.from(request).userText("""
            {re2_input_query}
            Read the question again: {re2_input_query}
            """).userParams(advisedParams).build();

    }

}
