package pl.chyla.watcher;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;

import javax.swing.*;

import pl.chyla.watcher.Watcher.EventInfo;

/** Main entry point to the app - starts watcher thread and gui thread
 * @author karma
 *
 */
public class DirectoryWatcher {

  public DirectoryWatcher(String watchedDirectory) {
    directoryName = watchedDirectory;
  }

  /**
   * Executes watcher for given directoryName and displays
   * watcher messages either in Swing window (if second commandline
   * parameter equals "true") or in stdout.
   *
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    WatcherProgramParams parameters = getProgramParameters(args);
    if (!areCorrect(parameters)) {
      printUsageAndExit();
    }
    DirectoryWatcher watcher = new DirectoryWatcher(parameters.getWatchedDirectory());
    watcher.startWatching(parameters.shouldDisplayGui());
  }


  private static boolean areCorrect(WatcherProgramParams parameters) {
    String dirName = parameters.getWatchedDirectory();
    return dirName != null && new File(dirName).isDirectory();
  }

  private static void printUsageAndExit() {
    System.out.println(USAGE);
    System.exit(0);
  }

  private void startWatching(boolean shouldDisplayGui) throws IOException {
    if (shouldDisplayGui){
      prepareGui();
    } else {
      prepareStdout();
    }
    startWatcherThread();
    startUpdaterThread();
  }

  private void startUpdaterThread() {
    new Thread(new ModelUpdater<EventInfo>(stringListModel, eventInfoQueue)).start();
  }

  private void startWatcherThread() throws IOException {
    Path dir = Paths.get(directoryName);
    Watcher w = new Watcher(dir, true, eventInfoQueue);
    new Thread(w).start();
  }

  private void prepareStdout() {
    stringListModel.addListDataListener(new StreamPrintingListener(System.out));
  }


  private void prepareGui() {
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        new MainFrame<String>(stringListModel).setVisible(true);

      }
    });

  }


  /**
   * Parses program parameters. Expects to get directoryName name and - optionally -
   * boolean value denoting if Swing gui should be displayed. For gui
   * to be shown, argument value should equal "true".
   *
   * @param args
   * @return
   */
  static WatcherProgramParams getProgramParameters(String[] args) {
    String watchedDir = null;
    Boolean shouldDisplayGui = Boolean.valueOf(false);
    if (args.length >= 1)  {
      watchedDir = args[0];
      shouldDisplayGui = Boolean.valueOf(
          (args.length > 1) && ("true".equalsIgnoreCase(args[1])));
    }

    return new WatcherProgramParams(watchedDir, shouldDisplayGui);
  }


  /**
   * Program usage message.
   */
  static final String USAGE = "Usage: java DirectoryWatcher watched_dir [true]\n Optional boolean " +
      "parameter denotes if graphical gui should be run.\nIf this valie is not \"true\" or is not " +
      "specified, messages are printed to standard output.";


  private BlockingQueue<Watcher.EventInfo> eventInfoQueue = new LinkedBlockingQueue<>();
  private StringListModel stringListModel  = new StringListModel();
  private String directoryName;


}
