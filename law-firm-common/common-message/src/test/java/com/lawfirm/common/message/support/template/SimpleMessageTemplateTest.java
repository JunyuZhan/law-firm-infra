package com.lawfirm.common.message.support.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleMessageTemplateTest {

    private static class SimpleMessageTemplate extends MessageTemplate {
        public SimpleMessageTemplate(String templateId, String content) {
            this.templateId = templateId;
            this.content = content;
        }

        @Override
        public String parse(Map<String, Object> params) {
            String result = content;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                result = result.replace("${" + entry.getKey() + "}", 
                    String.valueOf(entry.getValue()));
            }
            return result;
        }

        @Override
        public boolean validate(Map<String, Object> params) {
            for (String required : getRequiredParams()) {
                if (!params.containsKey(required)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String[] getRequiredParams() {
            return new String[]{"name", "code"};
        }
    }

    private SimpleMessageTemplate template;

    @BeforeEach
    void setUp() {
        template = new SimpleMessageTemplate(
            "test-template",
            "Hello ${name}, your verification code is: ${code}"
        );
    }

    @Test
    void should_parse_template_with_valid_params() {
        // given
        Map<String, Object> params = new HashMap<>();
        params.put("name", "John");
        params.put("code", "123456");

        // when
        String result = template.parse(params);

        // then
        assertThat(result).isEqualTo("Hello John, your verification code is: 123456");
    }

    @Test
    void should_validate_params() {
        // given
        Map<String, Object> validParams = new HashMap<>();
        validParams.put("name", "John");
        validParams.put("code", "123456");

        Map<String, Object> invalidParams = new HashMap<>();
        invalidParams.put("name", "John");

        // when & then
        assertThat(template.validate(validParams)).isTrue();
        assertThat(template.validate(invalidParams)).isFalse();
    }

    @Test
    void should_get_required_params() {
        // when
        String[] requiredParams = template.getRequiredParams();

        // then
        assertThat(requiredParams).containsExactly("name", "code");
    }
} 