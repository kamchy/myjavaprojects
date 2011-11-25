package pl.chyla.watcher;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class WatcherProgramParamsTest {

  private String testDirName = "testDirName";

  @Test
  public void testNullDir() {
    WatcherProgramParams params = new WatcherProgramParams(null, new Boolean(true));
    assertNull(params.getWatchedDirectory());
  }

  @Test
  public void testNullGuiIsFalse() {
    WatcherProgramParams params = new WatcherProgramParams(testDirName, null);
    assertNull(params.shouldDisplayGui());
  }

  @Test
  public void testDirRetrieved() {
    Boolean testGui = new Boolean(true);
    WatcherProgramParams params = new WatcherProgramParams(testDirName , testGui);
    assertThat(params.getWatchedDirectory(), equalTo(testDirName));
  }

  @Test
  public void testGuiOptionTrue() {
    WatcherProgramParams params = new WatcherProgramParams(testDirName , true);
    assertTrue(params.shouldDisplayGui());
  }

  @Test
  public void testGuiOptionFalse() {
    WatcherProgramParams params = new WatcherProgramParams(testDirName , false);
    assertFalse(params.shouldDisplayGui());
  }

}
