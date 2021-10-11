package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTests {

    @Autowired
    MessageSource messageSource;


    @Test
    void messageTest() throws Exception {
        //given
        final String result = messageSource.getMessage("hello", null, null);
        final String result_en = messageSource.getMessage("hello", null, Locale.ENGLISH);
        //when
        Assertions.assertThat(result).isEqualTo("안녕");
        Assertions.assertThat(result_en).isEqualTo("hello");
        //then
    }
    
    
    @Test
    public void notFoundMessage() throws Exception {
        //given
        Assertions.assertThatThrownBy(() -> messageSource.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }
    
    
    @Test
    public void notFoundDefaultMessage() throws Exception {
        //given
        final String message = messageSource.getMessage("no_code", null, "기본 메시지", null);
        Assertions.assertThat(message).isEqualTo("기본 메시지");

        //when
        
        //then
    }


    @Test
    public void argumentMessage() throws Exception {
        //given
        Assertions.assertThat("안녕 Spring").isEqualTo(messageSource.getMessage("hello.name", new Object[]{"Spring"}, null));
        //when
        //then
    }
}
