package hotel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DbLoader {
    private String url;
    private String username;
    private String password;

    public DbLoader(String url, String username, String password)   {
        this.url = url;
        this.username = username;
        this.password = password;
        
    }

    public static DbLoader loadFromCredentialsFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            String username = null;
            String password = null;
            String url = null;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if (key.equals("url")) {
                        url = value;
                    }
                    else if (key.equals("username")){
                        username = value;
                    }
                    else if (key.equals("password")) {
                        password = value;
                    }
                }
            }
            return new DbLoader(url, username, password);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUrl() {
        return url;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }





}



