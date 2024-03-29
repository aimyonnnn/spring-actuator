package hello.order.v2;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigv2 {

    @Bean
    public OrderServiceV2 orderServiceV2() {
        return new OrderServiceV2();
    }

    // CountedAspect를 등록하면 @Counted를 인지해서 Counter를 사용하는 AOP를 적용한다.
    // 주의! CountedAspect를 빈으로 등록하지 않으면 @Counted 관련 AOP가 동작하지 않는다.
    @Bean
    public CountedAspect countedAspect(MeterRegistry registry) {
        return new CountedAspect(registry);
    }

}
