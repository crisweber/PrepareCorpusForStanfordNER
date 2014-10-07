package br.pucrs.acad.ppgcc.cclrner;

import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.*;

/**
 * Created by cristoferweber on 02/05/14.
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length != 2) {
            System.out.println("Usage: java -jar PrepareCorpusForStanfordNER.jar inputWikiSentences outputStanfordFile");
            System.exit(1);
        }

        BufferedReader reader = null;
        BufferedWriter writer = null;

        int processors = Runtime.getRuntime().availableProcessors();
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        final ExecutorService executorService = new ThreadPoolExecutor(processors, processors, 1, TimeUnit.MINUTES, queue);

        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), Charset.forName("UTF-8")));
            writer = new BufferedWriter(new FileWriter(args[1]));

            String sentence;

            while((sentence = reader.readLine()) != null) {
                executorService.execute(new ConcurrentTokenizeToFile(sentence, writer));
            }

            System.out.println("Finished reading input file.");

        } catch(IOException e) {
            e.printStackTrace();
        } finally {

            while(true)
                if(queue.isEmpty())
                    break;
            System.out.println("Waiting Queue is Empty.");

            executorService.awaitTermination(5, TimeUnit.SECONDS);
            System.out.println("Finished.");

            if(reader != null) {
                reader.close();
            }
            if(writer!= null) {
                writer.close();
            }
            executorService.shutdownNow();
            System.out.println("Exiting.");
            System.exit(0);
        }
    }
}
