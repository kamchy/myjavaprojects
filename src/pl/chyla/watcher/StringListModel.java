package pl.chyla.watcher;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;


public class StringListModel extends AbstractListModel<String> {

  private static final long serialVersionUID = -7039788225297537494L;
  private List<String> stringList = new ArrayList<>();


  public StringListModel() {
    super();
  }

  @Override
  public int getSize() {
    return stringList.size();
  }

  @Override
  public String getElementAt(int index) {
    return stringList.get(index);
  }

  public void addString(String string) {
    int insterPosition = stringList.size();
    stringList.add(insterPosition, string);
    fireContentsChanged(this, insterPosition, insterPosition);
  }

}
