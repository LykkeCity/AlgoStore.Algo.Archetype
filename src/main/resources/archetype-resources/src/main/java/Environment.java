#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Environment {

    static Properties properties;
    public static void load() {


        String propertyFileName="hft.properties";

        String propertyFilePath = System.getenv().get("ALGO_PROPERTIES");

        try  {
            InputStream is;
            //Try to load properties from resources
            is = Algo.class.getClassLoader().getResourceAsStream(propertyFileName);
            if (is==null){
                //Try to load from a file which name is in system environment variable ALGO_PROPERTIES
                is = new FileInputStream(propertyFilePath);
            }
            properties = new Properties();
            properties.load(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getVariable(String variable) {
        return properties.getProperty(variable);
    }
}