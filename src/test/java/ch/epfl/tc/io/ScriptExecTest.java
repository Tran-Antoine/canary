package ch.epfl.tc.io;

import org.junit.jupiter.api.Test;

public class ScriptExecTest {

    @Test
    public void python_script_is_successfully_executed_and_future_action_is_performed() {

        PlayCommand command = new PlayCommand("src/test/resources/test_script.py");
        System.out.println("Launching script");
        command.execute(new String[]{});
        System.out.println("Script executed");
    }
}
