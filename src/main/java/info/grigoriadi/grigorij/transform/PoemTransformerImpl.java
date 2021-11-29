package info.grigoriadi.grigorij.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PoemTransformerImpl implements PoemTransformer{

    private ObjectMapper mapper = new ObjectMapper();

    private String jsonOutPath;

    private String htmlInPath;

    @Override
    public void transformPoem(String name) {
        String content = null;
        PoemContext context = new PoemContext();
        try (FileInputStream fis = new FileInputStream(htmlInPath + File.separator + "poem" + File.separator + name + ".txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                context.readLine(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file " + name, e);
        }

        File file = new File(jsonOutPath + File.separator + "poem" + File.separator + name + ".json");
        try {
            boolean created = file.createNewFile();
            mapper.writeValue(file, context.getChapter());
        } catch (IOException e) {
            throw new RuntimeException("Can't write file", e);
        }
    }

    @Value("${jsonOutPath}")
    public void setJsonOutPath(String jsonOutPath) {
        this.jsonOutPath = jsonOutPath;
    }

    @Value("${htmlInPath}")
    public void setHtmlInPath(String htmlInPath) {
        this.htmlInPath = htmlInPath;
    }
}
