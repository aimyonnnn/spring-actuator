package hello.order.gauge;

import hello.order.OrderService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class StockConfigV2 {

//    @Bean
//    public MeterBinder stockSize(OrderService orderService) {
//        return registry -> Gauge.builder("my.stock", orderService, service -> {
//            log.info("stock gauge call");
//            return service.getStock().get();
//        }).register(registry);
//    }

    @Bean
    public MeterBinder stockSize(OrderService orderService) {
        return new MeterBinder() {
            @Override
            public void bindTo(MeterRegistry registry) {
                Gauge.builder("my.stock", orderService, new java.util.function.ToDoubleFunction<OrderService>() {
                    @Override
                    public double applyAsDouble(OrderService service) {
                        log.info("stock gauge call");
                        return service.getStock().get();
                    }
                }).register(registry);
            }
        };
    }

}

/**
 * 람다 표현식은 Java 8부터 도입된 기능으로, 익명 함수를 생성하는 간결한 방법입니다.
 * 람다 표현식은 매개변수 목록, 화살표(`->`), 그리고 본문으로 구성됩니다.
 *
 * 아래의 익명 클래스를 람다 표현식으로 축약하는 과정을 살펴보겠습니다:
 *
 * ```java
 * new MeterBinder() {
 *     @Override
 *     public void bindTo(MeterRegistry registry) {
 *         Gauge.builder("my.stock", orderService, new java.util.function.ToDoubleFunction<OrderService>() {
 *             @Override
 *             public double applyAsDouble(OrderService service) {
 *                 log.info("stock gauge call");
 *                 return service.getStock().get();
 *             }
 *         }).register(registry);
 *     }
 * };
 * ```
 *
 * 1. 먼저, `MeterBinder` 인터페이스의 익명 클래스 구현을 람다 표현식으로 변환합니다.
 * `MeterBinder` 인터페이스는 `void bindTo(MeterRegistry registry)`라는 단일 메소드를 가지고 있으므로,
 * 이 메소드의 구현을 람다 표현식의 본문으로 사용합니다.
 * 매개변수 `registry`는 람다 표현식의 매개변수 목록으로 사용됩니다. 이를 통해 아래와 같이 변환할 수 있습니다:
 *
 * ```java
 * registry -> Gauge.builder("my.stock", orderService, new java.util.function.ToDoubleFunction<OrderService>() {
 *     @Override
 *     public double applyAsDouble(OrderService service) {
 *         log.info("stock gauge call");
 *         return service.getStock().get();
 *     }
 * }).register(registry);
 * ```
 *
 * 2. 다음으로, `java.util.function.ToDoubleFunction` 인터페이스의 익명 클래스 구현을 람다 표현식으로 변환합니다.
 * `ToDoubleFunction` 인터페이스는 `double applyAsDouble(T value)`라는 단일 메소드를 가지고 있으므로,
 * 이 메소드의 구현을 람다 표현식의 본문으로 사용합니다.
 * 매개변수 `service`는 람다 표현식의 매개변수 목록으로 사용됩니다. 이를 통해 아래와 같이 변환할 수 있습니다:
 *
 * ```java
 * registry -> Gauge.builder("my.stock", orderService, service -> {
 *     log.info("stock gauge call");
 *     return service.getStock().get();
 * }).register(registry);
 * ```
 *
 * 이렇게 두 단계를 거쳐 익명 클래스를 람다 표현식으로 축약하였습니다.
 * 이렇게 람다 표현식을 사용하면 코드가 간결해지고, 함수형 프로그래밍 스타일을 적용할 수 있습니다.
 */