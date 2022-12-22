package org.midokura;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SeatingManagerTest {

  private static final Table TABLE_OF_FOUR = new Table(4);
  private static final Table TABLE_OF_THREE = new Table(3);
  private SeatingManager sut;
  private static final CustomerGroup GROUP_OF_THREE = new CustomerGroup(3);
  private static final CustomerGroup ANOTHER_GROUP_OF_THREE = new CustomerGroup(3);
  private static final CustomerGroup GROUP_OF_SIX = new CustomerGroup(6);
  private static final CustomerGroup GROUP_OF_SEVEN = new CustomerGroup(7);
  private static final CustomerGroup ANOTHER_GROUP_OF_SIX = new CustomerGroup(6);

  @BeforeEach
  void setUp() {
    List<Table> tables = new ArrayList<>();
    tables.add(new Table(2));
    tables.add(new Table(5));
    tables.add(new Table(6));
    tables.add(TABLE_OF_THREE);
    tables.add(TABLE_OF_FOUR);

    sut = new SeatingManager(tables);
  }

  @Test
  void arrives_whenGroupSizeIsMoreThenSix_theyLeave() {

    sut.arrives(GROUP_OF_SEVEN);
    assertThat(sut.getWaitingGroups()).doesNotContain(GROUP_OF_SEVEN);
    assertThat(sut.getTables().values()).doesNotContain(GROUP_OF_SEVEN);

  }

  @Test
  void arrives_whenOnePossibleTableIsAvailable_seatThere() {

    sut.arrives(GROUP_OF_SIX);
    assertThat(sut.getWaitingGroups()).doesNotContain(GROUP_OF_SIX);
    assertThat(sut.getTables().values()).contains(GROUP_OF_SIX);
  }

  @Test
  void arrives_whenMultipleTablesArePossible_seatInTheOneWithLeastSeats() {
    sut.arrives(GROUP_OF_THREE);
    assertThat(sut.getWaitingGroups()).doesNotContain(GROUP_OF_THREE);
    assertThat(sut.getTables().get(TABLE_OF_THREE)).isEqualTo(GROUP_OF_THREE);

    sut.arrives(ANOTHER_GROUP_OF_THREE);
    assertThat(sut.getWaitingGroups()).doesNotContain(ANOTHER_GROUP_OF_THREE);
    assertThat(sut.getTables().get(TABLE_OF_FOUR)).isEqualTo(ANOTHER_GROUP_OF_THREE);
  }

  @Test
  void arrives_whenNoTableIsAvailable_putToWait() {
    sut.arrives(GROUP_OF_SIX);
    sut.arrives(ANOTHER_GROUP_OF_SIX);
    assertThat(sut.getWaitingGroups()).contains(ANOTHER_GROUP_OF_SIX);
    assertThat(sut.getTables().values()).doesNotContain(ANOTHER_GROUP_OF_SIX);
  }

  @Test
  void leaves_whenCustomerIsSeated_removeIt() {

    sut.getTables().put(TABLE_OF_FOUR, GROUP_OF_THREE);

    sut.leaves(GROUP_OF_THREE);

    assertThat(sut.getWaitingGroups()).doesNotContain(GROUP_OF_THREE);
    assertThat(sut.getTables().values()).doesNotContain(GROUP_OF_THREE);
  }

  @Test
  void leaves_whenCustomerIsWaiting_removeIt() {

    sut.getWaitingGroups().offer(GROUP_OF_SIX);

    sut.leaves(GROUP_OF_SIX);

    assertThat(sut.getWaitingGroups()).doesNotContain(GROUP_OF_SIX);
    assertThat(sut.getTables().values()).doesNotContain(GROUP_OF_SIX);
  }

  @Test
  void leaves_whenIsNeitherSeatedOrWaiting_doNothing() {

    sut.leaves(GROUP_OF_SIX);

    assertThat(sut.getWaitingGroups()).doesNotContain(GROUP_OF_SIX);
    assertThat(sut.getTables().values()).doesNotContain(GROUP_OF_SIX);
  }

  @Test
  void locate_whenGroupIsSeated_returnTheirTable() {
    sut.getTables().put(TABLE_OF_FOUR, GROUP_OF_THREE);

    assertThat(sut.locate(GROUP_OF_THREE)).isEqualTo(TABLE_OF_FOUR);
  }
  @Test
  void locate_whenGroupIsNotSeated_returnNull() {

    sut.locate(GROUP_OF_THREE);

    assertThat(sut.getTables().values()).doesNotContain(GROUP_OF_THREE);
  }
}