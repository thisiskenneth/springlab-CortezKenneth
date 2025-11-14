package edu.espe.springlab.interceptor;

import edu.espe.springlab.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

public class StudentInterceptor {
    @WebMvcTest
    @Import({RequestLoggingInterceptor.class, WebConfig.class})
    class InterceptorElapsedTimeTest {

        @Autowired
        private MockMvc mvc;

        @Test
        void shouldAddElapsedTimeHeader() throws Exception {
            mvc.perform(get("/api/students"))
                    .andExpect(status().isOk())
                    .andExpect(header().exists("X-Elapsed-Time"));
        }
    }

}
