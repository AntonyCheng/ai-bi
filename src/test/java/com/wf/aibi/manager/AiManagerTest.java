package com.wf.aibi.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AiManagerTest {
    @Resource
    private AiManager aiManager;

    @Test
    void doChat() {
        String jay = aiManager.doChat(1708117271022800898L, "周杰伦");
        System.out.println(jay);
    }
}