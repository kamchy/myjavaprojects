package pl.chyla.watcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.SwingUtilities;

import pl.chyla.watcher.Watcher.EventInfo;

/** Main entry point to the app - starts watcher thread and gui thread
 * @author karma
 *
 */
public class DirectoryWatcher {

  public DirectoryWatcher(String watchedDirectoryName) {
    dirName = watchedDirectoryName;
  }

  /**
   * Executes watcher for given directory and displays
   * watcher messages either in Swing window (if second commandline
   * parameter equals "true") or in stdout.
   *
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    WatcherProgramParams parameters = getProgramParameters(args);
    DirectoryWatcher watcher = new DirectoryWatcher(parameters.getWatchedDirectory());
    watcher.startWatching(parameters.shouldDisplayGui());
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
    Path dir = Paths.get(dirName);
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
   * Parses program parameters. Expects to get directory name and - optionally -
   * boolean value denoting if Swing gui should be displayed
   *
   * @param args
   * @return
   */
  private static WatcherProgramParams getProgramParameters(String[] args) {
    if (args.length < 1)  {
      System.out.println(USAGE);
      System.exit(0);
    }
    String watchedDir = args[0];
    boolean shouldDisplayGui = (args.length > 1) && ("true".equalsIgnoreCase(args[1]));

    return new WatcherProgramParams(watchedDir, shouldDisplayGui);
  }


  /**
   * Program usage message.
   */
  private static final String USAGE = "Usage: java DirectoryWatcher watched_dir [true]\n Optional boolean " +
      "parameter denotes if graphical gui should be run.\nIf this valie is not \"true\" or is not " +
      "specified, messages are printed to standard output.";


  private BlockingQueue<Watcher.EventInfo> eventInfoQueue = new LinkedBlockingQueue<>();
  private StringListModel stringListModel  = new StringListModel();
  private String dirName;


}
