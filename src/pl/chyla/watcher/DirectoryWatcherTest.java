package pl.chyla.watcher;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DirectoryWatcherTest {

  private File testDir;
  private String testDirString = getSomeExistingDirName();


  @Before
  public void setup() {
    testDir = new File(testDirString);
  }

  private String getSomeExistingDirName() {
    return System.getProperty("user.dir");
  }

  @Test(expected = NullPointerException.class)
  public void testNoCommandlineParams() {
    String[] paramArray = new String[]{};
    WatcherProgramParams watcherParams = DirectoryWatcher.getProgramParameters(paramArray);

    assertThat(watcherParams, notNullValue());
  }

  @Test
  public void testOneDirCommandlineParam() {

    String[] paramArray = new String[]{testDirString};
    WatcherProgramParams watcherParams = DirectoryWatcher.getProgramParameters(paramArray);

    assertTrue(watcherParams.areCorrect());
    assertThat(watcherParams.getWatchedDirectory(), equalTo(testDir));
  }


  @Test
  public void testOneDirAndGuiShouldBeDisplayed() {
    String[] paramArray = new String[]{testDirString, Boolean.toString(true)};
    WatcherProgramParams watcherParams = DirectoryWatcher.getProgramParameters(paramArray);

    assertTrue(watcherParams.areCorrect());
    assertTrue(watcherParams.shouldDisplayGui());
  }

  @Test
  public void testOneDirAndGuiShouldNotBeDisplayed() {
    String[] paramArray = new String[]{testDirString, Boolean.toString(false)};
    WatcherProgramParams watcherParams = DirectoryWatcher.getProgramParameters(paramArray);

    assertTrue(watcherParams.areCorrect());
    assertTrue(! watcherParams.shouldDisplayGui());
  }


}
