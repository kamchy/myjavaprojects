package pl.chyla.watcher;

import java.io.PrintStream;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Listener that prints to given stream
 * @author karma
 *
 */
final class StreamPrintingListener implements ListDataListener {

  private PrintStream stream;

  StreamPrintingListener (PrintStream stream) {
    this.stream = stream;
  }

  @Override
  public void intervalRemoved(ListDataEvent e) {

  }

  @Override
  public void intervalAdded(ListDataEvent e) {

  }

  @Override
  public void contentsChanged(ListDataEvent e) {
    if (!(e.getSource() instanceof StringListModel)) {
      return;
    }
    StringListModel model = (StringListModel) e.getSource();
    String data = model.getElementAt(e.getIndex0());
    stream.println(data);
  }
}