package com.parenting.core.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * RAG服务，处理AI问答
 */
@Service
public class RagService {

    private static final Logger log = LoggerFactory.getLogger(RagService.class);

    private final ChatLanguageModel chatModel;

    public RagService(ChatLanguageModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * 上传并处理PDF文档
     */
    public void processPdf(MultipartFile file) throws IOException {
        log.info("PDF文档处理功能暂未实现");
    }

    /**
     * 基于AI回答问题
     */
    public String answerQuestion(String question) {
        try {
            String prompt = String.format("你是一个专业的育儿助手，请用中文回答家长的问题。问题：%s", question);
            
            Response<AiMessage> response = chatModel.generate(UserMessage.from(prompt));
            return response.content().text();
        } catch (Exception e) {
            log.error("AI回答失败", e);
            return "抱歉，AI服务暂时不可用，请稍后重试。错误信息：" + e.getMessage();
        }
    }
}
