package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ObjectError;

public class MessageCodesResolverTest {

    MessageCodesResolver msg = new DefaultMessageCodesResolver();


    @Test
    public void messageCodesResolverObect() throws Exception {
        //given
        final String[] strings = msg.resolveMessageCodes("required", "item");
        for (int i = 0; i < strings.length; i++) {
            System.out.println("strings = " + strings[i]);
        }
        //then
        Assertions.assertThat(strings).containsExactly("required.item", "required");
    }


    @Test
    public void messageCodesResolverField() throws Exception {
        final String[] strings = msg.resolveMessageCodes("required", "item", "itemName", String.class);
        //given
        for (String string : strings) {
            System.out.println("string = " + string);
        }
        Assertions.assertThat(strings).containsExactly("required.item.itemName", "required.itemName", "required.java.lang.String", "required");
    }
}
