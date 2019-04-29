import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NodeTransMap.
 *
 * @author liguoyao
 */
public class NodeTransMap {
    public static ConcurrentHashMap<HostPortDto, HostPortDto> hashMap = new ConcurrentHashMap();

    public static void main(String args[]) throws ClassNotFoundException {
        //        init();
        //        BufferedReader reader = null;
        //        try {
        //            URL url = Class.forName("NodeTransMap").getResource("nodeMapTxt.csv");
        //            reader = new BufferedReader(new URLReader(url));
        //            String line;
        //            while ((line = reader.readLine()) != null) {
        //                if (line.startsWith("#")) {
        //                    continue;
        //                }
        //                String[] element = line.split(",");
        //                Preconditions.checkState(element.length == 4, "data error");
        //                hashMap.put(new HostPortDto(element[0], element[1]), new HostPortDto(element[2], element[3]));
        //            }
        //            for (Map.Entry<HostPortDto, HostPortDto> entry : hashMap.entrySet()) {
        //                System.out.println(entry.getKey() + "  =>  " + entry.getValue());
        //            }
        //        } catch (ClassNotFoundException e) {
        //            e.printStackTrace();
        //        } catch (MalformedURLException e) {
        //            e.printStackTrace();
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        } finally {
        //            if (null != reader) {
        //                try {
        //                    reader.close();
        //                } catch (IOException e) {
        //                    e.printStackTrace();
        //                }
        //            }
        //        }
        System.out.println(NodeTransMap.class.getClassLoader());
        System.out.println(HostPortDto.class.getClassLoader().loadClass("com.A").getClassLoader());
    }

    public static void init() {
        try {
            String fileName = Class.forName("NodeTransMap").getResource("switch.txt").getFile();
            InputStream inputStream = new FileInputStream(fileName);
            Properties properties = new Properties();
            properties.load(inputStream);
            System.out.println(properties.getProperty("switch"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
