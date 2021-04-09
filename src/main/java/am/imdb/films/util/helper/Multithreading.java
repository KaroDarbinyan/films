package am.imdb.films.util.helper;

import am.imdb.films.util.parser.ImdbParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Service
public class Multithreading {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private Path path;
    private Map<Long, String> longStringMap;

    public Multithreading(Path path, Map<Long, String> longStringMap) {
        this.path = path;
        this.longStringMap = longStringMap;
    }

    public void parallelExec() throws Exception {
        Map<Long, String> longStringMap = ImdbParser.parseHtml(this.longStringMap);


        List<CompletableFuture<Long>> futures = new ArrayList<>();
        List<Long> numbers = Arrays.asList(1L, 2L, 3L);
        // From logs you can notice that tasks are exec in async mode.
        // Same way you can parse your csv parts parallel or upload images
        for (Long number : numbers) {
            futures.add(getFutureTask(number));
        }

        CompletableFuture[] completableFutures = futures.toArray(new CompletableFuture[0]);
        CompletableFuture.allOf(completableFutures);

        // Here I get all completed tasks, tho its not necessary to return tasks
        // You can do whatever is needed inside getFutureTasks() and do not return anything, its up to you
        for (CompletableFuture completableFuture : completableFutures) {
            try {
                System.out.println(String.format("Number retrieved from thread %s", completableFuture.get()));
            } catch (InterruptedException | ExecutionException ignored) {
            }
        }
    }

    private CompletableFuture<Long> getFutureTask(Long number) {
        return CompletableFuture.supplyAsync(() -> {
//            number.downloadFile();
            return number;
        }, executorService);
    }
}
