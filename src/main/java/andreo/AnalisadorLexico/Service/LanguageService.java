package andreo.AnalisadorLexico.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LinesService linesService = new LinesService();

    public String tester(String text) {
        var lines = linesService.lines(text);
        List<String> wordsList = new ArrayList<>();
        List<String> errorsList = new ArrayList<>();
        List<String> commentsList = new ArrayList<>();
        List<String> numberIntList = new ArrayList<>();
        List<String> numberFloatList = new ArrayList<>();
        List<String> identifierList = new ArrayList<>();
        String retorno = "Tokens de Entrada\n";
        String erros = "";
        int inteiros = 0;
        int identifier = 0;
        int floats = 0;

        for (int i = 0; i < lines.length; i++) {
            if (comment(lines[i])) {
                commentsList.add(lines[i]);
                retorno += "[" + (i + 1) + "] COMENTARIO\n";
            } else {
                if (identifier(lines[i])) {
                    if (!identifierList.contains(lines[i])) {
                        identifierList.add(lines[i]);
                        identifier++;
                    }
                    retorno += "[" + (i + 1) + "] IDENTIFICADOR " + identifier + "\n";
                } else if (reservedWord(lines[i])) {
                    wordsList.add(lines[i]);
                    retorno += "[" + (i + 1) + "] " + lines[i].toUpperCase() + "\n";
                } else if (numberInt(lines[i])) {
                    numberIntList.add(lines[i]);
                    inteiros++;
                    retorno += "[" + (i + 1) + "] NUMERO INTEIRO " + inteiros + "\n";
                } else if (numberFloat(lines[i])) {
                    numberFloatList.add(lines[i]);
                    floats++;
                    retorno += "[" + (i + 1) + "] NUMERO REAL " + floats + "\n";
                } else {
                    errorsList.add(lines[i]);
                    if (!erros.contains((i + 1) + " (" + lines[i] + ") \n"))
                        erros += (i + 1) + " (" + lines[i] + ") \n";
                }
            }
        }
        retorno += "\nErros nas linhas\n" + erros;
        return retorno;
    }

    public Boolean comment(String text) {
        return text.charAt(0) == '/' && text.charAt(1) == '/';
    }

    public Boolean numberInt(String text) {

        String regex = "[0-9]{2}";

        Pattern p = Pattern.compile(regex);

        if (text == null) {
            return false;
        }

        Matcher m = p.matcher(text);

        return m.matches();
    }

    public Boolean numberFloat(String text) {

        String regex = "[0-9]{2}.[0-9]{2}";

        Pattern p = Pattern.compile(regex);

        if (text == null) {
            return false;
        }
        if (text.contains(",")) {
            return false;
        }

        Matcher m = p.matcher(text);

        return m.matches();
    }

    public Boolean identifier(String text) {

        String regex = "^[_a-z]\\w*$";

        Pattern p = Pattern.compile(regex);

        if (text == null) {
            return false;
        }

        Matcher m = p.matcher(text);

        return m.matches();
    }

    public Boolean reservedWord(String text) {
        return text.equals("int") ||
                text.equals("float") ||
                text.equals("char") ||
                text.equals("break") ||
                text.equals("case") ||
                text.equals("const") ||
                text.equals("continue");
    }

    public String fileChecker(MultipartFile file) throws IOException {
        String text = "";
        Scanner sc = new Scanner(convertMultiPartToFile(file));

        while (sc.hasNextLine())
            text += (sc.nextLine()) + "\n";

        return text;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}