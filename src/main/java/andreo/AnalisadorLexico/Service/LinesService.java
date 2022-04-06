package andreo.AnalisadorLexico.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter

public class LinesService {

    public String[] lines(String text) {
        var lines = text.split("\n");

        return lines;
    }
}
