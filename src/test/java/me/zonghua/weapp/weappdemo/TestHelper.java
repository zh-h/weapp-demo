package me.zonghua.weapp.weappdemo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestHelper {
    public static String readTestContent(String relativePath) throws IOException, URISyntaxException {
        ClassLoader classLoader = TestHelper.class.getClassLoader();
        URL resource = classLoader.getResource(relativePath);
        Path path = Paths.get(resource.toURI());
        byte[] data = Files.readAllBytes(path);
        return new String(data, StandardCharsets.UTF_8);
    }
}
