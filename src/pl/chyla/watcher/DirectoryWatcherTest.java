package pl.chyla.watcher;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;
public class DirectoryWatcherTest {

  private String testDirString = "somedirname";


  @Test
  public void testEmptyCommandlineCreatesCorrectParamsObject() {
    String[] paramArray = new String[]{};
    WatcherProgramParams params = DirectoryWatcher.getProgramParameters(paramArray);

    assertThat(params, notNullValue());
    assertNull(params.getWatchedDirectory());
    assertFalse(params.shouldDisplayGui());

  }

  @Test
  public void testOneDirCommandlineParam() {
    String[] paramArray = new String[]{testDirString};
    WatcherProgramParams watcherParams = DirectoryWatcher.getProgramParameters(paramArray);

    assertThat(watcherParams.getWatchedDirectory(), equalTo(testDirString));
  }


  @Test
  public void testOneDirAndGuiShouldBeDisplayed() {
    WatcherProgramParams watcherParams = createParamsForArguments(testDirString, "true");
    assertTrue(watcherParams.shouldDisplayGui());
  }

  @Test
  public void testOneDirAndGuiShouldNotBeDisplayed() {
    WatcherProgramParams watcherParams = createParamsForArguments(testDirString, "false");
    assertThat(watcherParams.shouldDisplayGui(), is(false));
  }

  @Test
  public void testThreeParamsWithGuiOtherIgnored() {
    WatcherProgramParams params = createParamsForArguments(testDirString, "true", "additional");
    assertNotNull(params);
    assertTrue(params.shouldDisplayGui());
    assertThat(params.getWatchedDirectory(), equalTo(testDirString));
  }

  @Test
  public void testThreeParamsNoGuiOtherIgnored() {
    WatcherProgramParams params = createParamsForArguments(testDirString, "false", "additional");
    assertNotNull(params);
    assertFalse(params.shouldDisplayGui());
    assertThat(params.getWatchedDirectory(), equalTo(testDirString));
  }

  @Test
  public void testGuiParamIsCaseInsensitive() {
    WatcherProgramParams params = createParamsForArguments(testDirString, "TrUe");
    assertTrue(params.shouldDisplayGui());
  }



  private WatcherProgramParams createParamsForArguments(String... args){
      WatcherProgramParams watcherParams = DirectoryWatcher.getProgramParameters(args);
      return watcherParams;
  }


}
