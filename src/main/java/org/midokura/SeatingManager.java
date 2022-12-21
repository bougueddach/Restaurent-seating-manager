package org.midokura;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class SeatingManager {

  private Map<Table, CustomerGroup> tables;
  // Queue to store the groups waiting to be seated
  private Queue<CustomerGroup> waitingGroups;

  /* Constructor */
  public SeatingManager(List<Table> tables) {
    this.tables = tables.stream()
        .collect(Collectors.toMap(table -> table, null));
    waitingGroups = new LinkedList<>();
  }

  /* Group arrives and wants to be seated. */
  public void arrives(CustomerGroup group) {

  }

  /* Whether seated or not, the group leaves the restaurant. */
  public void leaves(CustomerGroup group) {

  }

  /* Return the table at which the group is seated, or null if
  they are not seated (whether they're waiting or already left). */
  public Table locate(CustomerGroup group) {
    return null;
  }
}
