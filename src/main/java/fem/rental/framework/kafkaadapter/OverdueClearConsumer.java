package fem.rental.framework.kafkaadapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fem.rental.application.usecase.CompensationClearOverdueItemUsecase;
import fem.rental.domain.model.event.EventOverdueClearResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OverdueClearConsumer {
    private static final String TOPIC = "TOPIC_OVERDUE_CLEAR_RESULT";
    private final ObjectMapper objectMapper;
    private final CompensationClearOverdueItemUsecase compensationClearOverdueItemUsecase;

    @KafkaListener(topics = TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            EventOverdueClearResult eventOverdueClearResult = objectMapper.readValue(record.value(), EventOverdueClearResult.class);
            if (!eventOverdueClearResult.isSuccess()) {
                compensationClearOverdueItemUsecase.cancelMakeAvailableRental(eventOverdueClearResult.getIdName(), eventOverdueClearResult.getPoint());
            }
        }  catch (JsonProcessingException e) {
            log.error("json 변환 에러 : ", e);
            throw new IllegalStateException("json 변환 오류가 발생했습니다.");
        }
    }
}
