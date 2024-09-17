package ru.hogwarts.school_.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school_.service.ThreadService;

import java.util.stream.Stream;

@RestController
@RequestMapping("/auxiliary")
public class AuxiliaryController {

    private ThreadService threadService;

    public AuxiliaryController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping
    public Integer streamExperiment() {
        long start = System.currentTimeMillis();

        int sum = Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel() // не ускоряет
                .reduce(0, (a, b) -> a + b);

        long finish = System.currentTimeMillis();
        System.out.println(finish - start);

        return sum;

    }

    @GetMapping("/threadOne")
    public void threadExperimentOne() {
        threadService.threadOne();

    }

    @GetMapping("/threadTwo")
    public void threadExperimentTwo() {
        threadService.threadTwo();

    }


}