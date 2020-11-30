package stackoverflow.Junittest;

import static org.junit.Assert.assertTrue;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class TestUI {
	@Test
	public void testUI() {
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		SWTBotShell[] shells = bot.shells();
		boolean found = false;
		for (int i = 0; i < shells.length && !found; i++) {
			if (shells[i].isVisible()) {
				if (shells[i].getText().contains("Eclipse")) {
					found = true;
					System.out.println(i);
				}
			}
		}
		assertTrue(found);
	}
	
	@Test
	public void createProject() {
	 SWTWorkbenchBot bot = new SWTWorkbenchBot();
	 bot.menu("File").menu("Project...").click();
	 SWTBotShell shell = bot.shell("New Project");
	 shell.activate();
	 bot.tree().expandNode("General").select("Project");
	 bot.button("Next >").click();
	 bot.textWithLabel("Project name:").
	 setText("SWTBot Test Project");
	 bot.button("Finish").click();
	}
	
}
