package br.pucrs.acad.ppgcc.cclrner;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

import java.io.StringReader;
import java.util.regex.Pattern;

/**
 * Created by cristoferweber on 02/05/14.
 */
public class NLPStringTokenizer {
    private final static Pattern isClass = Pattern.compile("\\p{Upper}{2,}");

    public String split(String input) {
        StringBuilder outSentence = new StringBuilder((int)(input.length() * 1.3));

        PTBTokenizer<CoreLabel> ptbt;
        ptbt = new PTBTokenizer<CoreLabel>(new StringReader(input), new CoreLabelTokenFactory(), "normalizeOtherBrackets=false,normalizeParentheses=false,untokenizable=allKeep");

        boolean insideBrackets = false;

        for (CoreLabel label; ptbt.hasNext(); ) {
            label = ptbt.next();
            String word = label.word().trim();

            if(word.equals("{")) {
                insideBrackets = true;

            } else if(word.equals("}")) {
                insideBrackets = false;

            } else {
                if(insideBrackets) {
                    int linkSeparator = word.lastIndexOf('_');

                    if(linkSeparator > 0) {
                        String linkedWord = word.substring(0, linkSeparator);
                        String nerClass = word.substring(linkSeparator + 1);

                        if(isClass.matcher(nerClass).matches()) {
                            outSentence.append(linkedWord).append("\t").append(nerClass).append("\n");
                        } else {
                            outSentence.append(linkedWord).append('\t').append('O').append('\n');
                        }

                    } else {
                        outSentence.append(word).append('\t').append('O').append('\n');
                    }
                } else {
                    outSentence.append(word).append('\t').append('O').append('\n');
                }
            }
        }

        return outSentence.toString();
    }
}
