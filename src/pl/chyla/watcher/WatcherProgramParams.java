package pl.chyla.watcher;

import pl.chyla.util.Pair;

public class WatcherProgramParams extends Pair<String, Boolean> {

  public WatcherProgramParams(String first, Boolean second) {
    super(first, second);
  }

  public String getWatchedDirectory() {
    return getFirst();
  }

  public boolean shouldDisplayGui() {
    return getSecond();
  }

}
