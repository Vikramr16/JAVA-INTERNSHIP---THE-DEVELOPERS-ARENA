package com.example.multienv.controller;
import com.example.multienv.AppProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
@RestController
public class RootController {
    private final AppProperties props;
    private final Environment env;
    public RootController(AppProperties props, Environment env) {
        this.props = props;
        this.env = env;
    }
    @GetMapping("/")
    public Map<String, Object> root() {
        String[] profiles = env.getActiveProfiles();
        return Map.of(
            "message", props.getMessage(),
            "activeProfiles", profiles.length == 0 ? new String[]{"default"} : profiles,
            "activeMessage", props.getActiveMessage()
        );
    }
}
