package org.midokura;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class SeatingManager {

  private Map<Table, CustomerGroup> tables;
  // Queue to store the groups waiting to be seated
  private Queue<CustomerGroup> waitingGroups;

  /* Constructor */
  public SeatingManager(List<Table> tables) {
    this.tables = new HashMap<>();
    waitingGroups = new ArrayDeque<>();
    for (Table table : tables) {
      this.tables.put(table, null);
    }
  }

  /**
   * Group arrives and wants to be seated. The worst case time complexity of this function is O(n)
   * bc we only loop once Space complexity is constant as we are not adding any data structure to
   * help with computation
   */
  public void arrives(CustomerGroup group) {
    if (group.size > 6 || group.size < 2) {
      return;
    }
    Table optimalTable = null;
    int minEmptySeats = Integer.MAX_VALUE;
    for (Table table : tables.keySet()) {
      if (table.size >= group.size && tables.get(table) == null
          && table.size - group.size < minEmptySeats) {
        optimalTable = table;
        minEmptySeats = table.size - group.size;
      }
    }
    if (optimalTable != null) {
      tables.put(optimalTable, group);
    } else {
      waitingGroups.add(group);
    }
  }

  /**
   * Whether seated or not, the group leaves the restaurant. Time complexity is O(n) here since
   * removing the group requires us to read all the values
   */
  public void leaves(CustomerGroup group) {
    tables.values().remove(group);
    waitingGroups.remove(group);
  }

  /**
   * Return the table at which the group is seated, or null if they are not seated (whether they're
   * waiting or already left). Time complexity is again O(n) here
   */
  public Table locate(CustomerGroup group) {
    return tables.entrySet()
        .stream()
        .filter(entry -> entry.getValue() == group)
        .map(Map.Entry::getKey)
        .findFirst()
        .orElse(null);
  }

  public Map<Table, CustomerGroup> getTables() {
    return tables;
  }

  public Queue<CustomerGroup> getWaitingGroups() {
    return waitingGroups;
  }

}
