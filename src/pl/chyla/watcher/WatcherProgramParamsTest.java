package pl.chyla.watcher;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class WatcherProgramParamsTest {

  private File testDir;
  private String testDirName = "testDirName";

  @Before
  public void setUp() {
    testDir = new File(testDirName);
  }
  @Test(expected = NullPointerException.class)
  public void testNullDirInvalid() {
    WatcherProgramParams params = new WatcherProgramParams(null, new Boolean(true));
    assertFalse(params.areCorrect());
  }

  @Test
  public void testNullGuiInvalid() {
    WatcherProgramParams params = new WatcherProgramParams(testDirName, null);
    assertFalse(params.areCorrect());
  }

  @Test
  public void testDirRetrieved() {
    Boolean testGui = new Boolean(true);
    WatcherProgramParams params = new WatcherProgramParams(testDirName , testGui);
    assertThat(params.getWatchedDirectory(), equalTo(testDir));
  }

  @Test
  public void testGuiOptionRetrieved() {
    Boolean testGui = new Boolean(true);
    WatcherProgramParams params = new WatcherProgramParams(testDirName , testGui);
    assertThat(params.shouldDisplayGui(), equalTo(testGui));
  }

}
