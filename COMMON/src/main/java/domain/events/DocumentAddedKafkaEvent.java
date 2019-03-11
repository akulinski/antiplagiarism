package domain.events;

import domain.DocumentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentAddedKafkaEvent {
    DocumentDTO documentDTO;
    byte[] documentData;

    @Override
    public String toString() {
        return "DocumentAddedKafkaEvent{" +
                "documentDTO=" + documentDTO +
                ", documentData=" + Arrays.toString(documentData) +
                '}';
    }
}
