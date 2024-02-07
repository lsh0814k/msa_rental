package fem.rental.framework.kafkaadapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fem.rental.application.usecase.CompensationReturnItemUsecase;
import fem.rental.domain.model.event.EventReturnResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReturnItemConsumer {
    private static final String TOPIC = "TOPIC_RETURN_RESULT";
    private final ObjectMapper objectMapper;
    private final CompensationReturnItemUsecase compensationReturnItemUsecase;

    @KafkaListener(topics = TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void consumeReturn(ConsumerRecord<String, String> record) {
        try {
            EventReturnResult eventReturnResult = objectMapper.readValue(record.value(), EventReturnResult.class);
            if (!eventReturnResult.isSuccess()) {
                log.info("반납 취소 보상 트랜잭션 실행");
                compensationReturnItemUsecase.cancelReturnItem(eventReturnResult.getIdName(), eventReturnResult.getItem(), eventReturnResult.getPoint());
            }
        }  catch (JsonProcessingException e) {
            log.error("json 변환 에러 : ", e);
            throw new IllegalStateException("json 변환 오류가 발생했습니다.");
        }
    }
}
