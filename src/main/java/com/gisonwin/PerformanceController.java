package com.gisonwin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/16 13:35
 */
@RestController
public class PerformanceController {
    @GetMapping("test")
    public String test() {
        var now = LocalDateTime.now().toString();
        System.out.println("now==" + now);
        return now;
    }
}
