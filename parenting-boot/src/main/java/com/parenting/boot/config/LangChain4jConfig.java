package com.parenting.boot.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChain4jConfig {

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .baseUrl("https://api.deepseek.com")
                .apiKey("sk-4ced5c85cf5a4377bd4ac66f67e117ee")
                .modelName("deepseek-chat")
                .build();
    }
}
