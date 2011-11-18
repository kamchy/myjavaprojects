package pl.chyla.watcher;

import java.util.concurrent.BlockingQueue;

import javax.swing.SwingUtilities;

public class ModelUpdater<T> implements Runnable{

  private BlockingQueue<T> eventInfoQueue;
  private StringListModel stringListModel;

  public ModelUpdater(StringListModel stringListModel,
      BlockingQueue<T> eventInfoQueue) {
    this.stringListModel = stringListModel;
    this.eventInfoQueue = eventInfoQueue;
  }

  /**
   * Runnable that updates model with given string
   * @author karma
   *
   */
  private static final class Updater<P> implements Runnable {
    private String param;
    private StringListModel model;

    Updater(P param, StringListModel model) {
      this.param = param.toString();
      this.model = model;
    }
    @Override
    public void run() {
      model.addString(param);
    }

  }

  @Override
  public void run() {
    T info = null;
    while (true){
      try {
        info = eventInfoQueue.take();
      } catch (InterruptedException e) {
        e.printStackTrace();
        break;
      }
      SwingUtilities.invokeLater(new Updater<T>(info, stringListModel));
    }
  }


}
