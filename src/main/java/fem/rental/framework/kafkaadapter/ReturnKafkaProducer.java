package fem.rental.framework.kafkaadapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fem.rental.application.outputport.EventReturnOutputPort;
import fem.rental.domain.model.event.ItemReturned;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReturnKafkaProducer implements EventReturnOutputPort {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC = "TOPIC_RETURN";

    @Override
    public void occurReturnEvent(ItemReturned itemReturned) {
        try {
            kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(itemReturned));
        } catch (JsonProcessingException e) {
            log.error("json 변환 에러 : ", e);
            throw new IllegalStateException(e);
        }
    }
}
