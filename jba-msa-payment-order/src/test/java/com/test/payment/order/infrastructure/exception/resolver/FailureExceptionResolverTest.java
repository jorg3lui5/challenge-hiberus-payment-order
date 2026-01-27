package com.test.payment.order.infrastructure.exception.resolver;

import com.test.payment.order.infrastructure.exception.custom.ParsingJsonException;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorList;
import com.test.payment.order.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.payment.order.infrastructure.input.adapter.rest.config.PropertiesTest;
import com.test.payment.order.util.JsonSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.test.payment.order.util.MockDataUtils.URL_TEST;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {FailureExceptionResolver.class})
@ImportAutoConfiguration({PropertiesTest.class})
@SuppressWarnings("java:S2699")
class FailureExceptionResolverTest extends ErrorBadRequestResolverTest<FailureExceptionResolver> {

    private static final String EXCEPTION_MESSAGE = "{\"title\":\"\",\"detail\":\"Parameter debtorAccountcurrency was sent with\",\"errors\":[{\"code\":\"400\"}],\"instance\":\"BS-400\",\"type\":\"/payment-orders\"}";

    @Test
    void givenIllegalArgumentExceptionWhenIllegalArgumentExceptionResolverIsCalledThenReturnError() {
        ErrorList errorList = new ErrorList();
        errorList.code("01")
                .businessMessage("businessMessage")
                .message("message");
        List<ErrorList> errors = new ArrayList<>();
        errors.add(errorList);
        final var exception = new FailureException(
                new ErrorModel()
                    .title("BAD REQUEST")
                    .detail(EXCEPTION_MESSAGE)
                        .errors(errors)
                , 400
        );
        callToErrorResolverAndExpectError(
                exception,
                "",
                "Parameter debtorAccountcurrency was sent with",
                HttpStatus.BAD_REQUEST.value(),
                URL_TEST
        );
    }

    @Test
    void givenWrongExceptionWhenWebClientResponseExceptionResolverIsCalledThenThrowClassCastException() {
        callToErrorResolverAndExpectClassCastException(new Exception(EXCEPTION_MESSAGE));
    }

    @Test
    void testJsonStringToObjectEmpty() {
        Assertions.assertNull(JsonSerializer.jsonStringToObject("", ErrorModel.class));
    }

    @Test
    void failureExceptionResolverTestJsonProcessingException() {
        try {
            JsonSerializer.jsonStringToObject("--{}{}--", ErrorModel.class);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof ParsingJsonException);
        }
    }
}