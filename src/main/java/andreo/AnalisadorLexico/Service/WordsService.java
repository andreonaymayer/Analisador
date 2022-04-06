package andreo.AnalisadorLexico.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class WordsService {

    public String[] words (String line){
        var words = line.split(" ");

        return words;
    }
}
