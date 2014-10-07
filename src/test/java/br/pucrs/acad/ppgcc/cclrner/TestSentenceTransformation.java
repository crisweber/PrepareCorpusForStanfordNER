package br.pucrs.acad.ppgcc.cclrner;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by cristoferweber on 02/05/14.
 */
public class TestSentenceTransformation {

    private NLPStringTokenizer tokenizer;

    @Before
    public void setup() {
        tokenizer = new NLPStringTokenizer();
    }

    @Test
    public void mathEquationBecomesO() {
        String input = "{ something_i } will become O .";
        String expected = "something\tO\nwill\tO\nbecome\tO\nO\tO\n.\tO\n";
        String current = tokenizer.split(input);
        Assert.assertEquals(expected, current);
    }

    @Test
    public void testSentenceSplitting() {
        String input = "A sentence with {one_LINK composed_LINK link_LINK} followed by { another_LINK } .";
        String expected = "A\tO\nsentence\tO\nwith\tO\none\tLINK\ncomposed\tLINK\nlink\tLINK\nfollowed\tO\nby\tO\nanother\tLINK\n.\tO\n";
        String current = tokenizer.split(input);
        Assert.assertEquals(expected, current);
    }


}
