package br.pucrs.acad.ppgcc.cclrner;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by cristoferweber on 02/05/14.
 */
public class ConcurrentTokenizeToFile implements Runnable {
    private final String inputSentence;
    private final Writer outWriter;

    private final static NLPStringTokenizer tokenizer = new NLPStringTokenizer();

    public ConcurrentTokenizeToFile(String inputSentence, Writer outWriter) {
        this.inputSentence = inputSentence;
        this.outWriter = outWriter;
    }

    @Override
    public void run() {
        String tokenizedString = tokenizer.split(inputSentence);
        synchronized (outWriter) {
            try {
                outWriter.write(tokenizedString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
