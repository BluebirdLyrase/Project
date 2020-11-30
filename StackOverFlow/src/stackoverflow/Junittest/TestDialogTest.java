package stackoverflow.Junittest;
import static org.junit.Assert.assertTrue;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.
SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(SWTBotJunit4ClassRunner.class)
public class TestDialogTest {

	@Test
	public void test() {
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		 SWTBotShell[] shells = bot.shells();
		 boolean found = false;
		 for (int i = 0; i < shells.length && !found; i++) {
		 if (shells[i].isVisible()) {
		 if (shells[i].getText().contains("Eclipse")) {
		 found = true;
		 }
		 }
		 }
		 assertTrue(found);
		 }
	}


