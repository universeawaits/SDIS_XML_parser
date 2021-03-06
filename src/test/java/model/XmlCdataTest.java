package model;

import org.junit.Test;
import parser.ElementFactory;
import static org.junit.Assert.*;

public class XmlCdataTest {
    @Test(expected = IllegalArgumentException.class)
    public void negativeTestOnNotCdata() {
        String inputData = "<![CDATA[/n/tWithin this Character Data block I can/n/t" +
                "use double dashes as much as I want (along with <, &, ', and \")" +
                "\n\t*and* %MyParamEntity; will be expanded to the text" +
                "\n\t\"Has been expanded\" ... however, I can't use" +
                "\n\tthe CEND sequence. If I need to use CEND I must escape one of the" +
                "\n\tbrackets or the greater-than sign using concatenated CDATA sections.";

        XmlElement cdata = (XmlElement) ElementFactory.XmlCdataFactory.getInstance(inputData, null);
    }

   @Test
    public void positiveTest() {
        String inputData = "<![CDATA[\n" +
                "\tWithin this Character Data block I can\n" +
                "\tuse double dashes as much as I want (along with <, &, ', and \")\n" +
                "\t*and* %MyParamEntity; will be expanded to the text\n" +
                "\t\"Has been expanded\" ... however, I can't use\n" +
                "\tthe CEND sequence. If I need to use CEND I must escape one of the\n" +
                "\tbrackets or the greater-than sign using concatenated CDATA sections.\n" +
                "]]>";
        String expected = "\n\tWithin this Character Data block I can\n" +
                "\tuse double dashes as much as I want (along with <, &, ', and \")\n" +
                "\t*and* %MyParamEntity; will be expanded to the text\n" +
                "\t\"Has been expanded\" ... however, I can't use\n" +
                "\tthe CEND sequence. If I need to use CEND I must escape one of the\n" +
                "\tbrackets or the greater-than sign using concatenated CDATA sections.\n";
        XmlElement cdata = (XmlElement) ElementFactory.XmlCdataFactory.getInstance(inputData, null);

        assertNull(cdata.getParent());
        assertEquals(inputData, cdata.toString());
        assertEquals(expected, cdata.getData());
    }

    @Test
    public void positiveTestOnDeletingOfElement() {
        String inputData = "<![CDATA[\n" +
                "\tWithin this Character Data block I can\n" +
                "\tuse double dashes as much as I want (along with <, &, ', and \")\n" +
                "\t*and* %MyParamEntity; will be expanded to the text\n" +
                "\t\"Has been expanded\" ... however, I can't use\n" +
                "\tthe CEND sequence. If I need to use CEND I must escape one of the\n" +
                "\tbrackets or the greater-than sign using concatenated CDATA sections.\n" +
                "]]> <!-- test -->";
        String expected = "<!-- test -->";

        String result = ElementFactory.XmlCdataFactory.deleteElementFromContent(inputData);

        assertEquals(expected, result);
    }
}