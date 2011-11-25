package pl.chyla.watcher;


import pl.chyla.util.*;

public class WatcherProgramParams extends Pair<String, Boolean> {


  protected WatcherProgramParams(String first, Boolean second) {
    super(first, second);
  }

  public String getWatchedDirectory() {
    return getFirst();
  }

  public Boolean shouldDisplayGui() {
    return getSecond();
  }

}
