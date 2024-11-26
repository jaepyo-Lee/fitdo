package com.jaejoo.fitdo.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaejoo.fitdo.config.JacksonConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Import(JacksonConfig.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public abstract class RestDocsSupport {
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setup(RestDocumentationContextProvider provider) {
        this.mvc = MockMvcBuilders.standaloneSetup(initController())
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .build();
    }

    protected abstract Object initController();
}
