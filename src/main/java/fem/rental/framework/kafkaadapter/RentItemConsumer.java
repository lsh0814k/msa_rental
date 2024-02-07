package fem.rental.framework.kafkaadapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fem.rental.application.usecase.CompensationRentItemUsecase;
import fem.rental.domain.model.event.EventRentResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentItemConsumer {
    private static final String TOPIC = "TOPIC_RENT_RESULT";
    private final ObjectMapper objectMapper;
    private final CompensationRentItemUsecase compensationRentItemUsecase;

    @KafkaListener(topics = TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void consumeRent(ConsumerRecord<String, String> record) {
        try {
            EventRentResult eventRentResult = objectMapper.readValue(record.value(), EventRentResult.class);
            if (!eventRentResult.isSuccess()) {
                log.info("대여 취소 보상 트랜잭션 실행");
                compensationRentItemUsecase.cancelRentItem(eventRentResult.getIdName(), eventRentResult.getItem(), eventRentResult.getPoint());
            }
        }  catch (JsonProcessingException e) {
            log.error("json 변환 에러 : ", e);
            throw new IllegalStateException("json 변환 오류가 발생했습니다.");
        }
    }

}
