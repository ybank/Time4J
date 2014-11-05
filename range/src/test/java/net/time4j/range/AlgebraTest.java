package net.time4j.range;

import net.time4j.PlainDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class AlgebraTest {

    @Test
    public void precedesIfNoGap() {
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 10);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.precedes(b), is(false));
        assertThat(b.precededBy(a), is(false));
    }

    @Test
    public void precedesIfGap() {
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 10);

        PlainDate startB = PlainDate.of(2014, 5, 12);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.precedes(b), is(true));
        assertThat(b.precededBy(a), is(true));
    }

    @Test
    public void meetsIfNoGap() {
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 10);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.meets(b), is(true));
        assertThat(b.metBy(a), is(true));
    }

    @Test
    public void meetsIfGap() {
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 10);

        PlainDate startB = PlainDate.of(2014, 5, 12);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.meets(b), is(false));
        assertThat(b.metBy(a), is(false));
    }

    @Test
    public void precedesPast() {
        PlainDate endA = PlainDate.axis().getMinimum();
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.until(endA);
        DateInterval b = DateInterval.until(endB);

        assertThat(a.precedes(b), is(false));
    }

    @Test
    public void meetsPast() {
        PlainDate endA = PlainDate.axis().getMinimum();
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.until(endA);
        DateInterval b = DateInterval.until(endB);

        assertThat(a.meets(b), is(false));
    }

    @Test
    public void precedesFuture() {
        PlainDate startA = PlainDate.of(2014, 5, 17);
        PlainDate startB = PlainDate.axis().getMaximum();

        DateInterval a = DateInterval.since(startA);
        DateInterval b = DateInterval.since(startB);

        assertThat(a.precedes(b), is(false));
    }

    @Test
    public void meetsFuture() {
        PlainDate startA = PlainDate.of(2014, 5, 17);
        PlainDate startB = PlainDate.axis().getMaximum();

        DateInterval a = DateInterval.since(startA);
        DateInterval b = DateInterval.since(startB);

        assertThat(a.meets(b), is(false));
    }

    @Test
    public void meetsEmpty1() {
        PlainDate startA = PlainDate.of(2014, 5, 13);
        PlainDate endA = PlainDate.of(2014, 6, 15);

        PlainDate startB = PlainDate.of(2014, 6, 16);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.since(startB).collapse();

        assertThat(a.meets(b), is(true));
        assertThat(b.metBy(a), is(true));
    }

    @Test
    public void meetsEmpty2() {
        PlainDate startA = PlainDate.of(2014, 5, 13);
        PlainDate endA = PlainDate.of(2014, 6, 15);

        PlainDate startB = PlainDate.of(2014, 6, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.since(startB).collapse();

        assertThat(a.meets(b), is(false));
        assertThat(b.metBy(a), is(false));
    }

    @Test
    public void overlaps1() { // meets
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 10);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.overlaps(b), is(false));
    }

    @Test
    public void overlaps2() {
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 11);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.overlaps(b), is(true));
    }

    @Test
    public void overlaps3() { // meets
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 11);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA).withOpenEnd();
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.overlaps(b), is(false));
    }

    @Test
    public void overlaps4() {
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 12);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.overlaps(b), is(true));
    }

    @Test
    public void overlaps5() {
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 16);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.overlaps(b), is(true));
    }

    @Test
    public void overlaps6() { // finishes
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 17);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.overlaps(b), is(false));
    }

    @Test
    public void overlaps7() { // finishes
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 16);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB).withOpenEnd();

        assertThat(a.overlaps(b), is(false));
    }

    @Test
    public void overlaps8() { // equals
        PlainDate startA = PlainDate.of(2014, 5, 11);
        PlainDate endA = PlainDate.of(2014, 5, 17);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 18);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB).withOpenEnd();

        assertThat(a.overlaps(b), is(false));
    }

    @Test
    public void overlaps9() { // contains
        PlainDate startA = PlainDate.of(2014, 5, 11);
        PlainDate endA = PlainDate.of(2014, 5, 18);

        PlainDate startB = PlainDate.of(2014, 5, 12);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.overlaps(b), is(false));
    }

    @Test
    public void overlaps10() { // during
        PlainDate startA = PlainDate.of(2014, 5, 13);
        PlainDate endA = PlainDate.of(2014, 5, 15);

        PlainDate startB = PlainDate.of(2014, 5, 12);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.overlaps(b), is(false));
    }

    @Test
    public void overlaps11() { // starts
        PlainDate startA = PlainDate.of(2014, 5, 13);
        PlainDate endA = PlainDate.of(2014, 6, 15);

        PlainDate startB = PlainDate.of(2014, 5, 13);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.overlaps(b), is(false));
    }

    @Test
    public void overlaps12() { // overlappedBy
        PlainDate startA = PlainDate.of(2014, 5, 13);
        PlainDate endA = PlainDate.of(2014, 6, 15);

        PlainDate startB = PlainDate.of(2014, 5, 10);
        PlainDate endB = PlainDate.of(2014, 5, 13);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.overlaps(b), is(false));
    }

    @Test
    public void overlapsEmpty1() {
        PlainDate startA = PlainDate.of(2014, 5, 13);
        PlainDate endA = PlainDate.of(2014, 6, 15);

        PlainDate startB = PlainDate.of(2014, 6, 15);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.since(startB).collapse();

        assertThat(a.overlaps(b), is(false));
    }

    @Test
    public void overlapsEmpty2() {
        PlainDate startA = PlainDate.of(2014, 5, 13);
        PlainDate endA = PlainDate.of(2014, 6, 15);

        PlainDate startB = PlainDate.of(2014, 6, 16);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.since(startB).collapse();

        assertThat(a.overlaps(b), is(false));
    }

    @Test
    public void overlappedBy() {
        PlainDate startA = PlainDate.of(2014, 5, 13);
        PlainDate endA = PlainDate.of(2014, 6, 15);

        PlainDate startB = PlainDate.of(2014, 5, 10);
        PlainDate endB = PlainDate.of(2014, 5, 13);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(b.overlaps(a), is(true));
        assertThat(a.overlappedBy(b), is(true));
    }

    @Test
    public void finishedByEmpty1() {
        PlainDate startA = PlainDate.of(2014, 5, 13);
        PlainDate endA = PlainDate.of(2014, 6, 15);

        PlainDate startB = PlainDate.of(2014, 6, 15);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.since(startB).collapse();

        assertThat(a.finishedBy(b), is(false));
    }

    @Test
    public void finishedByEmpty2() {
        PlainDate startA = PlainDate.of(2014, 5, 13);
        PlainDate endA = PlainDate.of(2014, 6, 15);

        PlainDate startB = PlainDate.of(2014, 6, 16);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.since(startB).collapse();

        assertThat(a.finishedBy(b), is(false));
    }

    @Test
    public void finishes1() {
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 17);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.finishes(b), is(false));
        assertThat(a.finishedBy(b), is(true));
    }

    @Test
    public void finishes2() {
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 18);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA).withOpenEnd();
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.finishes(b), is(false));
        assertThat(a.finishedBy(b), is(true));
    }

    @Test
    public void finishes3() {
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 16);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB).withOpenEnd();

        assertThat(a.finishes(b), is(false));
        assertThat(a.finishedBy(b), is(true));
    }

    @Test
    public void finishes4() { // contains
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 18);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.finishes(b), is(false));
        assertThat(a.finishedBy(b), is(false));
    }

    @Test
    public void finishes5() { // overlaps
        PlainDate startA = PlainDate.of(2012, 2, 29);
        PlainDate endA = PlainDate.of(2014, 5, 16);

        PlainDate startB = PlainDate.of(2014, 5, 11);
        PlainDate endB = PlainDate.of(2014, 5, 17);

        DateInterval a = DateInterval.between(startA, endA);
        DateInterval b = DateInterval.between(startB, endB);

        assertThat(a.finishes(b), is(false));
        assertThat(a.finishedBy(b), is(false));
    }

}