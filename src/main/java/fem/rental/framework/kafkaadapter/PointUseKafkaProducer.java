package fem.rental.framework.kafkaadapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fem.rental.application.outputport.EventPointUseOutputPort;
import fem.rental.domain.model.event.PointCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointUseKafkaProducer implements EventPointUseOutputPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC = "TOPIC_POINT_USE";

    @Override
    public void occurPointUseCommand(PointCommand pointCommand) {
        try {
            this.kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(pointCommand));
        } catch (JsonProcessingException e) {
            log.error("json 변환 에러 : ", e);
            throw new IllegalStateException(e);
        }
    }
}
