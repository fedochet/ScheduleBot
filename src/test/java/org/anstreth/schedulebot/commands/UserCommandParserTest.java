package org.anstreth.schedulebot.commands;

import org.junit.Test;

import static org.anstreth.schedulebot.commands.UserCommand.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserCommandParserTest {

    private UserCommandParser parser = new UserCommandParser();

    @Test
    public void parserCorrectlyParsesTomorrowCommand() throws Exception {
        assertThat(parser.parse("/tomorrow"), is(TOMORROW));
        assertThat(parser.parse("Tomorrow"), is(TOMORROW));
    }

    @Test
    public void parserCorrectlyParsesTodayCommand() throws Exception {
        assertThat(parser.parse("/today"), is(TODAY));
        assertThat(parser.parse("Today"), is(TODAY));
    }

    @Test
    public void parserCorrectlyParsesWeekCommand() throws Exception {
        assertThat(parser.parse("/week"), is(WEEK));
        assertThat(parser.parse("Week"), is(WEEK));
    }

    @Test
    public void parserCorrectlyParsesMenuCommand() throws Exception {
        assertThat(parser.parse("/menu"), is(MENU));
        assertThat(parser.parse("Menu"), is(MENU));
    }

    @Test
    public void ifCommandIsWeirdThen_UNKNOWN_IsReturned() throws Exception {
        assertThat(parser.parse("testtest"), is(UNKNOWN));
    }

    @Test
    public void commandWithNotTrimmedSpacesIsCorrectlyParsed() throws Exception {
        assertThat(parser.parse("  /today  "), is(TODAY));
    }
}
